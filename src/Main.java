import ExternalConnections.Delivery.FedEx;
import ExternalConnections.Payment.Visa;
import ExternalConnections.PurchasePolicies;
import GlobalSystemServices.Log;
import History.History;
import ShoppingCart.ShoppingCart;
import ShoppingCart.FakeInventoryManager;
import Store.InventoryManager;

import java.util.logging.Level;


public class Main {

    public static void main(String[] args) {
	// write your code here
        Log my_log = null;
        try {
             my_log = Log.getLogger();
        }catch (Exception e)
        {}
        my_log.logger.setLevel(Level.WARNING);

        FedEx fedEx = new FedEx();
        fedEx.connect(21312);

        Visa visa = new Visa ();

        PurchasePolicies purchasePolicies = new PurchasePolicies(fedEx,visa);
        ShoppingCart shoppingCart = new ShoppingCart("1");

        InventoryManager inventoryManager = new InventoryManager();

        //FakeInventoryManager fakeInventory = new FakeInventoryManager();
        inventoryManager.addNewProduct("fanta",12,20,"Other");
        inventoryManager.addNewProduct("coca cola",4,20,"Other");
        inventoryManager.addNewProduct("magnum",1,20,"Other");


        shoppingCart.addProduct("ProductID_0","1",10,inventoryManager,false);
        shoppingCart.addProduct("ProductID_1","2",5,false);
        shoppingCart.addProduct("ProductID_2","2",10,inventoryManager,false);

        System.out.println(shoppingCart.getCartInventory());



        //price is wrong because FakeInventory manager returns 10 always.
        shoppingCart.purchaseCart(purchasePolicies);


        History history = History.getInstance();
        history.PrintHistory();



    }
}
