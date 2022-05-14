package Store.StorePurchase.predicates;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.UserInfo;
import Store.StorePurchase.PurchasableProduct;

import java.util.List;

public interface Predicate {//todo add store policy of the original owner and for them not to collide
    boolean predicateStands(List<PurchasableProduct> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo);
}
