package Store.StorePurchase.Discounts;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.UserInfo;
import Store.StorePurchase.PurchasableProduct;

import java.util.List;

public interface Discount {
    List<PurchasableProduct> applyDiscount(List<PurchasableProduct> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo); // return the price after discount
}
