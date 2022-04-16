package NotMine;

import java.util.HashMap;

public interface InventoryProtector {

    public String getProductName (int productID);

    //return total price of items, if cant purchase return negative number.
    public int purchase (HashMap<Integer,Integer> ProductAmount);
}
