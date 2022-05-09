package Store.Policies.predicates;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.UserInfo;
import Store.Product;

import java.util.HashMap;

public interface Predicate {//todo להוסיף עילוצי עקביות ולדאוג שפרדיקט לא סוטר אותן
    boolean predicateStands(HashMap<Product, Integer> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo);
}
