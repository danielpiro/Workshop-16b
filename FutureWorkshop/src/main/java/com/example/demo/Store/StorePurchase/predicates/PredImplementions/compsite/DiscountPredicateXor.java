package com.example.demo.Store.StorePurchase.predicates.PredImplementions.compsite;


import com.example.demo.ExternalConnections.ExternalConnectionHolder;
import com.example.demo.ShoppingCart.UserInfo;
import com.example.demo.Store.StorePurchase.PurchasableProduct;
import com.example.demo.Store.StorePurchase.predicates.DiscountPredicate;


import java.util.List;

public class DiscountPredicateXor implements DiscountPredicate {
    DiscountPredicate left;
    DiscountPredicate right;

    public DiscountPredicateXor(DiscountPredicate left, DiscountPredicate right) {
        this.left = left;
        this.right = right;
    }



    @Override
    public boolean predicateStandsForProduct(PurchasableProduct ProductAmount) {
        return left.predicateStandsForProduct(ProductAmount) ^ right.predicateStandsForProduct(ProductAmount);
    }
}
