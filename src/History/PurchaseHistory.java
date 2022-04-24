package History;

import java.util.Date;

public class PurchaseHistory {

    private int userID;
    private int storeID;
    private int purchaseID;
    private String itemName;
    private float itemPrice;
    private int itemAmount;
    private Date timeOfTransaction;

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

    public PurchaseHistory(int userID, int storeID, int transactionId, String itemName, float itemPrice, int amount, Date timeOfTransaction) {
        this.userID = userID;
        this.storeID = storeID;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemAmount= amount;
        this.timeOfTransaction = timeOfTransaction;
    }

    public int getUserID() {
        return userID;
    }

    public int getStoreID() {
        return storeID;
    }

    public int getPurchaseID() {
        return purchaseID;
    }

    public String getItemName() {
        return itemName;
    }

    public float getItemPrice() {
        return itemPrice;
    }

    public Date getTimeOfTransaction() {
        return timeOfTransaction;
    }
}
