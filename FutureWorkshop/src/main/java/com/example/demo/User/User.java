package com.example.demo.User;


import com.example.demo.CustomExceptions.Exception.CantPurchaseException;
import com.example.demo.CustomExceptions.Exception.StorePolicyViolatedException;
import com.example.demo.CustomExceptions.Exception.SupplyManagementException;
import com.example.demo.ExternalConnections.Old.ExternalConnectionHolder;
import com.example.demo.ShoppingCart.InventoryProtector;
import com.example.demo.ShoppingCart.ShoppingCart;

public abstract class User {
    public String name;
    private ShoppingCart shoppingCart;
    private String sessionId;



    public User(String name){
    shoppingCart = new ShoppingCart(name);

    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }


    public boolean containsStore(String storeID) {
        return shoppingCart.containsStore(storeID);
    }

    public void removeProduct(String productID, String storeID, int amount) {
        shoppingCart.removeProduct(productID, storeID, amount);
    }

    public void addProduct(String productID, String storeID, int amount, InventoryProtector inventoryProtector, boolean auctionOrBid) {
     shoppingCart.addProduct(productID,storeID,amount,inventoryProtector,auctionOrBid);
 }
    public float purchaseCart(ExternalConnectionHolder externalConnectionHolder,String nameHolder, String address, String city, String country, String zip ,
                              String holder, String cardNumber, String expireDate, int cvv, String id) throws Exception {
     float a = shoppingCart.purchaseCart(externalConnectionHolder, nameHolder,  address,  city,  country,  zip ,
              holder,  cardNumber,  expireDate,  cvv,  id);
     return a;
 }

    public String getCartInventory() {
        return shoppingCart.getCartInventory();
    }

    public float getPriceOfCartAfterDiscount(ExternalConnectionHolder externalConnectionHolder) throws StorePolicyViolatedException {
        return shoppingCart.getPriceOfCartAfterDiscount(externalConnectionHolder);
    }

    public String getSessionId(){return sessionId;}
    public void setSessionId(String sId){
        this.sessionId=sId;
    }



}
