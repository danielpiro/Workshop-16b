import ExternalConnections.Delivery.Delivery;
import ExternalConnections.Delivery.FedEx;
import ExternalConnections.Payment.Visa;
import ShoppingCart.PurchasePolicies;
import ShoppingCart.ShoppingCart;
import ShoppingCart.FakeInventoryManager;


public class Main {

    public static void main(String[] args) {
	// write your code here

        FedEx fedEx = new FedEx();
        Visa visa = new Visa ();
        PurchasePolicies purchasePolicies = new PurchasePolicies(fedEx,visa);
        ShoppingCart shoppingCart = new ShoppingCart();

        FakeInventoryManager fakeInventory = new FakeInventoryManager();

        shoppingCart.addProduct(1,1,10,fakeInventory);
        shoppingCart.addProduct(2,1,5);
        shoppingCart.addProduct(3,2,10,fakeInventory);

        shoppingCart.purchaseCart(purchasePolicies);




    }
}
