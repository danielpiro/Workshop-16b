package Store.StorePurchase.Discounts;

import Store.PurchasableProduct;
import Store.StorePurchase.predicates.DiscountPredicate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PercentageDiscount implements Discount{
    private float discountPercent;
    private DiscountPredicate discountOnPredicate;

    public List<DiscountBox> applyDiscount(List<PurchasableProduct> productsAmount){
        List<DiscountBox> productsAfterDiscount = new ArrayList<>();
        for (PurchasableProduct pp:productsAmount){
            if(discountOnPredicate.predicateStands(productsAmount)) {
                float newPrice = pp.getPrice() * (discountPercent / 100);
                productsAfterDiscount.add(new DiscountBox(pp, newPrice, pp.getAmount()));
            }
        }
        return productsAfterDiscount;
    }


}
