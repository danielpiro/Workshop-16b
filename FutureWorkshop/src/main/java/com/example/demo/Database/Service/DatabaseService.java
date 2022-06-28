package com.example.demo.Database.Service;
import com.example.demo.CustomExceptions.Exception.ResourceNotFoundException;
import com.example.demo.CustomExceptions.Exception.SupplyManagementException;
import com.example.demo.Database.DTOobjects.Cart.ShoppingBasketDTO;
import com.example.demo.Database.DTOobjects.GlobalServices.IdGeneratorDTO;
import com.example.demo.Database.DTOobjects.History.HistoryDTO;
import com.example.demo.Database.DTOobjects.Store.Permissions.StoreRoleDTO;
import com.example.demo.Database.DTOobjects.Store.Permissions.StoreRoleToPermissionDTO;
import com.example.demo.Database.DTOobjects.Store.Permissions.StoreRoleToStoreRoleDTO;
import com.example.demo.Database.DTOobjects.Store.PolicyDTO;
import com.example.demo.Database.DTOobjects.Store.Predicates.*;
import com.example.demo.Database.DTOobjects.Store.ProductDTO;
import com.example.demo.Database.DTOobjects.Store.ReviewDTO;
import com.example.demo.Database.DTOobjects.User.ComplaintDTO;
import com.example.demo.Database.DTOobjects.Store.StoreDTO;
import com.example.demo.Database.DTOobjects.User.UserDTO;
import com.example.demo.Database.Repositories.*;

import com.example.demo.Database.Repositories.Store.PolicesRepository;
import com.example.demo.Database.Repositories.Store.Predicate.*;
import com.example.demo.Database.Repositories.Store.Permission.StoreRoleRepository;
import com.example.demo.Database.Repositories.Store.Permission.StoreRoleToPermissionRepository;
import com.example.demo.Database.Repositories.Store.Permission.StoreRoleToStoreRoleRepository;
import com.example.demo.Database.Repositories.Store.StoreRepository;
import com.example.demo.Database.Repositories.globalServices.IdGeneratorRepository;
import com.example.demo.NotificationsManagement.ComplaintNotification;
import com.example.demo.ShoppingCart.ShoppingBasket;
import com.example.demo.ShoppingCart.ShoppingCart;
import com.example.demo.Store.Product;
import com.example.demo.Store.ProductsCategories;
import com.example.demo.Store.Review;
import com.example.demo.Store.Store;
import com.example.demo.Store.StorePurchase.Policies.*;
import com.example.demo.Store.StorePurchase.PurchasableProduct;
import com.example.demo.Store.StorePurchase.predicates.*;
import com.example.demo.Store.StorePurchase.predicates.PredImplementions.*;
import com.example.demo.StorePermission.*;
import com.example.demo.User.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;


@Service
public class DatabaseService {


    //todo add transactional
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private StoreRoleRepository storeRoleRepository;

    @Autowired
    private StoreRoleToPermissionRepository storeRoleToPermissionRepository;

    @Autowired
    private StoreRoleToStoreRoleRepository storeRoleToStoreRoleRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private IdGeneratorRepository idGeneratorRepository;

    @Autowired
    private AllPredicateRepository allPredicateRepository;

    @Autowired
    private CategoryPredicateRepository categoryPredicateRepository;
    @Autowired
    private CompositePredicateRepository compositePredicateRepository;
    @Autowired
    private ProductPredicateRepository productPredicateRepository;
    @Autowired
    private UserPredicateRepository userPredicateRepository;
    @Autowired
    private PolicesRepository policesRepository;

    public DatabaseService(){
        super();
    }

    @Transactional
    public ProductDTO saveProduct(Product p,String storeId){
        String productId= p.getId();

        //so we dont save twice
        deleteReviewByProductID(productId);

        //save all the reviews of the product
        for (Review r : p.getReviews()){
            saveReviewByProduct(r,productId);
        }

        return productRepository.saveAndFlush(p.productToDTO(storeId));
    }

