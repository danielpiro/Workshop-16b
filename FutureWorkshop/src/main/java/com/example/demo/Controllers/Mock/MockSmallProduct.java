package com.example.demo.Controllers.Mock;

import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;

public class MockSmallProduct {

    @NotNull
    private String user_id;

    @NotNull
    private String productID;

    @NotNull
    private String storeID;

    @NotNull
    private Integer amount;

    public MockSmallProduct(String user_id, String productID, String storeID, Integer amount) {
        this.user_id = user_id;
        this.productID = productID;
        this.storeID = storeID;
        this.amount = amount;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getProductID() {
        return productID;
    }

    public String getStoreID() {
        return storeID;
    }

    public Integer getAmount() {
        return amount;
    }
}
