package ShoppingCart;

import CustomExceptions.CantPurchaseException;
import ExternalConnections.PurchasePolicies;
import Generic.ThreeGenerics;
import GlobalSystemServices.Log;
import History.History;


import java.util.*;
import java.util.logging.Logger;

public class ShoppingCart {

    //storeID, and the basket
    private HashMap<String, ShoppingBasket> basketCases;
    private PurchasePolicies purchasePolicies;
    Log my_log;



    private String userId;


    public ShoppingCart(String userId) {
        this.basketCases = new HashMap<String, ShoppingBasket>();
        this.userId=userId;

        try{
            my_log = Log.getLogger();
        }catch (Exception e){}
    }

    public boolean containsStore(String storeID) {
        return basketCases.containsKey(storeID);
    }

    public int removeProduct(String productID, String storeID, int amount) {
        if (basketCases.containsKey(storeID)) {
            basketCases.get(storeID).removeProduct(productID, amount);
            my_log.logger.info("removed Product");
        } else {
            my_log.logger.warning("could not remove Product");
            return -1;

        }
        return 1;

    }
    public int removeCompleteyProduct(String productID, String storeID) {
        if (basketCases.containsKey(storeID)) {
            basketCases.get(storeID).removeCompleteyProduct(productID);
            my_log.logger.info("removed Product");
        } else {
            my_log.logger.warning("could not remove Product");
            return -1;

        }
        return 1;

    }




    // to use when we do not have an instance of the store, and need an inventory protector
    public int addProduct(String productID, String storeID, int amount, InventoryProtector inventoryProtector, boolean auctionOrBid) {

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
        my_log.logger.info("Product " + productID+ " was added to user: " + userId + " cart");
        return 1;


    }

    public String getCartInventory() {
        StringBuilder sb = new StringBuilder();
        sb.append("showing user's shopping cart:\n");
        for (Map.Entry<String, ShoppingBasket> basket : basketCases.entrySet()) {
            sb.append("store number " + basket.getKey() + "\n");
            sb.append(basket.getValue().getInventory());
        }
        my_log.logger.info("Printing cart Inventory of user: " + userId);

        return sb.toString();
    }


    public float purchaseCart(PurchasePolicies purchasePolicies) {
        float total=0;
        int weight = 10;
        int ans =0;

        my_log.logger.info("Purchasing Cart of user: " + userId);

        //check if we can purchase from store, that items are in inventory and store policies are complied
        try {
            for (Map.Entry<String, ShoppingBasket> basket : basketCases.entrySet()) {
                total += basket.getValue().purchase(purchasePolicies,userId);
            }
        }
        catch ( CantPurchaseException e){
            my_log.logger.warning("could not reserve items in cart");

            for (Map.Entry<String, ShoppingBasket> basket : basketCases.entrySet()) {
                basket.getValue().purchaseSuccessful(false);
            }
            return -1;

        }
        ans = purchasePolicies.tryToPurchase(total,weight);

        //if transaction succeeded we need to save it in history.
        if (ans ==0){
            for (Map.Entry<String, ShoppingBasket> basket : basketCases.entrySet()) {
                basket.getValue().purchaseSuccessful(true);
            }
            recordPurchase();
        }

        //check if the order complies with Purchase and Delivery selected.
        return total;
    }


    //Iterate through all baskets, in each basket iterate through each item and add it to history;
    private boolean recordPurchase (){


        my_log.logger.info("Recording Purchase from user: " + userId );

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
}