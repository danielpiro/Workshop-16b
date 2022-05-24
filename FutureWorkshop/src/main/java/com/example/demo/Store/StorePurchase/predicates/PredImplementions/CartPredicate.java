package com.example.demo.Store.StorePurchase.predicates.PredImplementions;


import com.example.demo.ExternalConnections.ExternalConnectionHolder;
import com.example.demo.ShoppingCart.UserInfo;
import com.example.demo.Store.StorePurchase.PurchasableProduct;
import com.example.demo.Store.StorePurchase.predicates.PolicyPredicate;


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
