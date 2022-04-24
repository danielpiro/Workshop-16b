package ShoppingCart;

import ExternalConnections.PurchasePolicies;

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
    public int getProductPrice(int productID) {
        if(productID==1)
            return 1;
        if(productID==2)
            return 5;
        else
            return 10;
    }

    @Override
    public void purchaseSuccessful(HashMap<Integer, Integer> ProductAmount, boolean success) {

    }

    @Override
    public float purchase(HashMap<Integer, Integer> ProductAmount, PurchasePolicies purchasePolicies) {
        return 0;
    }


}