package Store.StorePurchase.Discounts;

import ShoppingCart.ProductAmount;
import Store.PurchasableProduct;

import java.util.HashMap;
import java.util.List;

public interface Discount {
    List<DiscountBox> applyDiscount(List<PurchasableProduct> ProductAmount); // return the price after discount
}
