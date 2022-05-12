package Store.StorePurchase.predicates;

import ShoppingCart.ProductAmount;
import Store.Product;
import Store.PurchasableProduct;

import java.util.HashMap;
import java.util.List;

public abstract class DiscountPredicate implements Predicate {
    public boolean predicateStands(List<PurchasableProduct> Product){
        return predicateStands(Product, null, null);//todo find better solution then null
    }
}
