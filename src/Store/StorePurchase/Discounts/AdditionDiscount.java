package Store.StorePurchase.Discounts;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.UserInfo;
import Store.StorePurchase.PurchasableProduct;

import java.util.ArrayList;
import java.util.List;

public class AdditionDiscount implements Discount {
    PercentageDiscount left;
    PercentageDiscount right;

    public AdditionDiscount(PercentageDiscount left, PercentageDiscount right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public List<PurchasableProduct> applyDiscount(List<PurchasableProduct> productsAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
        List<PurchasableProduct> productsAfterDiscount = new ArrayList<>();
        float newPrice;
        for (PurchasableProduct pp : productsAmount){
            float finalPercentage = 100;
            if(left.getDiscountOnPredicate().predicateStands(productsAmount,externalConnectionHolder,userInfo)&&
                    right.getDiscountOnPredicate().predicateStands(productsAmount,externalConnectionHolder,userInfo)
            ) {
                finalPercentage = left.getDiscountPercent()+right.getDiscountPercent();
            }
            else if(left.getDiscountOnPredicate().predicateStands(productsAmount,externalConnectionHolder,userInfo)){
                finalPercentage = left.getDiscountPercent();
            }
            else if(right.getDiscountOnPredicate().predicateStands(productsAmount,externalConnectionHolder,userInfo)){
                finalPercentage = right.getDiscountPercent();
            }
            newPrice = pp.getPrice() * (finalPercentage / 100);
            productsAfterDiscount.add(new DiscountBox(pp, newPrice, pp.getAmount()));

        }

        return productsAfterDiscount;
    }
}
