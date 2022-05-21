package ShoppingCart;

import CustomExceptions.CantPurchaseException;
import ExternalConnections.ExternalConnectionHolder;

import java.util.HashMap;

public class FakeInventoryManager implements InventoryProtector  {




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

    @Override
    public float reserve(HashMap<String, Integer> ProductAmount, ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) throws CantPurchaseException {
        return 0;
    }



    @Override
    public boolean checkIfProductExist(String productId) {
        return false;
    }


    public float purchase(HashMap<String, Integer> ProductAmount, ExternalConnectionHolder externalConnectionHolder) {
        return 0;
    }
}