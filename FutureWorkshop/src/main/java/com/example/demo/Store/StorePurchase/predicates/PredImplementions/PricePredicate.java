package com.example.demo.Store.StorePurchase.predicates.PredImplementions;


import com.example.demo.ExternalConnections.Old.ExternalConnectionHolder;
import com.example.demo.ShoppingCart.UserInfo;
import com.example.demo.Store.StorePurchase.PurchasableProduct;
import com.example.demo.Store.StorePurchase.predicates.DiscountPredicate;
import com.example.demo.Store.StorePurchase.predicates.PolicyPredicate;


import java.util.List;

/**
 * for predicate: checks if price of cart is higher than "price"
 * for discount: checks if price of product is higher than "price"
 */
public class PricePredicate implements DiscountPredicate, PolicyPredicate {
    private float price;

    public PricePredicate(float price) {
        this.price = price;
    }

    @Override
    public boolean predicateStands(List<PurchasableProduct> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
        float totalPrice = 0 ;
        for(PurchasableProduct pp: ProductAmount){
            totalPrice += (pp.getPrice()*(float) pp.getAmount());
        }
        return price<totalPrice;
    }

    @Override
    public boolean predicateStandsForProduct(PurchasableProduct ProductAmount) {
        return price<ProductAmount.getPrice();
    }

    @Override
    public String toString() {
        return "PricePredicate{" +
                "price=" + price +
                '}';
    }
}
