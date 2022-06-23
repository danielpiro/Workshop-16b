package com.example.demo.Store.StorePurchase.predicates.PredImplementions.compsite;

import com.example.demo.ExternalConnections.Old.ExternalConnectionHolder;
import com.example.demo.ShoppingCart.UserInfo;
import com.example.demo.Store.StorePurchase.PurchasableProduct;
import com.example.demo.Store.StorePurchase.predicates.PolicyPredicate;

import java.util.List;

public class PolicyPredicateXor implements PolicyPredicate {
    PolicyPredicate p1;
    PolicyPredicate p2;

    public PolicyPredicateXor(PolicyPredicate p1, PolicyPredicate p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public boolean predicateStands(List<PurchasableProduct> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
        return p1.predicateStands(ProductAmount,externalConnectionHolder,userInfo)^
                p2.predicateStands(ProductAmount,externalConnectionHolder,userInfo);
    }

    public PolicyPredicate getPolicyPredicate1() {
        return p1;
    }

    public PolicyPredicate getPolicyPredicate2() {
        return p2;
    }

    @Override
    public String toString() {
        return "PolicyPredicateXor{" +
                "p1=" + p1 +
                ", p2=" + p2 +
                '}';
    }
}
