package Store.Policies.predicates;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.UserInfo;
import Store.Product;
import Store.ProductsCategories;

import java.util.HashMap;
import java.util.List;

public class CategoryPredicate implements IfPredicate,ThenPredicate{
    List<ProductsCategories> categories;
    @Override
    public boolean predicateStands(HashMap<Product, Integer> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
        for (Product p : ProductAmount.keySet()){
            if(categories.stream().anyMatch(c -> c.toString().equals(p.getCategory().toString()))){
                return true;
            }
        }
        return false;

    }
}
