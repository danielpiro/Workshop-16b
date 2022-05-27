package com.example.demo.Store.StorePurchase.predicates;

import com.example.demo.ExternalConnections.ExternalConnectionHolder;
import com.example.demo.ShoppingCart.UserInfo;
import com.example.demo.Store.StorePurchase.PurchasableProduct;

import java.util.List;

public abstract interface DiscountPredicate extends Predicate {
    boolean predicateStandsForProduct(PurchasableProduct ProductAmount);
}
