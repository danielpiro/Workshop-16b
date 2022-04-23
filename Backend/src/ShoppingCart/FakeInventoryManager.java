package ShoppingCart;

import java.util.HashMap;

public class FakeInventoryManager implements InventoryProtector {
    @Override
    public String getProductName(int productID) {
        if(productID==1)
            return "dan";
        if(productID==2)
            return "guy";
        else
            return "amit";
    }

    @Override
    public int purchase(HashMap<Integer, Integer> ProductAmount, PurchasePolicies purchasePolicies) {
        return 10;
    }
}
