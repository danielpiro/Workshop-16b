package com.example.demo.Database.DTOobjects.Store.Predicates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "all_predicates")
public class AllPredicateDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String predicateType;
    //cartPredicate
    private int cartNumOfProducts;
    //pricePredicate
    private float price;
    //timePredicate
    private String startTime;
    private String endTime;
    private String timeType;

    //userPredicate
    private int startAge;
    private int endAge;


    public AllPredicateDTO(String predicateType, int cartNumOfProducts) {
        this.predicateType = predicateType;
        this.cartNumOfProducts = cartNumOfProducts;

    }
    public AllPredicateDTO(String predicateType, float price) {
        this.predicateType = predicateType;
        this.price = price;
    }
    public AllPredicateDTO(String predicateType, String startTime, String endTime, String timeType) {
        this.predicateType = predicateType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeType = timeType;
    }

    public AllPredicateDTO(String predicateType,int startAge, int endAge) {
        this.predicateType = predicateType;
        this.startAge = startAge;
        this.endAge = endAge;
    }

    public AllPredicateDTO(String predicateType) {
        this.predicateType = predicateType;
    }
}