    @Transactional
    public ReviewDTO saveReviewByProduct(Review r, String productId){

        //save the review with reference to its product
        ReviewDTO reviewDTO = r.reviewToDTO();
        reviewDTO.setProductId(productId);
        return reviewRepository.saveAndFlush(reviewDTO);
    }
    @Transactional
    public ConcurrentHashMap<String,Product> getProductsOfStore(String storeId) throws SupplyManagementException, ResourceNotFoundException {
        List<ProductDTO> productDTOList = productRepository.getByBelongsToStore(storeId);
        ConcurrentHashMap<String,Product> products = new ConcurrentHashMap<>();
        for(ProductDTO productDTO:productDTOList){
            products.put(productDTO.getId(),getProductByID(productDTO.getId()));
        }
        return products;
    }

    
    private Product getProductByID(String productID) throws ResourceNotFoundException, SupplyManagementException {
       Optional<ProductDTO> productOpt=  productRepository.findById(productID);
       if(productOpt.isPresent()){
           ProductDTO pDTO = productOpt.get();

           List<Review> reviewList = new LinkedList<>();

           //get reviewsDTO and convert into Reviews
           for(ReviewDTO rd : getReviewListByProductID( pDTO.getId())){
               reviewList.add(rd.reviewDtoToReview());
           }

           return  new Product(pDTO.getId(),pDTO.getName(),pDTO.getPrice(),pDTO.getSupply()
                   ,reviewList,pDTO.getRating(),pDTO.getCategory().toString());
       }
       else
       {
           throw new ResourceNotFoundException("no product with productId " + productID );
       }
    }

    public List<ReviewDTO> getReviewListByProductID (String productId){
        return reviewRepository.findByProductId(productId);
    }

    @Transactional
    public void deleteProductById(String productId){
        productRepository.deleteById(productId);
        deleteReviewByProductID(productId);
    }


    public void deleteReviewByProductID(String productId){
        reviewRepository.deleteByProductId(productId);

    }


    public ShoppingCart getShoppingCart(String userId){


        List<ShoppingBasketDTO> allBaskets= basketRepository.findByUserId(userId);

        ShoppingCart sc = new ShoppingCart(userId);

        for (ShoppingBasketDTO entry : allBaskets){
            sc.addProduct(entry.getProductID(),entry.getStoreId(),entry.getProdAmount());
        }

        return sc;
    }

    public void saveShoppingCart(ShoppingCart shoppingCart){

        deleteShoppingCart(shoppingCart.getUserId());

        for (Map.Entry<String,ShoppingBasket>  sb :shoppingCart.basketCases.entrySet()){
            for(Map.Entry<String,Integer> entry: sb.getValue().productAmount.entrySet()){
                ShoppingBasketDTO toSave = new ShoppingBasketDTO(sb.getKey(),shoppingCart.getUserId(),entry.getKey(),entry.getValue());
                basketRepository.save(toSave);

            }

        }


    }

    public void deleteShoppingCart(String userId){
        basketRepository.deleteByUserId(userId);
    }

    public void deleteReviewBody (String body){
        reviewRepository.deleteByBody(body);
    }

    @Transactional
    public UserDTO saveUser(Subscriber sub){
        UserDTO userDTO = new UserDTO(sub.getName(),sub.getPassword(),sub.isLogged_in());
        return userRepository.saveAndFlush(userDTO);
    }

    @Transactional
    public void deleteUserByName (String name){
        userRepository.deleteByName(name);
        complaintRepository.deleteByUserID(name);
    }
    public void saveComplaint(ComplaintDTO complaintDTO) {
        complaintRepository.saveAndFlush(complaintDTO);
    }
    public void deleteComplaint(ComplaintDTO complaintDTO) {
        complaintRepository.delete(complaintDTO);
    }
    public List<ComplaintDTO> findComplaintByUserId (String userID) {
        return complaintRepository.findByUserID(userID);
    }

    public List<UserDTO> findUserbyName(String name){
       return userRepository.findByName(name);
    }

    public List<HistoryDTO> findHistoryByUserId (String userID){
        return historyRepository.findByUserID(userID);
    }
    public List<HistoryDTO> findHistoryByStoreId (String storeID){
        return historyRepository.findByStoreID(storeID);
    }
    public void savePurchaseHistory(HistoryDTO historyDTO){
        historyRepository.saveAndFlush(historyDTO);
    }
    public void deleteHistoryDTO(HistoryDTO historyDTO){
        historyRepository.delete(historyDTO);
    }
    public List<UserDTO> allUsers(){
        return userRepository.findAll();
    }

