package com.example.demo.Controllers.Mock;

import javax.validation.constraints.NotNull;

public class MockFullProduct {

    @NotNull
    private String storeId;

    @NotNull
    private String userId;

    @NotNull
    private String productName;

    @NotNull
    private Float price;

    @NotNull
    private Integer supply;

    @NotNull
    private String category;

    public MockFullProduct(@NotNull String storeId, @NotNull String userId, @NotNull String productName, @NotNull Float price, @NotNull Integer supply, @NotNull String category) {
        this.storeId = storeId;
        this.userId = userId;
        this.productName = productName;
        this.price = price;
        this.supply = supply;
        this.category = category;
    }

    public String getStoreId() {
        return storeId;
    }

    public String getUserId() {
        return userId;
    }

    public String getProductName() {
        return productName;
    }

    public Float getPrice() {
        return price;
    }

    public Integer getSupply() {
        return supply;
    }

    public String getCategory() {
        return category;
    }
}
