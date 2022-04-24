package History;

import java.util.Date;

public class PurchaseHistory {

    private int userID;
    private int storeID;
    private int purchaseID;
    private String itemName;
    private int itemPrice;
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

    public PurchaseHistory(int userID, int storeID, int transactionId, String itemName, int itemPrice, int amount, Date timeOfTransaction) {
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

    public int getItemPrice() {
        return itemPrice;
    }

    public Date getTimeOfTransaction() {
        return timeOfTransaction;
    }
}
