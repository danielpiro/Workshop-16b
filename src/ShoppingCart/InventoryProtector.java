package ShoppingCart;

import ExternalConnections.PurchasePolicies;

import java.util.HashMap;

public interface InventoryProtector {

    public String getProductName (int productID);

    public int getProductPrice (int productID);

    //if purchase is succefull let the invetory manager know, so that he can remove the items from the inventory and tell store to send,
    //notification to store owners.
    //its by user id, if no items are held do nothing
    //todo Guy add inventory manger
    public void purchaseSuccessful(HashMap<Integer,Integer> ProductAmount , boolean success);

    //return total price of items, if cant purchase return negative number. also send user payment and delivery
    //also will ask with auction items, if any are available, return the price. doesnt need to be great just for this version.
    public int purchase (HashMap<Integer,Integer> ProductAmount , PurchasePolicies purchasePolicies);
}
