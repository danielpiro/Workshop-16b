package User;

import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.ShoppingCart;
import ShoppingCart.InventoryProtector;


public abstract class User {
    public String name;
    private ShoppingCart shoppingCart;

 public User(String name){
    shoppingCart = new ShoppingCart(name);

    }


    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }
    public void setShoppingCart(ShoppingCart shoppingCart) {
     this.shoppingCart = shoppingCart;
    }


    public boolean containsStore(String storeID){
     return shoppingCart.containsStore(storeID);
 }

    public int removeProduct(String productID, String storeID, int amount){
        return shoppingCart.removeProduct(productID,storeID,amount);
    }

    public int addProduct(String productID, String storeID, int amount, InventoryProtector inventoryProtector, boolean auctionOrBid) {
    return shoppingCart.addProduct(productID,storeID,amount,inventoryProtector,auctionOrBid);
 }
    public String getCartInventory() {
     return shoppingCart.getCartInventory();
 }
    public float purchaseCart(ExternalConnectionHolder externalConnectionHolder) {
     float a = shoppingCart.purchaseCart(externalConnectionHolder);
     return a;
 }





    }
