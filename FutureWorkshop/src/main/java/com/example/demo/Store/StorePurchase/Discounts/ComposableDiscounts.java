package com.example.demo.Store.StorePurchase.Discounts;

import com.example.demo.ExternalConnections.Old.ExternalConnectionHolder;
import com.example.demo.ShoppingCart.UserInfo;
import com.example.demo.Store.StorePurchase.PurchasableProduct;
import com.example.demo.Store.StorePurchase.predicates.DiscountPredicate;
import com.example.demo.Store.StorePurchase.predicates.PolicyPredicate;

import java.util.List;

public interface ComposableDiscounts {
    float getDiscountPercentage();
    List<PurchasableProduct> applyDiscount(List<PurchasableProduct> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo);
    DiscountPredicate getDiscountOnProductPredicate();
    PolicyPredicate getDiscountOnCartPredicate();
}
