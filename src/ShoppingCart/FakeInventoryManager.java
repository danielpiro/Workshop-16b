package ShoppingCart;

import ExternalConnections.PurchasePolicies;

import java.util.HashMap;

public class FakeInventoryManager implements InventoryProtector {




    @Override
    public String getProductName(String productID) {
        if(productID=="1")
            return "dan";
        if(productID=="2")
            return "guy";
        else
            return "amit";
    }

    @Override
    public float getProductPrice(String productID) {
        if(productID=="1")
            return 1;
        if(productID=="1")
            return 5;
        else
            return 10;
    }

    @Override
    public void purchaseSuccessful(HashMap<String, Integer> ProductAmount, boolean success) {

    }

    @Override
    public float purchase(HashMap<String, Integer> ProductAmount, PurchasePolicies purchasePolicies) {
        return 0;
    }
}