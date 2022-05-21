package Store.StorePurchase.Discounts;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.UserInfo;
import Store.StorePurchase.PurchasableProduct;

import java.util.List;

public class MaxDiscount implements Discount{
    Discount left;
    Discount right;

    public MaxDiscount(Discount left, Discount right) {
        this.left = left;
        this.right = right;
    }


    @Override
    public List<PurchasableProduct> applyDiscount(List<PurchasableProduct> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) {
        List<PurchasableProduct> leftAfterDiscount = left.applyDiscount(ProductAmount,externalConnectionHolder,userInfo);
        List<PurchasableProduct> rightAfterDiscount = right.applyDiscount(ProductAmount,externalConnectionHolder,userInfo);
        float leftTotalPrice = 0 ;
        for(PurchasableProduct pp: leftAfterDiscount){
            leftTotalPrice += (pp.getPrice()*(float) pp.getAmount());
        }
        float rightTotalPrice = 0 ;
        for(PurchasableProduct pp: rightAfterDiscount){
            rightTotalPrice += (pp.getPrice()*(float) pp.getAmount());
        }
        if(leftTotalPrice > rightTotalPrice)
            return rightAfterDiscount;
        return leftAfterDiscount;
    }

}
