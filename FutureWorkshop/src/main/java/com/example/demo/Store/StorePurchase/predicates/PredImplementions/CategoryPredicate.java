package com.example.demo.Store.StorePurchase.predicates.PredImplementions;


import com.example.demo.ExternalConnections.ExternalConnectionHolder;
import com.example.demo.ShoppingCart.UserInfo;
import com.example.demo.Store.ProductsCategories;
import com.example.demo.Store.StorePurchase.PurchasableProduct;
import com.example.demo.Store.StorePurchase.predicates.DiscountPredicate;
import com.example.demo.Store.StorePurchase.predicates.PolicyPredicate;

import java.util.List;

public class CategoryPredicate  implements PolicyPredicate, DiscountPredicate {
    List<ProductsCategories> categories;

    public CategoryPredicate(List<ProductsCategories> categories) {
        this.categories = categories;
    }

    @Override
    public boolean predicateStands(List<PurchasableProduct> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
        for(ProductsCategories pc : categories){
            boolean categoryFound = false;
            for(PurchasableProduct p : ProductAmount){
                if(pc.equals(p.getCategory())){
                    categoryFound = true;
                    break;
                }
            }
            if(!categoryFound){
                return false;
            }
        }
        return true;
    }




}
