import ExternalConnections.Delivery.FedEx;
import ExternalConnections.Payment.Visa;
import ExternalConnections.PurchasePolicies;
import History.History;
import ShoppingCart.ShoppingCart;
import ShoppingCart.FakeInventoryManager;


public class Main {

    public static void main(String[] args) {
	// write your code here

        FedEx fedEx = new FedEx();
        fedEx.connect(21312);

        Visa visa = new Visa ();

        PurchasePolicies purchasePolicies = new PurchasePolicies(fedEx,visa);
        ShoppingCart shoppingCart = new ShoppingCart(1);

        FakeInventoryManager fakeInventory = new FakeInventoryManager();

        shoppingCart.addProduct(1,1,10,fakeInventory,false);
        shoppingCart.addProduct(2,1,5,false);
        shoppingCart.addProduct(3,2,10,fakeInventory,false);

        System.out.println(shoppingCart.getCartInventory());



        //price is wrong because FakeInventory manager returns 10 always.
        shoppingCart.purchaseCart(purchasePolicies);


        History history = History.getInstance();
        history.PrintHistory();



    }
}
