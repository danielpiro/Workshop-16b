package com.example.demo.Controllers;

import com.example.demo.CustomExceptions.Exception.NotifyException;
import com.example.demo.CustomExceptions.Exception.StorePolicyViolatedException;
import com.example.demo.CustomExceptions.Exception.SupplyManagementException;
import com.example.demo.CustomExceptions.Exception.UserException;
import com.example.demo.Mock.*;
import com.example.demo.CustomExceptions.ExceptionHandler.ReturnValue;
import com.example.demo.ExternalConnections.Delivery.DeliveryNames;
import com.example.demo.ExternalConnections.Delivery.FedEx;
import com.example.demo.ExternalConnections.Delivery.UPS;
import com.example.demo.ExternalConnections.ExternalConnectionHolder;
import com.example.demo.ExternalConnections.ExternalConnections;
import com.example.demo.ExternalConnections.Payment.MasterCard;
import com.example.demo.ExternalConnections.Payment.PaymentNames;
import com.example.demo.ExternalConnections.Payment.Visa;
import com.example.demo.GlobalSystemServices.Log;
import com.example.demo.History.History;
import com.example.demo.NotificationsManagement.NotificationManager;
import com.example.demo.ShoppingCart.InventoryProtector;
import com.example.demo.ShoppingCart.ShoppingBasket;
import com.example.demo.ShoppingCart.ShoppingCart;
import com.example.demo.Store.Product;
import com.example.demo.Store.ProductsCategories;
import com.example.demo.Store.Store;
import com.example.demo.Store.StorePurchase.Discounts.Discount;
import com.example.demo.Store.StorePurchase.Policies.Policy;
import com.example.demo.Store.StorePurchase.Policies.PolicyBuilder;
import com.example.demo.Store.StorePurchase.PurchasableProduct;
import com.example.demo.User.Guest;
import com.example.demo.User.Subscriber;
//import com.example.demo.dto.AdminDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.naming.NoPermissionException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;


@CrossOrigin(maxAge = 3600)
@RestController
@EnableWebMvc
@RequestMapping("/api")
public class BigController {
    private StoreController sc;
    private UserController us;
    private PolicyBuilder policyBuilder;
    Log my_log = Log.getLogger();


    //    public BigController(UserController us , StoreController sc) throws IOException {
//        this.us = us;
//        this.sc = sc;
//    }
    public BigController() throws IOException, UserException, NoPermissionException, SupplyManagementException {
        this.us = new UserController();
        this.sc = new StoreController();
        this.policyBuilder = new PolicyBuilder();
        initiateExternalConnections();
        NotificationManager.buildNotificationManager(us);
        initializeSystem();
        my_log.info("System Started");
    }

    public void initiateExternalConnections() {
        ExternalConnections externalConnections = ExternalConnections.getInstance();
        externalConnections.addPayment(new Visa());
        externalConnections.addPayment(new MasterCard());
        externalConnections.addDelivery(new FedEx());
        externalConnections.addDelivery(new UPS());
    }

    //    @PostMapping(value = "/add/admin" , consumes = {MediaType.APPLICATION_JSON_VALUE})
//    public void addSystemAdmin(@RequestBody(required = true)  whoIsAdding, String user_toMakeAdmin) {
//        getUserController().addSystemAdmin(whoIsAdding,user_toMakeAdmin);
//    }


    private List<String> initializeUsers() throws UserException {
        return getUserController().initialize();


    }


    @PostMapping("/users/delete")
    public ReturnValue deleteUser(@RequestParam String isDeleting,
                                  @RequestParam String whosBeingDeleted) throws NoPermissionException, UserException {
        my_log.info("user" + isDeleting + "is trying to delete user" + whosBeingDeleted);
        //sc.removeRoleInHierarchy(whosBeingDeleted);
        if (checkIfUserHaveRoleInStore(whosBeingDeleted)) {
            throw new NoPermissionException("cant delete user with store role");
        }
        ReturnValue rv = new ReturnValue(true, "", getUserController().deleteUser(isDeleting, whosBeingDeleted));
        return rv;
    }


