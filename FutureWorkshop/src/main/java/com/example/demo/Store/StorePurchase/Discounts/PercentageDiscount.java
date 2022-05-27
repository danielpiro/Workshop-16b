package com.example.demo.Store.StorePurchase.Discounts;


import com.example.demo.ExternalConnections.ExternalConnectionHolder;
import com.example.demo.GlobalSystemServices.IdGenerator;
import com.example.demo.ShoppingCart.UserInfo;
import com.example.demo.Store.StorePurchase.PurchasableProduct;
import com.example.demo.Store.StorePurchase.predicates.DiscountPredicate;

import java.util.ArrayList;
import java.util.List;

public class PercentageDiscount implements Discount{//simple discount, for example "discount on all store/category/product/products/price"
    private String id;
    private float discountPercent;
    private DiscountPredicate discountOnPredicate;//discount on what "discount on all store/category/product/products/price"

    public PercentageDiscount(float discountPercent, DiscountPredicate discountOnPredicate) {
        if(discountPercent<0 || discountPercent >100){
            throw new IllegalArgumentException("discount percent is between  0<= percent <= 100");
        }
        this.discountPercent = discountPercent;
        this.discountOnPredicate = discountOnPredicate;
        id = IdGenerator.getInstance().getDiscountId();
    }
    @Override
    public  List<PurchasableProduct> applyDiscount(List<PurchasableProduct> productsAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo){
        List<PurchasableProduct> productsAfterDiscount = new ArrayList<>();
        float newPrice;
        for (PurchasableProduct pp:productsAmount){

            if(discountOnPredicate.predicateStandsForProduct(pp)) {
                newPrice = pp.getPrice() * ((100-discountPercent) / 100);
            }
            else {
                newPrice =  pp.getPrice();
            }
            productsAfterDiscount.add(new DiscountBox(pp, newPrice, pp.getAmount()));

        }
        return productsAfterDiscount;
    }

    @Override
    public String getDiscountId() {
        return id;
    }


    public float getDiscountPercentage() {
        return discountPercent;
    }



    public DiscountPredicate getDiscountOnPredicate() {
        return discountOnPredicate;
    }
}
