package Store.StorePurchase.predicates;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.ProductAmount;
import ShoppingCart.UserInfo;
import Store.Product;
import Store.PurchasableProduct;

import java.util.HashMap;
import java.util.List;

public class CartPredicate implements PolicyPredicate{
    private int numOfProducts;

    public CartPredicate(int numOfProducts) {
        this.numOfProducts = numOfProducts;
    }

    @Override
    public boolean predicateStands(List<PurchasableProduct> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
        return numOfProducts<= ProductAmount.size();
    }
}
