package com.example.demo.ShoppingCart;




import com.example.demo.CustomExceptions.Exception.CantPurchaseException;
import com.example.demo.CustomExceptions.Exception.StorePolicyViolatedException;
import com.example.demo.CustomExceptions.Exception.SupplyManagementException;
import com.example.demo.ExternalConnections.ExternalConnectionHolder;
import com.example.demo.Generic.ThreeGenerics;
import com.example.demo.GlobalSystemServices.Log;
import com.example.demo.History.History;


import java.util.*;

public class ShoppingCart {

    //storeID, and the basket
    private HashMap<String, ShoppingBasket> basketCases;



    private String userId;


    public ShoppingCart(String userId) {
        this.basketCases = new HashMap<String, ShoppingBasket>();
        this.userId=userId;


    }

    public boolean containsStore(String storeID) {
        return basketCases.containsKey(storeID);
    }

    public void removeProduct(String productID, String storeID, int amount) {
        if (basketCases.containsKey(storeID)  ) {
            if (basketCases.get(storeID).removeProduct(productID, amount) >= 0)
                Log.getLogger().fine("user " + userId + "removed product " + productID + "from store " + storeID + "and amount " + amount);
            else {
                Log.getLogger().warning("user " + userId + "could not remove product " + productID + " because the item does not exist in the basket");
                throw new NoSuchElementException( "couldn't remove item because it is not in the basket");
            }
        } else {
            Log.getLogger().warning("user " + userId + "could not remove product " + productID + " because cart does not contain that store");
            throw new NoSuchElementException( "couldn't remove item because it is not in the basket");



        }

    }
    public int removeCompleteyProduct(String productID, String storeID) {
        if (basketCases.containsKey(storeID)) {
            if (basketCases.get(storeID).removeCompleteyProduct(productID) >= 0)
                Log.getLogger().fine("user " + userId + "removed product " + productID + "from store " + storeID + " completly");
            else
                Log.getLogger().warning("user " + userId + "could not remove product " + productID + " because the item does not exist in basket");

        } else {
            Log.getLogger().warning("user " + userId + "could not remove product " + productID + " because cart does not contain that store");
            return -1;

        }
        return 1;


    }




    // to use when we do not have an instance of the store, and need an inventory protector
    public void addProduct(String productID, String storeID, int amount, InventoryProtector inventoryProtector, boolean auctionOrBid) {


        if(!inventoryProtector.checkIfProductExist(productID))
            throw new NoSuchElementException("item doest not exist in the store you specified");


        if (basketCases.containsKey(storeID) && auctionOrBid == false)
            basketCases.get(storeID).addProduct(productID, amount);
        else if(basketCases.containsKey(storeID) && auctionOrBid == true)
            basketCases.get(storeID).addProductAuction(productID, amount);
        else {

            ShoppingBasket sb = new ShoppingBasket(storeID,inventoryProtector);
            if(!auctionOrBid)
                sb.addProduct(productID, amount);
            else
                sb.addProductAuction(productID,amount);
            basketCases.put(storeID, sb);

        }
        Log.getLogger().info("user " + userId +" added product " + productID + "to store " + storeID + " with amount of " + amount);



    }

    public String getCartInventory() {
        StringBuilder sb = new StringBuilder();
        sb.append("showing user's shopping cart:\n");
        for (Map.Entry<String, ShoppingBasket> basket : basketCases.entrySet()) {
            sb.append("store number " + basket.getKey() + "\n");
            sb.append(basket.getValue().getInventory());
        }
        Log.getLogger().fine("user " + userId + " printing cart inventory");

        return sb.toString();
    }


    //if successfull returns price, else returns -1
    public float purchaseCart(ExternalConnectionHolder externalConnectionHolder) throws SupplyManagementException, StorePolicyViolatedException, CantPurchaseException {
        float total=0;
        int weight = 10;
        int ans =0;

        Log.getLogger().info("user " + userId + "trying to purchase Cart");


        //check if we can purchase from store, that items are in inventory and store policies are complied
        try {
            for (Map.Entry<String, ShoppingBasket> basket : basketCases.entrySet()) {
                total += basket.getValue().purchase(externalConnectionHolder,userId);

            }
        }
        catch (  StorePolicyViolatedException |  SupplyManagementException |CantPurchaseException e) {
            Log.getLogger().warning("user " + userId + " could not reserve items in cart");

            for (Map.Entry<String, ShoppingBasket> basket : basketCases.entrySet()) {
                basket.getValue().purchaseSuccessful(false);
            }
            throw new CantPurchaseException ("not enough items in stock in the store");

        }
        Log.getLogger().info("user " + userId + " total cart value " + total);

        ans = externalConnectionHolder.tryToPurchase(total,weight);

        //if transaction succeeded we need to save it in history.
        if (ans >=0){
            for (Map.Entry<String, ShoppingBasket> basket : basketCases.entrySet()) {
                basket.getValue().purchaseSuccessful(true);
            }
            Log.getLogger().info("user " + userId + " purchased cart successfully " );
            Log.getLogger().fine("user " + userId + " price of cart is " + total);

            recordPurchase();
        }
        else {
            Log.getLogger().warning("user " + userId + " could not purchase cart");
            throw new CantPurchaseException ("could not complete transaction, because of payment or delivery");

        }

        //check if the order complies with Purchase and Delivery selected.
        return total;
    }


    //Iterate through all baskets, in each basket iterate through each item and add it to history;
    private boolean recordPurchase (){



        History history = History.getInstance();
        int indexPurchase = history.getIndexPurchase();

        Date date = new Date();
        date.getTime();
        List<ThreeGenerics<String,Float,Integer>> namePriceAmount = new LinkedList<>();

            for (Map.Entry<String, ShoppingBasket> basket : basketCases.entrySet()) {
                namePriceAmount = basket.getValue().recordPurchase();
                for (ThreeGenerics<String, Float,Integer> singleNamePriceAmount : namePriceAmount) {
                    history.insertRecord(userId, basket.getValue().getStore(), indexPurchase, singleNamePriceAmount.getOb1(),
                            singleNamePriceAmount.getOb2(), singleNamePriceAmount.getOb3(), date);
                }


            }
            basketCases=new HashMap<String, ShoppingBasket>();



        return true;


    }

    //     HashMap<String, ShoppingBasket> basketCases;
    public  boolean equals(ShoppingCart shoppingCart) {
        for (Map.Entry<String, ShoppingBasket> basket : basketCases.entrySet()) {

            if  (!basket.getValue().equals(shoppingCart.basketCases.get(basket.getKey())))
                return false;

        }
        return true;


    }
}
