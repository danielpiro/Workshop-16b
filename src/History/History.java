package History;

import ShoppingCart.ShoppingBasket;

import java.util.*;

public class History {
    private static History history;
    private HashMap<Integer, PurchaseHistory> purchaseHistoryHashMap;
    private int indexRecord;
    private int indexPurchase;
    private Object lock;


    public History() {
        this.purchaseHistoryHashMap = new HashMap<Integer, PurchaseHistory>();
        this.indexRecord = 0;
        this.indexPurchase = 0;
        lock = new Object();
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


    public boolean insertRecord(int userID, int storeID, int transcationId, String itemName, int itemPrice,int amount, Date timeOfTransaction) {

        //synchronized because we dont want the same index to be used twice
        synchronized (History.class) {
            PurchaseHistory ph = new PurchaseHistory(userID, storeID,transcationId, itemName, itemPrice,amount, timeOfTransaction);
            purchaseHistoryHashMap.put(indexRecord, ph);
            indexRecord++;
            return true;
        }

    }

    public void PrintHistory() {

        for (Map.Entry<Integer, PurchaseHistory> ph : purchaseHistoryHashMap.entrySet()) {
            System.out.println(ph.toString().substring(2));

        }
    }
    public int getIndexPurchase(){
        synchronized (lock){
            indexPurchase++;
            return indexPurchase;
        }
    }

    public List<PurchaseHistory> getUserHistory (int userID) {

        List<PurchaseHistory> phList = new LinkedList<PurchaseHistory>();
        for (Map.Entry<Integer, PurchaseHistory> ph : purchaseHistoryHashMap.entrySet()) {
            if (ph.getValue().getUserID() == userID)
                phList.add(ph.getValue());
        }

        return phList;
    }

    public List<PurchaseHistory> getStoreHistory (int storeId) {

        List<PurchaseHistory> phList = new LinkedList<PurchaseHistory>();
        for (Map.Entry<Integer, PurchaseHistory> ph : purchaseHistoryHashMap.entrySet()) {
            if (ph.getValue().getStoreID() == storeId)
                phList.add(ph.getValue());
        }

        return phList;
    }
}