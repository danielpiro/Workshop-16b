package History;

import java.util.Date;

public class PurchaseHistory {

    private String userID;
    private String storeID;
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

    public PurchaseHistory(String userID, String storeID, int transactionId, String itemName, float itemPrice, int amount, Date timeOfTransaction) {
        this.userID = userID;
        this.storeID = storeID;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemAmount= amount;
        this.timeOfTransaction = timeOfTransaction;
    }

    public String getUserID() {
        return userID;
    }

    public String getStoreID() {
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
