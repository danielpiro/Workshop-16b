package Mine;

import CustomExceptions.CantPurchaseException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ShoppingCart {

    //private HashMap<Integer, List<ProductAmount>> basketCases;
    private HashMap<Integer, ShoppingBasket> basketCases;


    public ShoppingCart() {
        this.basketCases = new HashMap<>();
    }

    public boolean containsStore(int storeID) {
        return basketCases.containsKey(storeID);
    }

    public int removeProduct(int productID, int storeID, int amount) {
        if (basketCases.containsKey(storeID)) {
            basketCases.get(storeID).removeProduct(productID, amount);
        } else
            return -1;
        return 1;

    }

    public int addProduct(int productID, int storeID, int amount) {

        if (basketCases.containsKey(storeID)) {
            basketCases.get(storeID).addProduct(productID, amount);

        } else {
            // todo how to get the inventory manager

            ShoppingBasket sb = new ShoppingBasket(storeID);
            sb.addProduct(productID, amount);
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


    public int purchaseCart() {
        int total=0;

        try {
            for (Map.Entry<Integer, ShoppingBasket> basket : basketCases.entrySet()) {
                total += basket.getValue().purchase();
            }
        }
        catch ( CantPurchaseException e){
            return -1;

        }
        return total;
    }
}