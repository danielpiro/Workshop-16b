package Store.StorePurchase.predicates.PredImplementions;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.UserInfo;
import Store.StorePurchase.PurchasableProduct;
import Store.StorePurchase.predicates.DiscountPredicate;
import Store.StorePurchase.predicates.PolicyPredicate;

import java.util.List;

public class PricePredicate implements DiscountPredicate, PolicyPredicate {
    private float price;

    public PricePredicate(float price) {
        this.price = price;
    }

    @Override
    public boolean predicateStands(List<PurchasableProduct> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
        float totalPrice = 0 ;
        for(PurchasableProduct pp: ProductAmount){
            totalPrice += (pp.getPrice()*(float) pp.getAmount());
        }
        return price<totalPrice;
    }
}
