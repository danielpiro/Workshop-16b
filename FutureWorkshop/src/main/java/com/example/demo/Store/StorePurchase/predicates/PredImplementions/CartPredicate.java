package main.java.com.example.demo.Store.StorePurchase.predicates.PredImplementions;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.UserInfo;
import main.java.com.example.demo.Store.StorePurchase.PurchasableProduct;
import main.java.com.example.demo.Store.StorePurchase.predicates.PolicyPredicate;

import java.util.List;

public class CartPredicate implements PolicyPredicate {
    private int numOfProducts;

    public CartPredicate(int numOfProducts) {
        this.numOfProducts = numOfProducts;
    }

    @Override
    public boolean predicateStands(List<PurchasableProduct> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
        return numOfProducts<= ProductAmount.size();
    }



}
