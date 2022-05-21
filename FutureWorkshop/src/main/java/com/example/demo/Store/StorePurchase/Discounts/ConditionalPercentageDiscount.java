package com.example.demo.Store.StorePurchase.Discounts;

import com.example.demo.ExternalConnections.ExternalConnectionHolder;
import com.example.demo.ShoppingCart.UserInfo;
import com.example.demo.Store.StorePurchase.PurchasableProduct;
import com.example.demo.Store.StorePurchase.predicates.DiscountPredicate;


import java.util.List;

public class ConditionalPercentageDiscount implements   Discount {
      PercentageDiscount discount;
    DiscountPredicate pred; //discount only if predicate stands on Products examples " (DiscountPredicate = "purchase is above 200")(PercentageDiscount = " 5% on all fruits") "

    public ConditionalPercentageDiscount(  PercentageDiscount discount, DiscountPredicate pred) {
        this.discount = discount;
        this.pred = pred;
    }

    @Override
    public List<PurchasableProduct> applyDiscount(List<PurchasableProduct> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
        if(pred.predicateStands(ProductAmount,externalConnectionHolder,userInfo)){
            return discount.applyDiscount(ProductAmount,externalConnectionHolder,userInfo);
        }
        return ProductAmount;
    }
}
