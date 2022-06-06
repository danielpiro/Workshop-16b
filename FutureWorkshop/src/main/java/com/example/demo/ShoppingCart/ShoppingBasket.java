package com.example.demo.ShoppingCart;



import com.example.demo.CustomExceptions.Exception.CantPurchaseException;
import com.example.demo.CustomExceptions.Exception.StorePolicyViolatedException;
import com.example.demo.CustomExceptions.Exception.SupplyManagementException;
import com.example.demo.ExternalConnections.ExternalConnectionHolder;
import com.example.demo.Generic.ThreeGenerics;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ShoppingBasket {

    private String Store;
    public HashMap<String, Integer> productAmount;
    private HashMap<String, Integer> productAmountAuctionOrBid;
    private InventoryProtector iProtector;

    public int addProduct(String productID, int amount) {

        if (productAmount.containsKey(productID)) {
            int currentAmount = productAmount.get(productID);
            productAmount.replace(productID, currentAmount + amount);
        } else
            productAmount.put(productID, amount);

        return 1;
    }

    float getPriceOfCartAfterDiscount( ExternalConnectionHolder externalConnectionHolder,String userID) throws StorePolicyViolatedException
    {
        return iProtector.getPriceOfCartAfterDiscount(productAmount,externalConnectionHolder,new UserInfo(18, userID));
    }
    float getPriceOfCartBeforeDiscount( ExternalConnectionHolder externalConnectionHolder, String userID) throws StorePolicyViolatedException{
        return iProtector.getPriceOfCartBeforeDiscount(productAmount,externalConnectionHolder,new UserInfo(18, userID));
    }


    public int addProductAuction(String productID, int amount) {
        if (productAmountAuctionOrBid.containsKey(productID)) {
            int currentAmount = productAmountAuctionOrBid.get(productID);
            productAmountAuctionOrBid.replace(productID, currentAmount + amount);
        } else
            productAmountAuctionOrBid.put(productID, amount);

        return 1;
    }

    public ShoppingBasket(String store, InventoryProtector inventoryProtector) {
        Store = store;
        this.iProtector = inventoryProtector;
        this.productAmount = new HashMap<>();
    }

    public ShoppingBasket(String store) {
        Store = store;
        this.productAmount = new HashMap<>();
    }

    public int removeProduct(String productID, int amount) {
        if (productAmount.containsKey(productID)) {
            int currentAmount = productAmount.get(productID);
            if (currentAmount - amount <= 0)
                productAmount.remove(productID);
            else
                productAmount.replace(productID, currentAmount - amount);


        } else
            return -1;
        return 1;

    }

    public int removeCompleteyProduct(String productID) {
        if (productAmount.containsKey(productID)) {
            productAmount.remove(productID);
            return 0;
        } else
            return -1;

    }

    public String getInventory() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> pAmount : productAmount.entrySet()) {
            sb.append("id: " + pAmount.getKey() + "    name: " + iProtector.getProductName(pAmount.getKey()) + "    amount: " + pAmount.getValue() + "\n");


        }
        return sb.toString();
    }


    //todo get age
    public float purchase(ExternalConnectionHolder externalConnectionHolder, String userID) throws CantPurchaseException, SupplyManagementException, StorePolicyViolatedException {

        float answer = iProtector.reserve(productAmount, externalConnectionHolder, new UserInfo(18, userID));//todo guy edited it but dan need to change this userInfo
        if (answer < 0)
            return -1;
        else
            return answer;


    }

    public void purchaseSuccessful(boolean success) {
        try {
            iProtector.purchaseSuccessful(productAmount, success);
        } catch (SupplyManagementException e) {

        }

    }


    public String getStore() {
        return Store;
    }

    //name, price, amount
    public List<ThreeGenerics<String, Float, Integer>> recordPurchase() {
        List<ThreeGenerics<String, Float, Integer>> namePriceAmount = new LinkedList<>();
        for (Map.Entry<String, Integer> pAmount : productAmount.entrySet()) {

            String name = iProtector.getProductName(pAmount.getKey());
            float price = iProtector.getProductPrice(pAmount.getKey());

            ThreeGenerics<String, Float, Integer> threeGenerics = new ThreeGenerics<>(name, price, pAmount.getValue());
            namePriceAmount.add(threeGenerics);

        }
        return namePriceAmount;

    }

    public boolean equals(ShoppingBasket shoppingBasket) {
        return this.productAmount.equals(shoppingBasket.productAmount);


    }

    public ShoppingBasket addBaskets(ShoppingBasket shoppingBasket) {
        shoppingBasket.productAmount.forEach(
                (key, value) -> productAmount.merge( key, value, (v1, v2) ->  v1 + v2)
        );

        return this;

    }
}
