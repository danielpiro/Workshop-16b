package com.example.demo.Store.StorePurchase.Discounts;


import com.example.demo.ExternalConnections.ExternalConnectionHolder;
import com.example.demo.GlobalSystemServices.IdGenerator;
import com.example.demo.ShoppingCart.UserInfo;
import com.example.demo.Store.StorePurchase.PurchasableProduct;
import com.example.demo.Store.StorePurchase.predicates.DiscountPredicate;
import com.example.demo.Store.StorePurchase.predicates.PredImplementions.compsite.DiscountPredicateAnd;
import com.example.demo.Store.StorePurchase.predicates.PredImplementions.compsite.DiscountPredicateXor;

import javax.transaction.NotSupportedException;
import java.util.List;

public class AdditionDiscount implements   Discount {
    private String id;

    private PercentageDiscount andDiscount;
    private PercentageDiscount originalDiscount1;
    private PercentageDiscount originalDiscount2;

    public AdditionDiscount(  PercentageDiscount first,   PercentageDiscount second) throws NotSupportedException {
        float discountPercentAdded = first.getDiscountPercentage()+ second.getDiscountPercentage();

        DiscountPredicate discountOnPredicateOfTwoDiscountsAnd =new DiscountPredicateAnd( first.getDiscountOnPredicate(),second.getDiscountOnPredicate());
        this.andDiscount = new PercentageDiscount(discountPercentAdded,discountOnPredicateOfTwoDiscountsAnd);

        /** A Xor (A And B) = A and (Not B)      that means that the predicate will stand only for products in {@link #first} and not in {@link #second} */
         DiscountPredicate firstPredicateWithoutAnd =new DiscountPredicateXor( first.getDiscountOnPredicate(),discountOnPredicateOfTwoDiscountsAnd);
        this.originalDiscount1 = new PercentageDiscount(first.getDiscountPercentage(),firstPredicateWithoutAnd);

        DiscountPredicate secondPredicateWithoutAnd =new DiscountPredicateXor( second.getDiscountOnPredicate(),discountOnPredicateOfTwoDiscountsAnd);
        this.originalDiscount2 = new PercentageDiscount(second.getDiscountPercentage(),secondPredicateWithoutAnd);
        id = IdGenerator.getInstance().getDiscountId();
    }

    @Override
    public List<PurchasableProduct> applyDiscount(List<   PurchasableProduct> productsAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
        //apply addition discount on products that stand for the two predicates, then apply discount regular discount for the rest of the other products
        List<PurchasableProduct> productsAfterDiscount = andDiscount.applyDiscount(productsAmount,externalConnectionHolder,userInfo);
        productsAfterDiscount = originalDiscount1.applyDiscount(productsAfterDiscount,externalConnectionHolder,userInfo);
        return originalDiscount2.applyDiscount(productsAfterDiscount,externalConnectionHolder,userInfo);
    }

    @Override
    public String getDiscountId() {
        return id;
    }





}
