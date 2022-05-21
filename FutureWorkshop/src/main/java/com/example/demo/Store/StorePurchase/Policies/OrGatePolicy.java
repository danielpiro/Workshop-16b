package com.example.demo.Store.StorePurchase.Policies;


import com.example.demo.ExternalConnections.ExternalConnectionHolder;
import com.example.demo.ShoppingCart.UserInfo;
import com.example.demo.Store.Product;

import java.util.HashMap;

public class OrGatePolicy extends Policy {
    Policy left;
    Policy right;

    public OrGatePolicy(Policy left, Policy right) {
        super();
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean checkIfPolicyStands(HashMap<Product, Integer> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
        return left.checkIfPolicyStands(ProductAmount,externalConnectionHolder,userInfo) || right.checkIfPolicyStands(ProductAmount,externalConnectionHolder,userInfo);
    }
}
