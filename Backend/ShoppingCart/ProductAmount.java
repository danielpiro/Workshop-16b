package Mine;

public class ProductAmount {

    private int productID;
    private int amount;

    public ProductAmount(int productID, int amount) {
        this.productID = productID;
        this.amount = amount;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
