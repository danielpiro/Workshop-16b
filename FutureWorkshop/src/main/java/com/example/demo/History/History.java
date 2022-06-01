package com.example.demo.History;



import com.example.demo.GlobalSystemServices.Log;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class History {
    private static History history;
    private ConcurrentHashMap<Integer, PurchaseHistory> purchaseHistoryHashMap;
    private int indexRecord;
    private Object lockId;
    private Object printAndPut;


    public History() {
        this.purchaseHistoryHashMap = new ConcurrentHashMap<Integer, PurchaseHistory>();
        this.indexRecord = 0;
        lockId = new Object();
        printAndPut = new Object();
    }

    public static History getInstance() {

        // most will skip synchronized section, only in the beggining will one thread enter and create history.
        if (history == null) {
            synchronized (History.class) {
                if (history == null) {
                    history = new History();
                }
            }
        }
        return history;

    }


    public  boolean insertRecord(String userID, String storeID, String transcationId, String itemName, float itemPrice, int amount, LocalDateTime timeOfTransaction) {
        synchronized (printAndPut) {
            Log.getLogger().info("user " + userID + " recording purchase into history");

            //synchronized because we dont want the same index to be used twice
            synchronized (History.class) {
                PurchaseHistory ph = new PurchaseHistory(userID, storeID, transcationId, itemName, itemPrice, amount, timeOfTransaction);
                purchaseHistoryHashMap.put(indexRecord, ph);
                indexRecord++;
                return true;
            }
        }
    }
    public  boolean insertRecord(String userID, String storeID, String itemName, float itemPrice, int amount, LocalDateTime timeOfTransaction) {
        synchronized (printAndPut) {
            Log.getLogger().info("user " + userID + " recording purchase into history");

            //synchronized because we dont want the same index to be used twice
            synchronized (History.class) {
                PurchaseHistory ph = new PurchaseHistory(userID, storeID, itemName, itemPrice, amount, timeOfTransaction);
                purchaseHistoryHashMap.put(indexRecord, ph);
                indexRecord++;
                return true;
            }
        }
    }

    public  void printHistory() {

        synchronized (printAndPut) {
            for (Map.Entry<Integer, PurchaseHistory> ph : purchaseHistoryHashMap.entrySet()) {
                System.out.println(ph.toString().substring(2));

            }
        }
    }


    public synchronized List<PurchaseHistory> getUserHistory (String userID) {

        List<PurchaseHistory> phList = new LinkedList<PurchaseHistory>();
        for (Map.Entry<Integer, PurchaseHistory> ph : purchaseHistoryHashMap.entrySet()) {
            if (ph.getValue().getUserID().equals(userID))
                phList.add(ph.getValue());
        }

        return phList;
    }

    public  synchronized List<PurchaseHistory> getStoreHistory(String storeId) {

        List<PurchaseHistory> phList = new LinkedList<PurchaseHistory>();
        for (Map.Entry<Integer, PurchaseHistory> ph : purchaseHistoryHashMap.entrySet()) {
            if (ph.getValue().getStoreID().equals( storeId))
                phList.add(ph.getValue());
        }

        return phList;
    }
    public List<PurchaseHistory> getStoreUserHistory(String userId, String storeId) {
        List<PurchaseHistory> phList = new LinkedList<PurchaseHistory>();
        for (Map.Entry<Integer, PurchaseHistory> ph : purchaseHistoryHashMap.entrySet()) {
            if (ph.getValue().getStoreID().equals( storeId) && ph.getValue().getUserID().equals(userId))
                phList.add(ph.getValue());
        }

        return phList;
    }
    public synchronized ConcurrentHashMap<Integer,PurchaseHistory> getAllHistory (String storeId) {

        return purchaseHistoryHashMap;
    }


}