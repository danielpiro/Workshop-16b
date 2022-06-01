package com.example.demo.History;

import com.example.demo.GlobalSystemServices.IdGenerator;

import java.time.LocalDateTime;
import java.util.Date;

public class PurchaseHistory {

    private String userID;
    private String storeID;
    private String purchaseID;
    private String itemName;
    private float itemPrice;
    private int itemAmount;
    private LocalDateTime timeOfTransaction;

    @Override
    public String toString() {
        return "userID=" + userID +
                ", storeID=" + storeID +
                ", purchaseID=" + purchaseID +
                ", itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                ", itemAmount=" + itemAmount +
                ", timeOfTransaction=" + timeOfTransaction;
    }


    public PurchaseHistory(String userID, String storeID, String itemName, float itemPrice, int amount, LocalDateTime timeOfTransaction) {
        this.userID = userID;
        this.storeID = storeID;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemAmount= amount;
        this.timeOfTransaction = timeOfTransaction;
        this.purchaseID = IdGenerator.getInstance().getPurchaseID();
    }
    public PurchaseHistory(String userID, String storeID, String transactionId, String itemName, float itemPrice, int amount, LocalDateTime timeOfTransaction) {
        this.userID = userID;
        this.storeID = storeID;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemAmount= amount;
        this.timeOfTransaction = timeOfTransaction;
        this.purchaseID = transactionId;
    }

    public String getUserID() {
        return userID;
    }

    public String getStoreID() {
        return storeID;
    }

    public String getPurchaseID() {
        return purchaseID;
    }

    public String getItemName() {
        return itemName;
    }

    public float getItemPrice() {
        return itemPrice;
    }
    public int getItemAmount() {
        return this.itemAmount;
    }
    public LocalDateTime getTimeOfTransaction() {
        return timeOfTransaction;
    }
}
