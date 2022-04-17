package ShoppingCart;

import CustomExceptions.CantPurchaseException;

import java.util.HashMap;
import java.util.Map;

public class ShoppingBasket {

    private int Store;
    private HashMap<Integer, Integer> productAmount;
    private InventoryProtector iProtector;
    private Purchase myPurchase;

    public int addProduct(int productID, int amount) {
        if (productAmount.containsKey(productID)) {
            int currentAmount = productAmount.get(productID);
            productAmount.replace(productID, currentAmount + amount);
        } else
            productAmount.put(productID, amount);

        return 1;
    }

    public ShoppingBasket(int store , InventoryProtector inventoryProtector, Purchase myPurchase) {
        Store = store;
        this.iProtector = inventoryProtector;
        this.myPurchase = myPurchase;
        this.productAmount = new HashMap<>();
    }

    public int removeProduct(int productID, int amount) {
        if (productAmount.containsKey(productID)) {
            int currentAmount = productAmount.get(productID);
            if (currentAmount - amount <= 0)
                productAmount.remove(productID);
            else
                productAmount.replace(productID, currentAmount - amount);


        } else
            return -1;
        return -1;
    }

    public String getInventory (){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, Integer> pAmount : productAmount.entrySet()){
            sb.append("name: "+iProtector.getProductName(pAmount.getKey()) + "        amount: " + pAmount.getValue()+ "\n");
        }
        return sb.toString();
    }

    public int purchase() throws CantPurchaseException {
        int answer = iProtector.purchase(productAmount);
        if(answer < 0 )
             return -1;
        else
            return answer;


    }


}
