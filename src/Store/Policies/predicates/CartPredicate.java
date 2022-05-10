package Store.Policies.predicates;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.UserInfo;
import Store.Product;

import java.util.HashMap;

public class CartPredicate implements Predicate{
    private int numOfProducts;

    public CartPredicate(int numOfProducts) {
        this.numOfProducts = numOfProducts;
    }

    @Override
    public boolean predicateStands(HashMap<Product, Integer> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
        return numOfProducts<=ProductAmount.values().size();
    }
}
