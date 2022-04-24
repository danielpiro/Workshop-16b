package ShoppingCart;

import CustomExceptions.CantPurchaseException;
import ExternalConnections.PurchasePolicies;
import Generic.ThreeGenerics;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ShoppingBasket {

    private int Store;
    private HashMap<Integer, Integer> productAmount;
    private HashMap<Integer, Integer> productAmountAuctionOrBid;
    private InventoryProtector iProtector;

    public int addProduct(int productID, int amount) {
        if (productAmount.containsKey(productID)) {
            int currentAmount = productAmount.get(productID);
            productAmount.replace(productID, currentAmount + amount);
        } else
            productAmount.put(productID, amount);

        return 1;
    }

    public int addProductAuction(int productID, int amount) {
        if (productAmountAuctionOrBid.containsKey(productID)) {
            int currentAmount = productAmountAuctionOrBid.get(productID);
            productAmountAuctionOrBid.replace(productID, currentAmount + amount);
        } else
            productAmountAuctionOrBid.put(productID, amount);

        return 1;
    }

    public ShoppingBasket(int store , InventoryProtector inventoryProtector) {
        Store = store;
        this.iProtector = inventoryProtector;
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
            sb.append("id: " + pAmount.getKey() +"    name: "+iProtector.getProductName(pAmount.getKey()) + "    amount: " + pAmount.getValue()+ "\n");
        }
        return sb.toString();
    }

    public float purchase(PurchasePolicies purchasePolicies) throws CantPurchaseException {

        float answer = iProtector.purchase(productAmount , purchasePolicies);
        answer += iProtector.purchase(productAmountAuctionOrBid,purchasePolicies);
        if(answer < 0 )
             return -1;
        else
            return answer;


    }
    public void purchaseSuccessful (boolean success){
        iProtector.purchaseSuccessful(productAmount,success);
        iProtector.purchaseSuccessful(productAmountAuctionOrBid,success);

    }


    public int getStore() {
        return Store;
    }

    //name, price, amount
    public List<ThreeGenerics<String,Integer,Integer>> recordPurchase (){
        List<ThreeGenerics<String,Integer,Integer>> namePriceAmount = new LinkedList<>();
        for (Map.Entry<Integer, Integer> pAmount : productAmount.entrySet()){

            String name = iProtector.getProductName(pAmount.getKey());
            int price = iProtector.getProductPrice(pAmount.getKey());

            ThreeGenerics<String, Integer,Integer> threeGenerics = new ThreeGenerics<>(name,price,pAmount.getValue());
            namePriceAmount.add(threeGenerics);

        }
        return namePriceAmount;

    }


}