    private void saveStoreRole(StoreRoleDTO storeRoleDTO) {
        Optional<StoreRoleDTO> storeRoleDTOOptional = storeRoleRepository.findByUserIdAndStoreId(storeRoleDTO.getUserId(),storeRoleDTO.getStoreId());
        if(storeRoleDTOOptional.isEmpty()) {
            storeRoleRepository.saveAndFlush(storeRoleDTO);
        }
    }
    private StoreRoleDTO getStoreRole(String userId, String storeId) throws SQLException {
        Optional<StoreRoleDTO> storeRoleDTOOptional = storeRoleRepository.findByUserIdAndStoreId(userId,storeId);
        if(storeRoleDTOOptional.isPresent()){
            return storeRoleDTOOptional.get();
        }
        throw new SQLException("no StoreRole with this data");
    }

    private void saveStoreRolePermission(Long storeRoleId, List<Permission> permissions) {
        for (Permission p : permissions) {
            Optional<StoreRoleToPermissionDTO> storeRoleToPermissionDTOOptional = storeRoleToPermissionRepository.findByPermissionIdAndStoreRoleId(p.toString(),storeRoleId);
            if(storeRoleToPermissionDTOOptional.isEmpty()) {
                StoreRoleToPermissionDTO storeRoleToPermissionDTO = new StoreRoleToPermissionDTO(storeRoleId, p.toString());
                storeRoleToPermissionRepository.saveAndFlush(storeRoleToPermissionDTO);
            }
        }

    }

    private void saveStoreRolePermissionAndSaveStoreRole
            (StoreRoleDTO storeRoleDTO, List<Permission> permissions) throws SQLException {
        saveStoreRole(storeRoleDTO);
        Long storeRoleId = getStoreRole(storeRoleDTO.getUserId(),storeRoleDTO.getStoreId()).getId();
        saveStoreRolePermission(storeRoleId,permissions);
    }
    private void saveStoreRoleToStoreRole(String storeId,String userGivingId,String userGettingId) throws SQLException {
        long userGiving = getStoreRole(userGivingId,storeId).getId();
        long userGetting = getStoreRole(userGettingId,storeId).getId();
        if(storeRoleToStoreRoleRepository.findByGettingPermissionIdAndGivingPermissionId(userGetting,userGiving).isEmpty()) {
            StoreRoleToStoreRoleDTO storeRoleToStoreRoleDTO = new StoreRoleToStoreRoleDTO(userGiving,userGetting);
            storeRoleToStoreRoleRepository.saveAndFlush(storeRoleToStoreRoleDTO);
        }
    }
    @Transactional
    public void saveStoreRolePermissionAndSaveStoreRoleToStoreRoleAndSaveStoreRole
            (StoreRoleDTO storeRoleDTO, List<Permission> permissions,String userGiving) throws SQLException {
        saveStoreRole(storeRoleDTO);
        Long storeRoleId = getStoreRole(storeRoleDTO.getUserId(),storeRoleDTO.getStoreId()).getId();
        saveStoreRolePermission(storeRoleId,permissions);
        saveStoreRoleToStoreRole(storeRoleDTO.getStoreId(),userGiving,storeRoleDTO.getUserId());

    }