    @PostMapping("/users/signup")
    public ReturnValue signup(@RequestParam String user_name,
                              @RequestParam String password) throws UserException {
        my_log.info("user " + user_name + " is trying to sign up");
        ReturnValue rv = new ReturnValue(true, "", getUserController().sign_up(user_name, password));
        return rv;
    }

    @PostMapping("/users/login")
    public ReturnValue login(@RequestParam String userNameLogin,
                             @RequestParam String password) throws UserException {
        ReturnValue rv = new ReturnValue(true, "", getUserController().login(userNameLogin, password));
        return rv;
    }

    @PostMapping("/guest/login")
    public ReturnValue login(@RequestParam String guestId,
                             @RequestParam String userNameLogin,
                             @RequestParam String password) throws UserException {
        ReturnValue rv = new ReturnValue(true, "", getUserController().login(guestId, userNameLogin, password));
        return rv;
    }

    @PostMapping("/users/logout")
    public ReturnValue logout(@RequestParam String user_name) throws UserException {

        ReturnValue rv = new ReturnValue(true, "", getUserController().logout(user_name));
        return rv;

    }

    @PostMapping("/market")
    public ReturnValue sendComplaint(@RequestParam String userId, @RequestParam String StoreName, @RequestParam String complaint) {
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }


    @GetMapping("/market/guest")
    public ReturnValue addGuest() {

        ReturnValue rv = new ReturnValue(true, "", getUserController().addGuest().name);
        return rv;
    }


