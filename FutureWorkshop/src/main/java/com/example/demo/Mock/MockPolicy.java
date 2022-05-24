package com.example.demo.Mock;

import com.example.demo.Store.ProductsCategories;
import com.example.demo.Store.StorePurchase.PurchasableProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class MockPolicy {

    private Integer numOfProducts;
    private List<ProductsCategories> categories;
    private List<String> products;
    private HashMap<String,Integer> productsAmount;
    private List<String> userIds;
    private Integer startAge;
    private Integer endAge;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Float price;

}
