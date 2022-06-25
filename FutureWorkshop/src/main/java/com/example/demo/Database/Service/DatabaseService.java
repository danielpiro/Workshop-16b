package com.example.demo.Database.Service;
import com.example.demo.CustomExceptions.Exception.ResourceNotFoundException;
import com.example.demo.CustomExceptions.Exception.SupplyManagementException;
import com.example.demo.Database.DTOobjects.Cart.ShoppingBasketDTO;
import com.example.demo.Database.DTOobjects.History.HistoryDTO;
import com.example.demo.Database.DTOobjects.Store.ProductDTO;
import com.example.demo.Database.DTOobjects.Store.ReviewDTO;
import com.example.demo.Database.DTOobjects.User.ComplaintDTO;
import com.example.demo.Database.DTOobjects.User.UserDTO;
import com.example.demo.Database.Repositories.*;
import com.example.demo.History.History;
import com.example.demo.ShoppingCart.ShoppingBasket;
import com.example.demo.ShoppingCart.ShoppingCart;
import com.example.demo.Store.Product;
import com.example.demo.Store.Review;
import com.example.demo.User.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;


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

    public DatabaseService(){
        super();
    }

    @Transactional
    public ProductDTO saveProduct(Product p){
        String productId= p.getId();

        //so we dont save twice
        deleteReviewByProductID(productId);

        //save all the reviews of the product
        for (Review r : p.getReviews()){
            saveReviewByProduct(r,productId);
        }

        return productRepository.saveAndFlush(p.productToDTO());
    }

    @Transactional
    public ReviewDTO saveReviewByProduct(Review r, String productId){

        //save the review with reference to its product
        ReviewDTO reviewDTO = r.reviewToDTO();
        reviewDTO.setProductId(productId);
        return reviewRepository.saveAndFlush(reviewDTO);
    }


    @Transactional
    public Product getProductByID(String productID) throws ResourceNotFoundException, SupplyManagementException {
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

    public void saveComplaint(ComplaintDTO complaintDTO){
        complaintRepository.saveAndFlush(complaintDTO);
    }
    public void deleteComplaint(ComplaintDTO complaintDTO){
        complaintRepository.delete(complaintDTO);
    }
    public List<ComplaintDTO> findComplaintByUserId (String userID){
        return complaintRepository.findByUserID(userID);
    }






}
