package com.example.demo.Store.StorePurchase.Discounts;

import com.example.demo.Store.StorePurchase.predicates.DiscountPredicate;
import com.example.demo.Store.StorePurchase.predicates.PolicyPredicate;
import com.example.demo.Store.StorePurchase.predicates.PredImplementions.AlwaysTrue;

public class DiscountBuilder {

    public PercentageDiscount newPercentageDiscount(float discountPercent,String predicateJson){
        return new PercentageDiscount(discountPercent, parseProductPredicate(predicateJson));
    }
    private DiscountPredicate parseProductPredicate(String predicateJson){
        return new AlwaysTrue();
    }
    private PolicyPredicate parseCartPredicate(String predicateJson){
        return new AlwaysTrue();
    }

    public ConditionalPercentageDiscount newConditionalDiscount(float discountPercent,String cartPredicateJson, String productPredicateJson){
        PercentageDiscount percentageDiscount = new PercentageDiscount(discountPercent,parseProductPredicate(productPredicateJson));
        return new ConditionalPercentageDiscount(percentageDiscount,parseCartPredicate(cartPredicateJson));
    }
    public AdditionDiscount newAdditionDiscount(  PercentageDiscount first,   PercentageDiscount second){
        return newAdditionDiscount(first,second);
    }

    public MaxDiscount newMaxDiscount( PercentageDiscount first,   PercentageDiscount second){
        return newMaxDiscount(first,second);
    }
}