    @PostMapping("/users")
    public ReturnValue GuestExitSystem(@RequestParam String guestId) {
        getUserController().GuestExitSystem(guestId);

        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    private Subscriber getSystemAdmin() {
        return getUserController().getSystemAdmin();
    }


    @PostMapping("/cart")
    public ReturnValue getShoppingCart(@RequestParam String user_Id) throws Exception {
        ShoppingCart shoppingCart = getUserController().getShoppingCart(user_Id);

        List<Object> products = new ArrayList<>();
        List<Object> products2 = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (var basket : shoppingCart.basketCases.entrySet()) {
            for (var pamount : basket.getValue().productAmount.entrySet()) {
                Product product = sc.getProductById(basket.getKey(), pamount.getKey());

                String json = String.format("{\"id\":\"%s\",\"name\":\"%s\",\"price\":\"%s\",\"amount\":\"%d\",\"category\":\"%s\",\"storeId\":\"%s\"}", product.getId(), product.getName(), product.getPrice(), pamount.getValue(), product.getCategory(), basket.getKey());
                products.add(objectMapper.readTree(json));

            }
            if (!products.isEmpty()) {
                products2.add(objectMapper.readTree(String.format("{\"%s\":%s}", basket.getKey(), products)));
            }
            products.clear();

        }
        ReturnValue rv = new ReturnValue(true, "", products2);
        return rv;

    }


//    public boolean containsStore(String user_id, String storeID) {
//        return getUserController().containsStore(user_id, storeID);
//    }

    @PostMapping(value = "/cart/delete", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ReturnValue removeProductFromCart(@Valid @RequestBody MockSmallProduct mockSmallProduct) throws UserException {

        getUserController().removeProduct(mockSmallProduct.getUser_id(), mockSmallProduct.getProductID(), mockSmallProduct.getStoreID(), mockSmallProduct.getAmount());

        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    //auction or bid false for now
    @PostMapping(value = "/cart/product", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ReturnValue addProductFromCart(@Valid @RequestBody MockSmallProduct mockProduct,
                                          @RequestParam boolean auctionOrBid) throws UserException {

        InventoryProtector inventoryProtector = sc.getInventoryProtector(mockProduct.getStoreID());
        getUserController().addProduct(mockProduct.getUser_id(), mockProduct.getProductID(), mockProduct.getStoreID(), mockProduct.getAmount(), inventoryProtector, auctionOrBid);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;

    }

    public boolean removePaymentService(PaymentNames payment) {
        return ExternalConnections.getInstance().removePayment(payment);
    }

    @PostMapping("/cart/purchase")
    public ReturnValue purchaseCart(@RequestParam String user_id,
                                    @RequestParam PaymentNames payment,
                                    @RequestParam DeliveryNames delivery) throws SupplyManagementException, StorePolicyViolatedException, UserException {


        float a = getUserController().purchaseCart(user_id, new ExternalConnectionHolder(delivery, payment));
        ReturnValue rv = new ReturnValue(true, "", a);
        return rv;
    }

    /// Store controller

    //todo didnt return String!!!

    @PostMapping(value = "/store", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ReturnValue addNewProductToStore(@Valid @RequestBody MockFullProduct mockProduct) throws NoPermissionException, SupplyManagementException, UserException {
        userExistsAndLoggedIn(mockProduct.getUserId());

        String ans = getStoreController().addNewProduct(mockProduct.getStoreId(), mockProduct.getUserId(), mockProduct.getProductName(), mockProduct.getPrice(), mockProduct.getSupply(), mockProduct.getCategory());

        ReturnValue rv = new ReturnValue(true, "", ans);
        return rv;

    }

    @DeleteMapping(value = "/permissions", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ReturnValue removeSomePermissions(@Valid @RequestBody MockPermission mockPermission) throws NoPermissionException, UserException {
        userExistsAndLoggedIn(mockPermission.getUserIdRemoving());

        sc.removeSomePermissions(mockPermission.getStoreId(), mockPermission.getUserIdRemoving(), mockPermission.getUserAffectedId(), mockPermission.getPerToRemove());

        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    @PostMapping("/store/open")
    public ReturnValue openNewStore(@RequestParam String userId,
                                    @RequestParam String storeName) throws NoPermissionException, UserException {
        userExistsAndLoggedIn(userId);
        List<String> managers = new ArrayList<>();
        managers.add(userId);
        ReturnValue rv = new ReturnValue(true, "", getStoreController().openNewStore(storeName, managers));
        return rv;

    }

    @PostMapping("/store/freeze")
    public ReturnValue unfreezeStore(@RequestParam String storeId,
                                     @RequestParam String userId) throws NoPermissionException, UserException, NotifyException {
        userExistsAndLoggedIn(userId);
        getStoreController().unfreezeStore(storeId, userId);

        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    @PostMapping("/store/unfreeze")
    public ReturnValue freezeStore(@RequestParam String storeId,
                                   @RequestParam String userId) throws NoPermissionException, UserException, NotifyException {

        userExistsAndLoggedIn(userId);
        getStoreController().freezeStore(storeId, userId);

        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    @GetMapping("/online/amount")
    public ReturnValue getOnlineUsersNum() throws UserException {

        ReturnValue rv = new ReturnValue(true, "", getUserController().getOnlineUsersNum());
        return rv;
    }

    @GetMapping("/registered/amount")
    public ReturnValue getRegisteredUsersNum() throws UserException {

        ReturnValue rv = new ReturnValue(true, "",  getUserController().getRegisteredUsersNum());
        return rv;
    }


    /**
     * @param storeId the store you want manager info about
     * @param userId  the requesting user, will make sure you are logged in
     *                and that you have permission in the specific store
     * @return
     * @throws NoPermissionException
     * @throws UserException
     */

    @GetMapping("/managers/info")

    public ReturnValue getInfoOnManagersOwners(@RequestParam String storeId,
                                               @RequestParam String userId) throws NoPermissionException, UserException {
        userExistsAndLoggedIn(userId);


        ReturnValue rv = new ReturnValue(true, "", getStoreController().getInfoOnManagersOwners(storeId, userId));
        return rv;
    }

    @PostMapping(value = "/store/product", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ReturnValue editProduct(@RequestParam String productId, @Valid @RequestBody MockFullProduct mockProduct) throws NoPermissionException, SupplyManagementException, UserException {
        userExistsAndLoggedIn(mockProduct.getUserId());
        getStoreController().editProduct(mockProduct.getStoreId(), mockProduct.getUserId(), productId, mockProduct.getSupply(), mockProduct.getProductName(), mockProduct.getPrice(), mockProduct.getCategory());
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }


    @PostMapping("/store/product/delete")
    public ReturnValue deleteProductFromStore(@RequestParam String storeId,
                                              @RequestParam String userId,
                                              @RequestParam String productId) throws NoPermissionException, SupplyManagementException, UserException, NotifyException {
        userExistsAndLoggedIn(userId);
        getStoreController().deleteProduct(storeId, userId, productId);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    @DeleteMapping("/store/permissions")
    public ReturnValue removePermissionTo(@RequestParam String storeId, @RequestParam String userIdRemoving, @RequestParam String UserAffectedId) throws NoPermissionException, UserException, NotifyException {
        if (getUserController().checkIfUserExists(userIdRemoving) && getUserController().checkIfUserExists(UserAffectedId) && getUserController().checkIfUserIsLoggedIn(userIdRemoving))
            getStoreController().removeRoleInHierarchy(storeId, userIdRemoving, UserAffectedId);
        else
            throw new IllegalArgumentException("couldn't remove permission because the given userId doesn't exist or is not logged in");
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    @PostMapping(value = "/owner", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ReturnValue createOwner(@Valid @RequestBody MockSmallPermission mockPermission) throws NoPermissionException, UserException, NotifyException {
        if (getUserController().checkIfUserExists(mockPermission.getUserIdGiving()) && getUserController().checkIfUserExists(mockPermission.getUserGettingPermissionId()) && getUserController().checkIfUserIsLoggedIn(mockPermission.getUserIdGiving())) {
            getStoreController().createOwner(mockPermission.getStoreId(), mockPermission.getUserIdGiving(), mockPermission.getUserGettingPermissionId(), mockPermission.getPermissions());
        } else
            throw new IllegalArgumentException("couldn't give permission because the given userId doesn't exist or is not logged in");
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    @PostMapping("/manager")
    public ReturnValue createManager(@RequestParam String storeId, @RequestParam String userIdGiving, @RequestParam String UserGettingPermissionId) throws NoPermissionException, UserException, NotifyException {
        if (getUserController().checkIfUserExists(userIdGiving) && getUserController().checkIfUserExists(UserGettingPermissionId) && getUserController().checkIfUserIsLoggedIn(userIdGiving)) {
            getStoreController().createManager(storeId, userIdGiving, UserGettingPermissionId);
        } else
            throw new IllegalArgumentException("couldn't give permission because the given userId doesn't exist or is not logged in");
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }


    @PostMapping(value = "/product", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ReturnValue addReviewToProduct(@Valid @RequestBody MockProductReview mockProd) throws NoPermissionException, SupplyManagementException, UserException {
        userExistsAndLoggedIn(mockProd.getUserId());
        getStoreController().addReviewToProduct(mockProd.getStoreId(), mockProd.getUserId(), mockProd.getProductId(), mockProd.getTitle(), mockProd.getBody(), mockProd.getRating());
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    @PostMapping("/forum/thread")
    public ReturnValue addNewThreadToForum(@RequestParam String storeId, @RequestParam String title, @RequestParam String userId) throws NoPermissionException, UserException {
        userExistsAndLoggedIn(userId);


        ReturnValue rv = new ReturnValue(true, "", getStoreController().addNewThreadToForum(storeId, title, userId));
        return rv;
    }

    /**
     * @param usersIds - create stores for users and add products for them
     * @return stores ids
     */
    private List<String> initializeStores(List<String> usersIds) throws NoPermissionException, SupplyManagementException {
        List<String> output = new ArrayList<>();
        for (String userId : usersIds) {
            List<String> owner = new ArrayList<>();
            owner.add(userId);
            String StoreId = sc.openNewStore(userId + " store", owner);
            sc.addNewProduct(StoreId, userId, "p1", 1, 1, ProductsCategories.Apps$Games.toString());
            sc.addNewProduct(StoreId, userId, "p2", 2, 2, ProductsCategories.Appliances.toString());
            sc.addNewProduct(StoreId, userId, "p3", 3, 3, ProductsCategories.Other.toString());
            sc.addNewProduct(StoreId, userId, "p4", 4, 4, ProductsCategories.Apps$Games.toString());
            output.add(StoreId);
        }
        return output;
    }

    @PutMapping("/initializeSystem")
    public ReturnValue initializeSystem() throws UserException, SupplyManagementException, NoPermissionException {
        List<String> users = initializeUsers();
        initializeStores(users);
        ReturnValue rv = new ReturnValue(true, "", users);
        return rv;
    }

    @PostMapping("/forum/message")
    public ReturnValue postMessageToForum(@RequestParam String storeId, @RequestParam String threadId, @RequestParam String userId, @RequestParam String message) throws NoPermissionException, NotifyException, UserException {
        userExistsAndLoggedIn(userId);
        getStoreController().RolePostMessageToForum(storeId, threadId, userId, message);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;

    }

    public HashMap<String, List<Product>> getAllProductsAndStoresTest() {

        HashMap<String, List<Product>> ans = getStoreController().getAllProductsAndStores();
        return ans;
    }

    @GetMapping("/store-products/all")
    public ReturnValue getAllProductsAndStoresWeb() throws UserException, SupplyManagementException, NoPermissionException, JsonProcessingException {
        HashMap<String, List<Product>> allProductsAndStores = getStoreController().getAllProductsAndStores();
        List<Object> products = new ArrayList<>();
        List<Object> products2 = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (var entry : allProductsAndStores.entrySet()) {
            for (var product : entry.getValue()) {
                String json = String.format("{\"id\":\"%s\",\"name\":\"%s\",\"price\":\"%s\",\"quantity\":\"%s\",\"category\":\"%s\",\"storeId\":\"%s\"}", product.getId(), product.getName(), product.getPrice(), product.getSupply(), product.getCategory(), entry.getKey());
                products.add(objectMapper.readTree(json));
            }
            products2.add(objectMapper.readTree(String.format("{\"%s\":%s}", entry.getKey(), products)));
            products.clear();
        }
        ReturnValue rv = new ReturnValue(true, "", products2);
        return rv;
    }

    public HashMap<String, List<Product>> getAllProductsAndStores() {
        return sc.getAllProductsAndStores();
    }

    @GetMapping("/products/all")
    public ReturnValue getAllProducts() throws JsonProcessingException {
        HashMap<String, List<Product>> allProductsAndStores = getStoreController().getAllProductsAndStores();
        List<Object> products = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (var entry : allProductsAndStores.entrySet()) {
            for (var product : entry.getValue()) {
                String json = String.format("{\"id\":\"%s\",\"name\":\"%s\",\"price\":\"%s\",\"quantity\":\"%s\",\"category\":\"%s\",\"storeId\":\"%s\"}", product.getId(), product.getName(), product.getPrice(), product.getSupply(), product.getCategory(), entry.getKey());
                products.add(objectMapper.readTree(json));
            }
        }
        ReturnValue rv = new ReturnValue(true, "", products);
        return rv;
    }

    @GetMapping("/store/all")
    public ReturnValue getAllStores() {
        HashMap<String, List<Product>> allProductsAndStores = getStoreController().getAllProductsAndStores();
        List<Object> stores = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (var entry : allProductsAndStores.entrySet()) {
            stores.add(entry.getKey());
        }
        ReturnValue rv = new ReturnValue(true, "", stores);
        return rv;
    }

    @GetMapping("/store/products")
    public ReturnValue getStoreProducts(@RequestParam String storeId) throws JsonProcessingException {
        HashMap<String, List<Product>> allProductsAndStores = getStoreController().getAllProductsAndStores();
        List<Object> products = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (var entry : allProductsAndStores.entrySet()) {
            if (entry.getKey().equals(storeId)) {
                for (var product : entry.getValue()) {
                    String json = String.format("{\"id\":\"%s\",\"name\":\"%s\",\"price\":\"%s\",\"quantity\":\"%s\",\"category\":\"%s\",\"storeId\":\"%s\"}", product.getId(), product.getName(), product.getPrice(), product.getSupply(), product.getCategory(), entry.getKey());
                    products.add(objectMapper.readTree(json));
                }
            }

        }
        ReturnValue rv = new ReturnValue(true, "", products);
        return rv;
    }


    @GetMapping("/search/name")
    public ReturnValue SearchProductsAccordingName(@RequestParam String userId, @RequestParam String productName) throws UserException {
        System.out.println("");
        if (!IsGuest(userId))
            userExistsAndLoggedIn(userId);

        ReturnValue rv = new ReturnValue(true, "", getStoreController().SearchProductsAccordingName(productName));

        return rv;
    }


    @GetMapping(value = "/search/category", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public List<Product> SearchProductsAccordingCategory(@RequestParam String userId, @Valid @RequestBody MockCategories mockCategories) throws UserException {

        if (!IsGuest(userId))
            userExistsAndLoggedIn(userId);
        return getStoreController().SearchProductsAccordingCategory(mockCategories.getCategories());

    }


    @GetMapping("/search/price")
    public List<Product> SearchProductsAccordingPrice(@RequestParam String userId, @RequestParam float fromPrice, @RequestParam float toPrice) throws UserException {

        if (!IsGuest(userId))
            userExistsAndLoggedIn(userId);
        return getStoreController().SearchProductsAccordingPrice(fromPrice, toPrice);

    }

    /**
     * @param userId
     * @param productRating - the minimum rating of a product
     * @return
     */
    @GetMapping("/search/rating")
    public List<Product> SearchProductsAccordingRating(@RequestParam String userId, @RequestParam float productRating) throws UserException {

        if (!IsGuest(userId))
            userExistsAndLoggedIn(userId);
        return getStoreController().SearchProductsAccordingRating(productRating);
    }

    @PostMapping("/store/delete")
    public ReturnValue deleteStore(@RequestParam String userId, @RequestParam String storeId) throws NoPermissionException, UserException {
        userExistsAndLoggedIn(userId);
        getStoreController().deleteStore(userId, storeId);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;

    }

    private void userExistsAndLoggedIn(String userId) throws UserException {
        if (!getUserController().checkIfUserExists(userId) || !getUserController().checkIfUserIsLoggedIn(userId)) {
            my_log.warning("User doesn't exist or is not logged in");
            throw new IllegalArgumentException("User doesn't exist or is not logged in");

        }
    }


    @GetMapping("/history/store")
    public ReturnValue getStoreHistory(@RequestParam String storeId, @RequestParam String userId) throws NoPermissionException, UserException {
        userExistsAndLoggedIn(userId);

        ReturnValue rv = new ReturnValue(true, "", sc.getStoreHistory(storeId, userId));
        return rv;
    }

    @GetMapping("/history/user")
    public ReturnValue getUserHistory(@RequestParam String userId) throws NoPermissionException, UserException {
        userExistsAndLoggedIn(userId);

        ReturnValue rv = new ReturnValue(true, "", History.getInstance().getUserHistory(userId));
        return rv;
    }

    @GetMapping("/history/store/user")
    public ReturnValue getStoreUserHistory(@RequestParam String userIdRequesting,
                                           @RequestParam String storeId,
                                           @RequestParam String userId) throws NoPermissionException, UserException {
        userExistsAndLoggedIn(userId);
        ReturnValue rv = new ReturnValue(true, "", sc.getStoreHistory(userIdRequesting, storeId, userId));
        return rv;
    }

    private boolean IsGuest(String userId) {
        for (Guest g : us.getGuest_list()) {
            if (g.name.equals(userId)) {
                return true;
            }
        }
        return false;
    }

    @GetMapping("/permission/type")
    public ReturnValue getPermissionType(@RequestParam String username) {

        ReturnValue rv = new ReturnValue(true, "", getUserController().getPermissionType(username));
        return rv;
    }

    @GetMapping("/notification/complaint")
    public ReturnValue readComplaintNotification(@RequestParam String userId,
                                                 @RequestParam int complaintNotificaionId) throws UserException {
        userExistsAndLoggedIn(userId);
        getUserController().readComplaintNotification(userId, complaintNotificaionId);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }

    @GetMapping("/notification/store/complaint")
    public ReturnValue readStoreNotification(@RequestParam String userId,
                                             @RequestParam int storeNotificaionId) throws UserException {
        userExistsAndLoggedIn(userId);
        getUserController().readStoreNotification(userId, storeNotificaionId);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }


    @PostMapping("/policy/add")
    public ReturnValue addNewPolicy(@RequestParam String storeId, @RequestParam String userId, @RequestParam String typeOfPolicy, @RequestBody MockPolicy mockPolicy) throws Exception {
        Policy policy;
        userExistsAndLoggedIn(userId);
        switch (typeOfPolicy) {
            case "CartPolicy":
                policy = policyBuilder.newCartPolicy(mockPolicy.getNumOfProducts());
                break;
            case "CategoryPolicy":
                policy = policyBuilder.newCategoryPolicy(mockPolicy.getCategories());
                break;
            case "ProductWithoutAmountPolicy":
                //todo get products from guy
                List<PurchasableProduct> lp = new LinkedList<>();
                for (Product p : getProductById(storeId, mockPolicy.getProducts())) {
                    PurchasableProduct pp = p;
                    lp.add(pp);
                }
                policy = policyBuilder.newProductWithoutAmountPolicy(lp);
                break;
            case "ProductWithAmountPolicy":
                HashMap<PurchasableProduct, Integer> mp = new HashMap<>();
                for (String s : mockPolicy.getProductsAmount().keySet()) {

                    Product product = (Product) getProductById(storeId, s).getValue();
                    PurchasableProduct pp = product;
                    mp.put(pp, mockPolicy.getProductsAmount().get(s));
                }
                policy = policyBuilder.newProductWithAmountPolicy(mp);
                break;
            case "UserIdPolicy":
                policy = policyBuilder.newUserIdPolicy(mockPolicy.getUserIds());
                break;
            case "UseAgePolicy":
                policy = policyBuilder.newUseAgePolicy(mockPolicy.getStartAge(), mockPolicy.getEndAge());
                break;
            case "OnHoursOfTheDayPolicy":
                policy = policyBuilder.newOnHoursOfTheDayPolicy(mockPolicy.getStartTime(), mockPolicy.getEndTime());
                break;
            case "OnDaysOfTheWeekPolicy":
                policy = policyBuilder.newOnDaysOfTheWeekPolicy(mockPolicy.getStartTime(), mockPolicy.getEndTime());
                break;
            case "OnDayOfMonthPolicy":
                policy = policyBuilder.newOnDayOfMonthPolicy(mockPolicy.getStartTime(), mockPolicy.getEndTime());
                break;
            case "PricePredicate":
                policy = policyBuilder.newPricePredicate(mockPolicy.getPrice());
                break;
            default:
                throw new IllegalStateException("Unexpected type of policy: " + typeOfPolicy);
        }

        ReturnValue rv = new ReturnValue(true, "", sc.addNewPolicy(storeId, userId, policy));
        return rv;
    }

    @PostMapping("/policy/combine")
    public ReturnValue combineTwoPolicy(@RequestParam String storeId,
                                        @RequestParam String userId,
                                        @RequestParam String typeOfCombination,
                                        @RequestParam String policyID1,
                                        @RequestParam String policyID2) throws NoPermissionException, UserException {

        userExistsAndLoggedIn(userId);
        Policy policy;

        switch (typeOfCombination) {
            case "And":
                policy = policyBuilder.AndGatePolicy(getPolicy(storeId, policyID1), getPolicy(storeId, policyID2));
                break;
            case "Or":
                policy = policyBuilder.OrGatePolicy(getPolicy(storeId, policyID1), getPolicy(storeId, policyID2));
                break;
            case "Cond":
                policy = policyBuilder.ConditioningGatePolicy(getPolicy(storeId, policyID1), getPolicy(storeId, policyID2));
                break;
            default:
                throw new IllegalStateException("Unexpected type of policy: " + typeOfCombination);
        }
        ReturnValue rv = new ReturnValue(true, "", sc.addNewPolicy(storeId, userId, policy));
        return rv;
    }


    public String addNewDiscount(String storeId, String userId, Discount discount) throws NoPermissionException, UserException {// use policyBuilder to create policy
        if (!getUserController().checkIfUserExists(userId) || !getUserController().checkIfUserIsLoggedIn(userId)) {
            my_log.warning("User doesn't exist or is not logged in or is not logged in");
            return null;
        }
        return sc.addNewDiscount(storeId, userId, discount);
    }

    public void deleteDiscount(String storeId, String userId, String discountId) throws NoPermissionException, UserException {
        if (!getUserController().checkIfUserExists(userId) || !getUserController().checkIfUserIsLoggedIn(userId)) {
            my_log.warning("User doesn't exist or is not logged in or is not logged in");
        }
        sc.deleteDiscount(storeId, userId, discountId);
    }

    private boolean checkIfUserHaveRoleInStore(String userId) {
        return sc.checkIfUserHaveRoleInAnyStore(userId);
    }

    @GetMapping("/store/user/OwnerStores")
    public ReturnValue getStoreOwnerBuyUser(@RequestParam String userID){
        ReturnValue rv = new ReturnValue(true, "", sc.getStoreOwnerBuyUser(userID));
        return rv;
    }
    @GetMapping("/store/user/ManagerStores")
    public ReturnValue getStoreManagerBuyUser(@RequestParam String userID){
        ReturnValue rv = new ReturnValue(true, "", sc.getStoreManagerBuyUser(userID));
        return rv;
    }
    @GetMapping("/store/user/ManagerOwnerStores")
    public ReturnValue getStoreManagerOwnerBuyUser(@RequestParam String userID){
        List<Store> managerOf=sc.getStoreManagerBuyUser(userID);
        managerOf.addAll(sc.getStoreOwnerBuyUser(userID));
        List<Store> managerAndOwnerOf = managerOf;
        ReturnValue rv = new ReturnValue(true, "", managerAndOwnerOf);
        return rv;
    }

    @GetMapping("/store/user/Permission")
    public ReturnValue getUserPermissionInStore(@RequestParam String StoreId,
                                                @RequestParam String userId) {

        ReturnValue rv = new ReturnValue(true, "", sc.getUserPermission(StoreId, userId));
        return rv;
    }

    @GetMapping("/store/title")
    public ReturnValue getTitleInStore(String StoreId, String userIf) {
        ReturnValue rv = new ReturnValue(true, "", sc.getTitle(StoreId, userIf));
        return rv;
    }

    @DeleteMapping("/policy")
    public ReturnValue deletePolicy(@RequestParam String storeId,
                                    @RequestParam String userId,
                                    @RequestParam String policyId) throws NoPermissionException, UserException {
        userExistsAndLoggedIn(userId);
        sc.deletePolicy(storeId, userId, policyId);
        ReturnValue rv = new ReturnValue(true, "", null);
        return rv;
    }


    @GetMapping("/title")
    public ReturnValue getTitle(@RequestParam String userId,
                                @RequestParam String StoreId,
                                @RequestParam String userIf) throws UserException {
        userExistsAndLoggedIn(userId);
        ReturnValue rv = new ReturnValue(true, "", sc.getTitle(StoreId, userIf));
        return rv;
    }


    public List<Discount> getDiscounts(String storeId, String userId) throws NoPermissionException, UserException {
        if (!getUserController().checkIfUserExists(userId) || !getUserController().checkIfUserIsLoggedIn(userId)) {
            my_log.warning("User doesn't exist or is not logged in or is not logged in");
            return null;
        }
        return sc.getDiscounts(storeId, userId);
    }

    @GetMapping("/policy")
    public ReturnValue getPolices(@RequestParam String storeId) throws NoPermissionException, UserException {
        ReturnValue rv = new ReturnValue(true, "", sc.getPolices(storeId));
        return rv;
    }

    private Policy getPolicy(String storeId, String policyId) throws NoPermissionException, UserException {
        return sc.getPolicy(storeId, policyId);

    }


    @GetMapping("/stores/all")
    public ReturnValue getAllStoresByStoreName(@RequestParam String userId, @RequestParam String name) throws UserException {
        userExistsAndLoggedIn(userId);
        ReturnValue rv = new ReturnValue(true, "", sc.getAllStoresByStoreName(name));
        return rv;
    }

    public ReturnValue getProductById(String storeId, String productId) throws Exception {

        ReturnValue rv = new ReturnValue(true, "", sc.getProductById(storeId, productId));
        return rv;
    }

    private List<Product> getProductById(String storeId, List<String> productId) {
        return sc.getProductById(storeId, productId);

    }

    public List<Guest> getGuest_list() {
        return us.getGuest_list();
    }

    private UserController getUserController() {
        return us;
    }

    private StoreController getStoreController() {
        return sc;
    }
}
