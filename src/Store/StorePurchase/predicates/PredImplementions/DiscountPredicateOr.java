package Store.StorePurchase.predicates.PredImplementions;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.UserInfo;
import Store.StorePurchase.PurchasableProduct;
import Store.StorePurchase.predicates.DiscountPredicate;

import java.util.List;

public class DiscountPredicateOr implements DiscountPredicate {

    DiscountPredicate left;
    DiscountPredicate right;

    public DiscountPredicateOr(DiscountPredicate left, DiscountPredicate right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean predicateStands(List<PurchasableProduct> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
        return left.predicateStands(ProductAmount,externalConnectionHolder,userInfo) || right.predicateStands(ProductAmount,externalConnectionHolder,userInfo);
    }
}
