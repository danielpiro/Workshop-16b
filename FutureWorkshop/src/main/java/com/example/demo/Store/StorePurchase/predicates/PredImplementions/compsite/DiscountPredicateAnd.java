package com.example.demo.Store.StorePurchase.predicates.PredImplementions.compsite;

import com.example.demo.ExternalConnections.ExternalConnectionHolder;
import com.example.demo.ShoppingCart.UserInfo;
import com.example.demo.Store.StorePurchase.PurchasableProduct;
import com.example.demo.Store.StorePurchase.predicates.DiscountPredicate;


import java.util.List;


public class DiscountPredicateAnd implements DiscountPredicate {
    DiscountPredicate left;
    DiscountPredicate right;

    public DiscountPredicateAnd(DiscountPredicate left, DiscountPredicate right) {
        this.left = left;
        this.right = right;
    }



    @Override
    public boolean predicateStandsForProduct(PurchasableProduct ProductAmount) {
        return left.predicateStandsForProduct(ProductAmount) && right.predicateStandsForProduct(ProductAmount);
    }

    @Override
    public String toString() {
        return "DiscountPredicateAnd{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}
