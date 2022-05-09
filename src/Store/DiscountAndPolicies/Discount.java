package Store.DiscountAndPolicies;

import Store.Store;

import java.util.HashMap;
import java.util.List;

public interface Discount {
    HashMap<String, Integer> checkIfDiscountApply(HashMap<String, Integer> ProductAmount);// return the products that include in discount
    float applyDiscount(HashMap<String, Integer> ProductAmount); // return the price after discount
}
