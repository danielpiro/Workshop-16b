package Store.StorePurchase.predicates;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.ProductAmount;
import ShoppingCart.UserInfo;
import Store.Product;
import Store.PurchasableProduct;

import java.util.HashMap;
import java.util.List;

public interface Predicate {//todo add store policy of the original owner and for them not to collide
    boolean predicateStands(List<PurchasableProduct> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo);
}
