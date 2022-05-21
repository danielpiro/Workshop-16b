package com.example.demo.ShoppingCart;



import com.example.demo.CustomExceptions.Exception.CantPurchaseException;
import com.example.demo.ExternalConnections.ExternalConnectionHolder;

import java.util.HashMap;

public interface InventoryProtector {

    public String getProductName (String productID);

    public float getProductPrice (String productID);

    //if purchase is succefull let the invetory manager know, so that he can remove the items from the inventory and tell store to send,
    //notification to store owners.
    //its by user id, if no items are held do nothing
    public void purchaseSuccessful(HashMap<String,Integer> ProductAmount , boolean success) throws SupplyManagementException;

    //return total price of items, if cant purchase return negative number. also send user payment and delivery
    public float reserve (HashMap<String,Integer> ProductAmount , ExternalConnectionHolder externalConnectionHolder, UserInfo userInfo) throws CantPurchaseException, StorePolicyViolatedException, SupplyManagementException;

    public boolean checkIfProductExist(String productId);
}
