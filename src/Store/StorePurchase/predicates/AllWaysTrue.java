package Store.StorePurchase.predicates;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.ProductAmount;
import ShoppingCart.UserInfo;
import Store.PurchasableProduct;

import java.util.HashMap;
import java.util.List;

public class AllWaysTrue extends DiscountPredicate implements PolicyPredicate{
    @Override
    public boolean predicateStands(List<PurchasableProduct> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
        return true;
    }
}