    public List<StoreRoles> getRolesOfStore(String storeId) throws SQLException {
        List<StoreRoleDTO> storeRoleDTOList = storeRoleRepository.getByStoreId(storeId);
        HashMap<StoreRoles,List<StoreRoleToStoreRoleDTO>> usersStoreRoleCreated = new HashMap<>();
        HashMap<Long,StoreRoles> userRoleWithDatabaseId = new HashMap<>();

        for (StoreRoleDTO storeRoleDTO:storeRoleDTOList){

            List<StoreRoleToPermissionDTO> permissionDTOS = storeRoleToPermissionRepository.getByStoreRoleId(storeRoleDTO.getId());
            StoreRoles storeRole;
            if(storeRoleDTO.getType().equals(StoreRoleType.original_owner.toString())){
                storeRole = new OriginalStoreOwnerRole(storeRoleDTO.getUserId());
            }
            else if(storeRoleDTO.getType().equals(StoreRoleType.owner.toString())){
                List<Permission> permissions = new ArrayList<>();
                for(StoreRoleToPermissionDTO storeRoleToPermissionDTO : permissionDTOS){
                    permissions.add(Permission.valueOf(storeRoleToPermissionDTO.getPermissionId()));
                }
                storeRole = new StoreOwnerRole(storeRoleDTO.getUserId(),permissions);
            }
            else if(storeRoleDTO.getType().equals(StoreRoleType.manager.toString())){
                storeRole = new StoreManager(storeRoleDTO.getUserId());
            }
            else{
                throw new SQLException("no store role with this type");
            }
            userRoleWithDatabaseId.put(storeRoleDTO.getId(),storeRole);

            List<StoreRoleToStoreRoleDTO> gavePermissionTo = storeRoleToStoreRoleRepository.getByGivingPermissionId(storeRoleDTO.getId());
            usersStoreRoleCreated.put(storeRole,gavePermissionTo);
        }

        for (StoreRoles storeRole:usersStoreRoleCreated.keySet()){
            List<StoreRoleToStoreRoleDTO> storeRoleToStoreRoleDTOList = usersStoreRoleCreated.get(storeRole);
            for(StoreRoleToStoreRoleDTO storeRoleToStoreRoleDTO: storeRoleToStoreRoleDTOList){
                long databaseId = storeRoleToStoreRoleDTO.getGettingPermissionId();
                StoreRoles userToAdd = userRoleWithDatabaseId.get(databaseId);
                storeRole.addToCreatedList(userToAdd);
            }
        }
        return new ArrayList<>(usersStoreRoleCreated.keySet());
    }
    @Transactional
    public void deleteRole(String StoreId,List<StoreRoles> storeRolesList){
        for(StoreRoles storeRoles: storeRolesList){
            Optional<StoreRoleDTO> storeRoleDTO =storeRoleRepository.findByUserIdAndStoreId(storeRoles.getUserId(), StoreId);
            if(storeRoleDTO.isEmpty()){
                return;
            }
            storeRoleRepository.deleteByUserIdAndStoreId(storeRoles.getUserId(),StoreId);
            storeRoleToPermissionRepository.deleteByStoreRoleId(storeRoleDTO.get().getId());
            storeRoleToStoreRoleRepository.deleteByGettingPermissionIdOrGivingPermissionId(storeRoleDTO.get().getId(),storeRoleDTO.get().getId());
        }
    }

    @Transactional
    public void saveStore(StoreDTO storeDTO,HashMap<StoreRoleDTO, List<Permission>> storeRoleDTOListHashMap) throws SQLException {
        storeRepository.saveAndFlush(storeDTO);
        for(Map.Entry<StoreRoleDTO, List<Permission>> entry:storeRoleDTOListHashMap.entrySet()){
            saveStoreRolePermissionAndSaveStoreRole(entry.getKey(),entry.getValue());
        }
    }
    public void deleteStore(String userId, String storeId) {
        storeRepository.deleteById(storeId);
    }
    @Transactional
    public void saveIdGenerator(List<IdGeneratorDTO> idGeneratorDTO){
        idGeneratorRepository.saveAllAndFlush(idGeneratorDTO);
    }

    public List<IdGeneratorDTO> loadIdGenerator(){
        return idGeneratorRepository.findAll();
    }

