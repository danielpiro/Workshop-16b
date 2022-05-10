package Store.Policies.predicates;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.UserInfo;
import Store.Product;

import java.util.HashMap;
import java.util.List;

public class ProductPredicate implements Predicate{
    List<Product> products;

    public ProductPredicate(List<Product> products){
        this.products = products;
    }
    @Override
    public boolean predicateStands(HashMap<Product, Integer> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
        return ProductAmount.keySet().stream().anyMatch(
                product -> products.stream().anyMatch(
                        productsCantBuy -> productsCantBuy.getId().equals(product.getId())
                ));
    }
}
