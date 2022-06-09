package com.example.demo.Database.Service;
import com.example.demo.CustomExceptions.Exception.ResourceNotFoundException;
import com.example.demo.CustomExceptions.Exception.SupplyManagementException;
import com.example.demo.Database.DTOobjects.Cart.ShoppingBasketDTO;
import com.example.demo.Database.DTOobjects.Store.ProductDTO;
import com.example.demo.Database.DTOobjects.Store.ReviewDTO;
import com.example.demo.Database.Repositories.BasketRepository;
import com.example.demo.Database.Repositories.ProductRepository;
import com.example.demo.Database.Repositories.ReviewRepository;
import com.example.demo.ShoppingCart.ShoppingBasket;
import com.example.demo.ShoppingCart.ShoppingCart;
import com.example.demo.Store.Product;
import com.example.demo.Store.Review;
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

    public ReviewDTO saveReviewByProduct(Review r, String productId){

        //save the review with reference to its product
        ReviewDTO reviewDTO = r.reviewToDTO();
        reviewDTO.setProductId(productId);
        return reviewRepository.saveAndFlush(reviewDTO);
    }



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

}
