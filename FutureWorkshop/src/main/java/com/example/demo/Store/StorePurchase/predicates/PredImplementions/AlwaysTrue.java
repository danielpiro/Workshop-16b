package main.java.com.example.demo.Store.StorePurchase.predicates.PredImplementions;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.UserInfo;
import main.java.com.example.demo.Store.StorePurchase.PurchasableProduct;
import main.java.com.example.demo.Store.StorePurchase.predicates.DiscountPredicate;
import main.java.com.example.demo.Store.StorePurchase.predicates.PolicyPredicate;

import java.util.List;

public class AlwaysTrue implements PolicyPredicate, DiscountPredicate {
    @Override
    public boolean predicateStands(List<PurchasableProduct> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
        return true;
    }


}
