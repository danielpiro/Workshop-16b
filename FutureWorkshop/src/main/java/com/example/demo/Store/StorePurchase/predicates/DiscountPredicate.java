package com.example.demo.Store.StorePurchase.predicates;

import com.example.demo.Store.StorePurchase.PurchasableProduct;

public abstract interface DiscountPredicate extends Predicate {
    boolean predicateStandsForProduct(PurchasableProduct ProductAmount);
}
