package com.example.demo.Database.Service;
import com.example.demo.CustomExceptions.Exception.ResourceNotFoundException;
import com.example.demo.CustomExceptions.Exception.SupplyManagementException;
import com.example.demo.Database.DTOobjects.ProductDTO;
import com.example.demo.Database.DTOobjects.ReviewDTO;
import com.example.demo.Database.Repositories.ProductRepository;
import com.example.demo.Database.Repositories.ReviewRepository;
import com.example.demo.Store.Product;
import com.example.demo.Store.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Service
public class DatabaseService {


    //todo add transactional
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

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
            saveReview(r,productId);
        }

        return productRepository.saveAndFlush(p.productToDTO());
    }

    public ReviewDTO saveReview (Review r,String productId){

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

}
