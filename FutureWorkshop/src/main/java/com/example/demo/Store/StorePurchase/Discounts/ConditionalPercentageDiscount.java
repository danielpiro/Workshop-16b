package com.example.demo.Store.StorePurchase.Discounts;

import com.example.demo.ExternalConnections.ExternalConnectionHolder;
import com.example.demo.GlobalSystemServices.IdGenerator;
import com.example.demo.ShoppingCart.UserInfo;
import com.example.demo.Store.StorePurchase.PurchasableProduct;
import com.example.demo.Store.StorePurchase.predicates.DiscountPredicate;
import com.example.demo.Store.StorePurchase.predicates.PolicyPredicate;


import java.util.List;

public class ConditionalPercentageDiscount implements Discount {
    private String id;
    private PercentageDiscount discount;
    PolicyPredicate pred; //discount only if predicate stands on cart " (DiscountPredicate = "purchase is above 200")(PercentageDiscount = " 5% on all fruits") "

    public ConditionalPercentageDiscount(  PercentageDiscount discount, PolicyPredicate pred) {
        this.discount = discount;
        this.pred = pred;
        id = IdGenerator.getInstance().getDiscountId();
    }

    @Override
    public List<PurchasableProduct> applyDiscount(List<PurchasableProduct> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
        if(pred.predicateStands(ProductAmount,externalConnectionHolder,userInfo)){
            return discount.applyDiscount(ProductAmount,externalConnectionHolder,userInfo);
        }
        return ProductAmount;
    }

    @Override
    public String getDiscountId() {
        return id;
    }
}
