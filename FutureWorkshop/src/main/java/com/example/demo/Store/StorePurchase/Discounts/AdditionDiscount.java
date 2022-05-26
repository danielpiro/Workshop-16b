package com.example.demo.Store.StorePurchase.Discounts;


import com.example.demo.ExternalConnections.ExternalConnectionHolder;
import com.example.demo.GlobalSystemServices.IdGenerator;
import com.example.demo.ShoppingCart.UserInfo;
import com.example.demo.Store.StorePurchase.PurchasableProduct;
import com.example.demo.Store.StorePurchase.predicates.DiscountPredicate;
import com.example.demo.Store.StorePurchase.predicates.PredImplementions.compsite.DiscountPredicateAnd;

import java.util.ArrayList;
import java.util.List;

public class AdditionDiscount implements   Discount {
    private String id;

    private PercentageDiscount percentageDiscount;

    public AdditionDiscount(  PercentageDiscount first,   PercentageDiscount second) {
        float discountPercentAdded = first.getDiscountPercent()+ second.getDiscountPercent();
        DiscountPredicate discountOnPredicateOfTwoDiscounts =new DiscountPredicateAnd( first.getDiscountOnPredicate(),second.getDiscountOnPredicate());
        this.percentageDiscount = new PercentageDiscount(discountPercentAdded,discountOnPredicateOfTwoDiscounts);
        id = IdGenerator.getInstance().getDiscountId();
    }

    @Override
    public List<PurchasableProduct> applyDiscount(List<   PurchasableProduct> productsAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
        return percentageDiscount.applyDiscount(productsAmount,externalConnectionHolder,userInfo);
    }

    @Override
    public String getDiscountId() {
        return id;
    }
}
