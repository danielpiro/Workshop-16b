package ShoppingCart;

import ExternalConnections.PurchasePolicies;

import java.util.HashMap;

public class FakeInventoryManager  {




    public String getProductName(String productID) {
        if(productID=="1")
            return "dan";
        if(productID=="2")
            return "guy";
        else
            return "amit";
    }

    public float getProductPrice(String productID) {
        if(productID=="1")
            return 1;
        if(productID=="1")
            return 5;
        else
            return 10;
    }


    public void purchaseSuccessful(HashMap<String, Integer> ProductAmount, boolean success) {

    }

    
    public float purchase(HashMap<String, Integer> ProductAmount, PurchasePolicies purchasePolicies) {
        return 0;
    }
}