    public List<StoreDTO> getAllStores() {
        return storeRepository.findAll();
    }
    @Transactional
    public void saveAllPredicateDTOPolicy(String storeId,AllPredicateDTO allPredicateDTO, Policy policy){
            allPredicateRepository.saveAndFlush(allPredicateDTO);
            //Optional<AllPredicateDTO> allPredicateDTOWithId = allPredicateRepository.findByCartNumOfProductsAndPredicateType(allPredicateDTO.getCartNumOfProducts(), allPredicateDTO.getPredicateType());
            policesRepository.saveAndFlush(new PolicyDTO(storeId,policy.getPolicyId(), PolicyType.OnePredPolicy.toString(),allPredicateDTO.getId()));
    }
    @Transactional
    public void saveCategoryPredicateDTOPolicy(String storeId,AllPredicateDTO allPredicateDTO, List<ProductsCategories> categories, Policy policy){
        allPredicateRepository.save(allPredicateDTO);
        for(ProductsCategories productsCategory : categories){
            categoryPredicateRepository.save(new CategoryPredicateDTO(allPredicateDTO.getId(), productsCategory.toString()));
        }
        policesRepository.saveAndFlush(new PolicyDTO(storeId,policy.getPolicyId(), PolicyType.OnePredPolicy.toString(),allPredicateDTO.getId()));

    }
    @Transactional
    public void saveCompositePredicateDTOPolicy(AllPredicateDTO allPredicateDTO, CompositePredicateDTO compositePredicateDTO,PolicyDTO policyDTO){
        allPredicateRepository.save(allPredicateDTO);
        compositePredicateRepository.saveAndFlush(compositePredicateDTO);
        policesRepository.saveAndFlush(policyDTO);
    }
    @Transactional
    public void saveProductPredicateDTOPolicy(String storeId,AllPredicateDTO allPredicateDTO, List<PurchasableProduct> lp, Policy policy){
        allPredicateRepository.save(allPredicateDTO);//todo bug need fixing
        for(PurchasableProduct productPredicate: lp){
            productPredicateRepository.save(new ProductPredicateDTO(allPredicateDTO.getId(),PredicateProductType.Products_Above_Amount.toString() ));
        }
        policesRepository.saveAndFlush(new PolicyDTO(storeId,policy.getPolicyId(), PolicyType.OnePredPolicy.toString(),allPredicateDTO.getId()));
    }
    @Transactional
    public void saveProductPredicateDTOPolicy(String storeId, AllPredicateDTO allPredicateDTO, HashMap<PurchasableProduct,Integer> lp, Policy policy){
        allPredicateRepository.save(allPredicateDTO);//todo bug need fixing
        for(PurchasableProduct productPredicate: lp.keySet()){
            productPredicateRepository.save(new ProductPredicateDTO(allPredicateDTO.getId(),lp.get(productPredicate),PredicateProductType.WithoutAmount.toString() ));
        }
        policesRepository.saveAndFlush(new PolicyDTO(storeId,policy.getPolicyId(), PolicyType.OnePredPolicy.toString(),allPredicateDTO.getId()));
    }
    @Transactional
    public void saveUserPredicateDTOPolicy(String storeId, AllPredicateDTO allPredicateDTO, List<String> userIds,Policy policy){
        allPredicateRepository.save(allPredicateDTO);
        for(String userId: userIds){
            userPredicateRepository.save(new UserPredicateDTO(allPredicateDTO.getId(), userId));
        }
        policesRepository.saveAndFlush(new PolicyDTO(storeId, policy.getPolicyId(), PolicyType.OnePredPolicy.toString(),allPredicateDTO.getId()));
    }



    @Transactional
    public void savePolicyComp(PolicyDTO policyDTO){
        policesRepository.saveAndFlush(policyDTO);
    }

