package Store.StorePurchase.predicates;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.ProductAmount;
import ShoppingCart.UserInfo;
import Store.Product;
import Store.ProductsCategories;
import Store.PurchasableProduct;

import java.util.HashMap;
import java.util.List;

public class CategoryPredicate extends DiscountPredicate implements PolicyPredicate {
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
