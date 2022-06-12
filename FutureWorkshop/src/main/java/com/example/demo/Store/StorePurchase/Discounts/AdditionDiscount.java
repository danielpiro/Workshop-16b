package com.example.demo.Store.StorePurchase.Discounts;


import com.example.demo.ExternalConnections.ExternalConnectionHolder;
import com.example.demo.GlobalSystemServices.IdGenerator;
import com.example.demo.ShoppingCart.UserInfo;
import com.example.demo.Store.StorePurchase.Policies.Policy;
import com.example.demo.Store.StorePurchase.PurchasableProduct;
import com.example.demo.Store.StorePurchase.predicates.DiscountPredicate;
import com.example.demo.Store.StorePurchase.predicates.PolicyPredicate;
import com.example.demo.Store.StorePurchase.predicates.PredImplementions.compsite.DiscountPredicateAnd;
import com.example.demo.Store.StorePurchase.predicates.PredImplementions.compsite.DiscountPredicateXor;
import com.example.demo.Store.StorePurchase.predicates.PredImplementions.compsite.PolicyPredicateAnd;

import javax.transaction.NotSupportedException;
import java.util.List;

public class AdditionDiscount implements   Discount {
    private String id;

    private ComposableDiscounts AdditionDiscount;
    private ComposableDiscounts originalDiscount1;
    private ComposableDiscounts originalDiscount2;

    public AdditionDiscount(  ComposableDiscounts first,   ComposableDiscounts second) throws NotSupportedException {
        //apply the addition discount percentage to products that stands to the two predicates
        float discountPercentAdded = first.getDiscountPercentage() + second.getDiscountPercentage();

        DiscountPredicate discountOnPredicateOfTwoDiscountsAnd =new DiscountPredicateAnd( first.getDiscountOnProductPredicate(),second.getDiscountOnProductPredicate());
        PolicyPredicate discountOnPolicyPredicateOfTwoDiscountsAnd =new PolicyPredicateAnd( first.getDiscountOnCartPredicate(),second.getDiscountOnCartPredicate());
        this.AdditionDiscount = new ConditionalPercentageDiscount(new PercentageDiscount(discountPercentAdded,discountOnPredicateOfTwoDiscountsAnd),discountOnPolicyPredicateOfTwoDiscountsAnd);

        //apply the discount percentage of the original "first PercentageDiscount"  to products that stands to his predicate but not to both "first PercentageDiscount" and "second PercentageDiscount"
        /** A Xor (A And B) = A and (Not B)      that means that the predicate will stand only for products in {@link #first} and not in {@link #second} */
         DiscountPredicate firstPredicateWithoutAnd =new DiscountPredicateXor( first.getDiscountOnProductPredicate(),discountOnPredicateOfTwoDiscountsAnd);
        this.originalDiscount1 = new ConditionalPercentageDiscount(new PercentageDiscount(first.getDiscountPercentage(),firstPredicateWithoutAnd),first.getDiscountOnCartPredicate());

        //apply the discount percentage of the original "second PercentageDiscount"  to products that stands to his predicate but not to both "first PercentageDiscount" and "second PercentageDiscount"
        DiscountPredicate secondPredicateWithoutAnd =new DiscountPredicateXor( second.getDiscountOnProductPredicate(),discountOnPredicateOfTwoDiscountsAnd);
        this.originalDiscount2 = new ConditionalPercentageDiscount(new PercentageDiscount(second.getDiscountPercentage(),secondPredicateWithoutAnd), second.getDiscountOnCartPredicate());
        id = IdGenerator.getInstance().getDiscountId();
    }

    @Override
    public List<PurchasableProduct> applyDiscount(List<   PurchasableProduct> productsAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
        //apply addition discount on products that stand for the two predicates, then apply discount regular discount for the rest of the other products
        List<PurchasableProduct> productsAfterDiscount = AdditionDiscount.applyDiscount(productsAmount,externalConnectionHolder,userInfo);
        productsAfterDiscount = originalDiscount1.applyDiscount(productsAfterDiscount,externalConnectionHolder,userInfo);
        return originalDiscount2.applyDiscount(productsAfterDiscount,externalConnectionHolder,userInfo);
    }

    @Override
    public String getDiscountId() {
        return id;
    }





}