    @Transactional
    public CopyOnWriteArrayList<Policy> loadPoliesForStore(String storeId) throws SQLException {
        CopyOnWriteArrayList<Policy> output = new CopyOnWriteArrayList<>();
        List<PolicyDTO> policyDTOS = policesRepository.getByBelongsToStoreId(storeId);
        for(PolicyDTO policyDTO: policyDTOS){
            output.add(loadPolicyTree(policyDTO.getPolicyId()));
        }
        return output;
    }
    private Policy loadPolicyTree(String PolicyId) throws SQLException {
        PolicyDTO policyDTO = policesRepository.getById(PolicyId);
        PolicyType type = PolicyType.valueOf(policyDTO.getPolicyType());
        if(type.equals(PolicyType.OnePredPolicy)){
            PolicyPredicate predicate = loadPredicate(policyDTO.getPredicateId());
            return new OnePredPolicy(policyDTO.getPolicyId(),predicate);
        }
        else if(type.equals(PolicyType.AndGatePolicy)){
            Policy policy1 = loadPolicyTree(policyDTO.getForeignKeyPolicyId1());
            Policy policy2 = loadPolicyTree(policyDTO.getForeignKeyPolicyId2());
            return new AndGatePolicy(policy1,policy2);
        }
        else if(type.equals(PolicyType.OrGatePolicy)){
            Policy policy1 = loadPolicyTree(policyDTO.getForeignKeyPolicyId1());
            Policy policy2 = loadPolicyTree(policyDTO.getForeignKeyPolicyId2());
            return new OrGatePolicy(policy1,policy2);
        }
        else if(type.equals(PolicyType.conditioningPolicy)){
            Policy policy1 = loadPolicyTree(policyDTO.getForeignKeyPolicyId1());
            Policy policy2 = loadPolicyTree(policyDTO.getForeignKeyPolicyId2());
            return new conditioningPolicy(policy1,policy2);
        }
        else throw new SQLException("no policy with this type");
    }
    private PolicyPredicate loadPredicate(long PredicateId) throws SQLException {
        AllPredicateDTO allPredicateDTO =  allPredicateRepository.getById(PredicateId);
        if(allPredicateDTO.getPredicateType().equals(PredicatesTypes.AlwaysTrue.toString())){
            return new AlwaysTrue();
        }
        else if (allPredicateDTO.getPredicateType().equals(PredicatesTypes.ProductPredicate.toString())) {
            return new AlwaysTrue();//todo fix
//
//            List<ProductPredicateDTO> productPredicateDTOList =productPredicateRepository.getByProductIdForeignKey(allPredicateDTO.getId());
//            if(productPredicateDTOList.get(0).getPredicateProductType().equals(PredicateProductType.Products_Above_Amount.toString())){
//                HashMap<PurchasableProduct,Integer> products= new HashMap<>();
//                for(ProductPredicateDTO productPredicateDTO: productPredicateDTOList){
//                    products.put(new Product(productPredicateDTO.))
//                }
//            }
//
//            return new ProductPredicate()
        }
        else if (allPredicateDTO.getPredicateType().equals(PredicatesTypes.TimePredicate.toString())) {
            return new TimePredicate(LocalDateTime.parse(allPredicateDTO.getStartTime()),LocalDateTime.parse(allPredicateDTO.getEndTime()), PredicateTimeType.valueOf(allPredicateDTO.getTimeType()));
        }
        else if (allPredicateDTO.getPredicateType().equals(PredicatesTypes.pricePredicate.toString())) {
            return new PricePredicate(allPredicateDTO.getPrice());
        }
        else if (allPredicateDTO.getPredicateType().equals(PredicatesTypes.CartPredicate.toString())) {
            return  new CartPredicate(allPredicateDTO.getCartNumOfProducts());
        }
        else if (allPredicateDTO.getPredicateType().equals(PredicatesTypes.CategoryPredicate.toString())) {
            List<ProductsCategories> productsCategories = new ArrayList<>();
            List<CategoryPredicateDTO> categoryPredicateDTOS= categoryPredicateRepository.getByBelongsToPredicateId(allPredicateDTO.getId());
            for(CategoryPredicateDTO categoryPredicateDTO: categoryPredicateDTOS){
                productsCategories.add(ProductsCategories.valueOf(categoryPredicateDTO.getCategory()));
            }
            return new CategoryPredicate(productsCategories);
        }
        else if (allPredicateDTO.getPredicateType().equals(PredicatesTypes.UserAgePredicate.toString())) {
            return new UserPredicate(PredicateUserType.UserAge,allPredicateDTO.getStartAge(),allPredicateDTO.getEndAge());
        }
        else if (allPredicateDTO.getPredicateType().equals(PredicatesTypes.UserIdPredicate.toString())) {
            List<String> userIds = new ArrayList<>();
            List<UserPredicateDTO> userPredicateDTOS = userPredicateRepository.getByPredicateId(allPredicateDTO.getId());
            for(UserPredicateDTO userPredicateDTO: userPredicateDTOS){
                userIds.add(userPredicateDTO.getUserId());
            }
            return new UserPredicate(userIds,PredicateUserType.OnUserId);
        }
        else throw new SQLException("no predicate with this type");
    }

    public List<ComplaintDTO> findAllComplaint(){
        return complaintRepository.findAll();
    }

    public void clearDatabase(){
        productRepository.deleteAll();
        reviewRepository.deleteAll();
        basketRepository.deleteAll();
        userRepository.deleteAll();
        historyRepository.deleteAll();
        complaintRepository.deleteAll();
        storeRoleRepository.deleteAll();
        storeRoleToPermissionRepository.deleteAll();
        storeRoleToStoreRoleRepository.deleteAll();
        storeRepository.deleteAll();
        idGeneratorRepository.deleteAll();
        allPredicateRepository.deleteAll();
        categoryPredicateRepository.deleteAll();
        complaintRepository.deleteAll();
        productRepository.deleteAll();
        userPredicateRepository.deleteAll();
        policesRepository.deleteAll();
    }
}
