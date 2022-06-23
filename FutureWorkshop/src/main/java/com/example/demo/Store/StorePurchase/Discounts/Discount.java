package com.example.demo.Store.StorePurchase.Discounts;

import com.example.demo.ExternalConnections.Old.ExternalConnectionHolder;
import com.example.demo.ShoppingCart.UserInfo;
import com.example.demo.Store.StorePurchase.PurchasableProduct;

import java.util.List;

public interface Discount {
    List<PurchasableProduct> applyDiscount(List<PurchasableProduct> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo); // return the price after discount

    String getDiscountId();




}
