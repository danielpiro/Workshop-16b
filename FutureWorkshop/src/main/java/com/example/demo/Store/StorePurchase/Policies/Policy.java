package com.example.demo.Store.StorePurchase.Policies;



import com.example.demo.ExternalConnections.Old.ExternalConnectionHolder;
import com.example.demo.GlobalSystemServices.IdGenerator;
import com.example.demo.ShoppingCart.UserInfo;
import com.example.demo.Store.Product;

import java.util.HashMap;

public abstract class Policy {
    private String policyId;

    public Policy() {
        this.policyId = IdGenerator.getInstance().getPolicyId();
    }

    public abstract boolean checkIfPolicyStands(HashMap<Product, Integer> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo);
    public String getPolicyId(){
        return policyId;
    }


}
