package main.java.com.example.demo.Store.StorePurchase.predicates.PredImplementions;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.UserInfo;
import Store.ProductsCategories;
import main.java.com.example.demo.Store.StorePurchase.PurchasableProduct;
import main.java.com.example.demo.Store.StorePurchase.predicates.DiscountPredicate;
import main.java.com.example.demo.Store.StorePurchase.predicates.PolicyPredicate;

import java.util.List;

public class CategoryPredicate  implements PolicyPredicate, DiscountPredicate {
    List<ProductsCategories> categories;

    public CategoryPredicate(List<ProductsCategories> categories) {
        this.categories = categories;
    }

    @Override
    public boolean predicateStands(List<PurchasableProduct> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
        for (PurchasableProduct p : ProductAmount){
            if(categories.stream().anyMatch(c -> c.toString().equals(p.getCategory().toString()))){
                return true;
            }
        }
        return false;

    }




}
