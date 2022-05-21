package main.java.com.example.demo.Store.StorePurchase.Discounts;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.UserInfo;
import main.java.com.example.demo.Store.StorePurchase.PurchasableProduct;
import main.java.com.example.demo.Store.StorePurchase.predicates.DiscountPredicate;

import java.util.ArrayList;
import java.util.List;

public class PercentageDiscount implements Discount{//simple discount, for example "discount on all store/category/product/products/price"
    private float discountPercent;
    private DiscountPredicate discountOnPredicate;//discount on what "discount on all store/category/product/products/price"

    public PercentageDiscount(float discountPercent, DiscountPredicate discountOnPredicate) {
        this.discountPercent = discountPercent;
        this.discountOnPredicate = discountOnPredicate;
    }
    @Override
    public  List<PurchasableProduct> applyDiscount(List<PurchasableProduct> productsAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo){
        List<PurchasableProduct> productsAfterDiscount = new ArrayList<>();
        float newPrice;
        for (PurchasableProduct pp:productsAmount){

            if(discountOnPredicate.predicateStands(productsAmount,externalConnectionHolder,userInfo)) {
                newPrice = pp.getPrice() * (discountPercent / 100);
            }
            else {
                newPrice =  pp.getPrice();
            }
            productsAfterDiscount.add(new DiscountBox(pp, newPrice, pp.getAmount()));

        }

        return productsAfterDiscount;
    }

    public float getDiscountPercent() {
        return discountPercent;
    }

    public DiscountPredicate getDiscountOnPredicate() {
        return discountOnPredicate;
    }
}
