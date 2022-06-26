package com.example.demo.Store.StorePurchase.Policies;

import com.example.demo.ExternalConnections.ExternalConnectionHolder;
import com.example.demo.ShoppingCart.UserInfo;
import com.example.demo.Store.Product;
import com.example.demo.Store.StorePurchase.PurchasableProduct;
import com.example.demo.Store.StorePurchase.predicates.PolicyPredicate;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OnePredPolicy extends Policy {
    PolicyPredicate pPredicate;


    public OnePredPolicy(PolicyPredicate pPredicate) {
        super();
        this.pPredicate = pPredicate;
        }

    public OnePredPolicy(String policyId, PolicyPredicate pPredicate) {
        super(policyId);
        this.pPredicate = pPredicate;
    }

    //if ThenPredicate holds function return true you are violating store policy
    public boolean checkIfPolicyStands(HashMap<Product, Integer> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo){
        List<PurchasableProduct> PurchasableAmount = new ArrayList<>();
        for(Product p:ProductAmount.keySet()){
            PurchasableAmount.add(new ProductBox(p,ProductAmount.get(p)));
        }
        return pPredicate.predicateStands(PurchasableAmount,externalConnectionHolder,userInfo);
    }

    @Override
    public String toString() {
        return "OnePredPolicy{" +
                "pPredicate=" + pPredicate +
                '}';
    }
}
