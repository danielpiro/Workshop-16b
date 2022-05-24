package com.example.demo.Store.StorePurchase.Discounts;


import com.example.demo.ExternalConnections.ExternalConnectionHolder;
import com.example.demo.GlobalSystemServices.IdGenerator;
import com.example.demo.ShoppingCart.UserInfo;
import com.example.demo.Store.StorePurchase.PurchasableProduct;

import java.util.ArrayList;
import java.util.List;

public class AdditionDiscount implements   Discount {
    private String id;
    private  PercentageDiscount left;
    private  PercentageDiscount right;

    public AdditionDiscount(  PercentageDiscount left,   PercentageDiscount right) {
        this.left = left;
        this.right = right;
        id = IdGenerator.getInstance().getDiscountId();
    }

    @Override
    public List<PurchasableProduct> applyDiscount(List<   PurchasableProduct> productsAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
        List<   PurchasableProduct> productsAfterDiscount = new ArrayList<>();
        float newPrice;
        for (   PurchasableProduct pp : productsAmount){
            float finalPercentage = 100;
            if(left.getDiscountOnPredicate().predicateStands(productsAmount,externalConnectionHolder,userInfo)&&
                    right.getDiscountOnPredicate().predicateStands(productsAmount,externalConnectionHolder,userInfo)
            ) {
                finalPercentage = left.getDiscountPercent()+right.getDiscountPercent();
            }
            else if(left.getDiscountOnPredicate().predicateStands(productsAmount,externalConnectionHolder,userInfo)){
                finalPercentage = left.getDiscountPercent();
            }
            else if(right.getDiscountOnPredicate().predicateStands(productsAmount,externalConnectionHolder,userInfo)){
                finalPercentage = right.getDiscountPercent();
            }
            newPrice = pp.getPrice() * (finalPercentage / 100);
            productsAfterDiscount.add(new   DiscountBox(pp, newPrice, pp.getAmount()));

        }

        return productsAfterDiscount;
    }

    @Override
    public String getDiscountId() {
        return id;
    }
}
