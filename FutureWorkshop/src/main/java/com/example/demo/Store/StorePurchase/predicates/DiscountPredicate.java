package com.example.demo.Store.StorePurchase.predicates;

import com.example.demo.Store.StorePurchase.PurchasableProduct;

import java.util.List;

public  interface DiscountPredicate extends Predicate {
    boolean predicateStandsForProduct(PurchasableProduct ProductAmount);
}
