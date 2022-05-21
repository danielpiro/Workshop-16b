package main.java.com.example.demo.Store.StorePurchase.predicates.PredImplementions;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.UserInfo;
import main.java.com.example.demo.Store.StorePurchase.PurchasableProduct;
import main.java.com.example.demo.Store.StorePurchase.predicates.DiscountPredicate;

import java.util.List;

public class DiscountPredicateXor implements DiscountPredicate {
    DiscountPredicate left;
    DiscountPredicate right;

    public DiscountPredicateXor(DiscountPredicate left, DiscountPredicate right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean predicateStands(List<PurchasableProduct> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
        return left.predicateStands(ProductAmount,externalConnectionHolder,userInfo) ^ right.predicateStands(ProductAmount,externalConnectionHolder,userInfo);
    }
}
