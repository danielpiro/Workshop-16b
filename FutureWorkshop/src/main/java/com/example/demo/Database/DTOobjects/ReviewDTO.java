package com.example.demo.Database.DTOobjects;


import com.example.demo.Store.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@Setter
@NoArgsConstructor
@Data
@Entity
@Table(name = "review")
public class ReviewDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private float rating;

    @Column(name = "user_id")
    private String userId;


    private String title;
    private String body;
    @Column(name="product_id")
    private String productId;


    @Column(name="store_id")
    private String storeId;


    public ReviewDTO(float rating, String userId, String title, String body) {
        this.rating = rating;
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public Review reviewDtoToReview(){
        return new Review(rating,userId,title,body);
    }

}
