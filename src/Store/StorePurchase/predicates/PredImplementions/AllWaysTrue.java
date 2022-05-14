package Store.StorePurchase.predicates.PredImplementions;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.UserInfo;
import Store.StorePurchase.PurchasableProduct;
import Store.StorePurchase.predicates.DiscountPredicate;
import Store.StorePurchase.predicates.PolicyPredicate;

import java.util.List;

public class AllWaysTrue  implements PolicyPredicate, DiscountPredicate {
    @Override
    public boolean predicateStands(List<PurchasableProduct> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
        return true;
    }
}
