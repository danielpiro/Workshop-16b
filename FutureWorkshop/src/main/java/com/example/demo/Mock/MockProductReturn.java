package com.example.demo.Mock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
@Setter
@Getter
@AllArgsConstructor
public class MockProductReturn {

    private String Id;
    private String storeId;
    private String productName;
    private Float price;
    private Integer supply;
    private String category;
    private Float rating;
    private Integer amount;

    public MockProductReturn(String id, String storeId, String productName, Float price, Integer supply, String category, Float rating) {
        Id = id;
        this.storeId = storeId;
        this.productName = productName;
        this.price = price;
        this.supply = supply;
        this.category = category;
        this.rating = rating;
    }
    public MockProductReturn(String id, String productName, Float price, Integer supply, String category, Float rating) {
        Id = id;

        this.productName = productName;
        this.price = price;
        this.supply = supply;
        this.category = category;
        this.rating = rating;
    }
}
