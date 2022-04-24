package ShoppingCart;

import CustomExceptions.CantPurchaseException;
import ExternalConnections.PurchasePolicies;
import Generic.ThreeGenerics;
import History.History;

import java.util.*;

public class ShoppingCart {

    //storeID, and the basket
    private HashMap<Integer, ShoppingBasket> basketCases;
    private PurchasePolicies purchasePolicies;


    private int userId;


    public ShoppingCart(int userId) {
        this.basketCases = new HashMap<>();
        this.userId=userId;
    }

    public boolean containsStore(int storeID) {
        return basketCases.containsKey(storeID);
    }

    public int removeProduct(String productID, int storeID, int amount) {
        if (basketCases.containsKey(storeID)) {
            basketCases.get(storeID).removeProduct(productID, amount);
        } else
            return -1;
        return 1;

    }


    // to use when we already have an instance of the store
    public int addProduct(String productID, int storeID, int amount,boolean auctionOrBid) {

        if (basketCases.containsKey(storeID) && auctionOrBid == false)
            basketCases.get(storeID).addProduct(productID, amount);
        else if(basketCases.containsKey(storeID) && auctionOrBid == true)
            basketCases.get(storeID).addProductAuction(productID, amount);
        else
            return -1;


        return 1;


    }



    // to use when we do not have an instance of the store, and need an inventory protector
    public int addProduct(String productID, int storeID, int amount, InventoryProtector inventoryProtector, boolean auctionOrBid) {

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
        return 1;


    }

    public String getCartInventory() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, ShoppingBasket> basket : basketCases.entrySet()) {
            sb.append("store number " + basket.getKey() + "\n");
            sb.append(basket.getValue().getInventory());
        }

        return sb.toString();
    }


    public float purchaseCart(PurchasePolicies purchasePolicies) {
        float total=0;
        int weight = 10;
        int ans =0;


        //check if we can purchase from store, that items are in inventory and store policies are complied
        try {
            for (Map.Entry<Integer, ShoppingBasket> basket : basketCases.entrySet()) {
                total += basket.getValue().purchase(purchasePolicies);
            }
        }
        catch ( CantPurchaseException e){
            for (Map.Entry<Integer, ShoppingBasket> basket : basketCases.entrySet()) {
                basket.getValue().purchaseSuccessful(false);
            }
            return -1;

        }
        ans = purchasePolicies.tryToPurchase(total,weight);

        //if transaction succeeded we need to save it in history.
        if (ans ==0){
            for (Map.Entry<Integer, ShoppingBasket> basket : basketCases.entrySet()) {
                basket.getValue().purchaseSuccessful(true);
            }
            recordPurchase();
        }

        //check if the order complies with Purchase and Delivery selected.
        return total;
    }



    //Iterate through all baskets, in each basket iterate through each item and add it to history;
    public boolean recordPurchase (){



        History history = History.getInstance();
        int indexPurchase = history.getIndexPurchase();

        Date date = new Date();
        date.getTime();
        List<ThreeGenerics<String,Float,Integer>> namePriceAmount = new LinkedList<>();

            for (Map.Entry<Integer, ShoppingBasket> basket : basketCases.entrySet()) {
                namePriceAmount = basket.getValue().recordPurchase();
                for (ThreeGenerics<String, Float,Integer> singleNamePriceAmount : namePriceAmount) {
                    history.insertRecord(userId, basket.getValue().getStore(), indexPurchase, singleNamePriceAmount.getOb1(),
                            singleNamePriceAmount.getOb2(), singleNamePriceAmount.getOb3(), date);
                }


            }


        return true;


    }
}