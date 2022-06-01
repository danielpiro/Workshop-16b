package com.example.demo.Store.StorePurchase.predicates.PredImplementions;


import com.example.demo.ExternalConnections.ExternalConnectionHolder;
import com.example.demo.ShoppingCart.UserInfo;
import com.example.demo.Store.StorePurchase.PurchasableProduct;
import com.example.demo.Store.StorePurchase.predicates.PolicyPredicate;


import java.util.List;

/**
 * checks number of product in cart
 */
public class CartPredicate implements PolicyPredicate {
    private int numOfProducts;

    public CartPredicate(int numOfProducts) {
        this.numOfProducts = numOfProducts;
    }

    @Override
    public boolean predicateStands(List<PurchasableProduct> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
        return numOfProducts<= ProductAmount.size();
    }

    @Override
    public String toString() {
        return "CartPredicate{" +
                "MinimumNumOfProducts=" + numOfProducts +
                '}';
    }
}
