import Controllers.Service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class Main {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

         Service service  = new Service();

        Future future1 = service.sign_up("dan","rotman");
        Future future2 = service.sign_up("guy","porat");
        future1.get();
        future2.get();

        Future future3 = service.login("dan","rotman");
        Future future4 = service.login("guy","porat");
        future3.get();
        future4.get();







        // write your code here
        /*Log my_log = null;
        try {
             my_log = Log.getLogger();
        }catch (Exception e)
        {}
        my_log.logger.setLevel(Level.WARNING);

        FedEx fedEx = new FedEx();
        fedEx.connect(21312);

        Visa visa = new Visa ();

        PurchasePolicies purchasePolicies = new PurchasePolicies("fedEx","visa");
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
        history.PrintHistory();*/



    }
}
