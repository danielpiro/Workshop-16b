package Tests;

import com.company.Product;
import com.company.Proxy;
import com.company.Store;
import com.company.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTests {
    Proxy proxy = new Proxy();

    /**
     * Simulate requirement II.1.1 -> Entering to the system
     */
    @BeforeEach
    void setUp() {
        //set all system's users, stores & products
        proxy.openingMarket();
    }

    /**
     * Shutting down
     */
    @AfterEach
    void tearDown() {
        proxy.setUsers(new ArrayList<>());
        proxy.setStores(new ArrayList<>());
    }

    @Test
    void pass_sanity_test_success_case() {
        assertTrue(true);
    }

    /**
     * Requirements I.3 + I.4
     */
    @Test
    void payment_and_delivery_test() {
        String str = "";
        str += proxy.payment();
        str += "\n";
        str += proxy.delivery();
        assertEquals("Payment done successfully\n" +
                             "Delivery done successfully", str);
    }

    /**
     * Requirements II.1.1 + II.1.2
     */
    @Test
    void get_in_and_get_out_test() {
        //setting up a visitor in the system
        if(proxy.getReal() == null) {
            proxy.setCurrentUserInSystem(new User("user0", "", User.permission.Visitor));
        }

        String str = "";
        str += proxy.getInToTheSystem();
        str += "\n";
        str += proxy.getOutFromTheSystem();
        assertEquals("user got-in successfully\n" +
                             "user got out successfully", str);
    }

    /**
     *  1) Register new user -> Requirement II.1.3
     *  2) Login with this user -> Requirement II.1.4
     *  3) Logout from that user -> Requirement II.3.1
     **/
    @Test
    void register_login_and_logout_test() {
        String str = proxy.register("user0", "00000");
        str = str.concat("\n");
        str = str.concat(proxy.login("user0", "00000"));
        str = str.concat("\n");
        str = str.concat(proxy.logout());
        assertEquals("the user registered successfully\n" +
                             "user logged in successfully\n" +
                             "logout done successfully", str);
    }

    /**
     *  1) Receive Info in the system -> Requirement II.1.1
     *  2) Search product -> Requirement II.1.2
     **/
    @Test
    void receiveSystemInfo_and_searchProduct_test() {
        String str = "";
//        String expShowInfoStr = "";
//        str += "Users:\n";
//        for (User u : proxy.getUsers()){
//            str = str.concat(u.getUsername() + "\n");
//        }
//        str += "\nStores:\n";
//        for (Store s : proxy.getStores()){
//            str = str.concat(s.getStoreName() + "\n");
//        }
        str += proxy.receiveSystemInfo();
        str = str.concat("\n");
        str = str.concat(proxy.searchProduct("p1"));
        str = str.concat("\n");
        assertEquals("Users:\n" + "user1\n" + "user2\n" + "user3\n" +
                            "user4\n" + "user5\n" + "user6\n" + "user7\n\n" +
                            "Stores:\n" + "store1\n\n" +
                            "showing all the products that were searched by the user\n", str);
    }

    /**
     *  1) User in the system (by Login) -> Requirement II.1.4
     *  2) Search product -> Requirement II.1.2
     *  3) Save Product to the shopping cart -> Requirement II.2.3
     *  4) Check shopping cart content -> Requirement II.2.4
     *  5) Purchase shopping cart -> Requirement II.2.5
     *  6) Logout from that user -> Requirement II.3.1
     **/
    @Test
    void login_searchProduct_saveProduct_checkShoppingCart_and_purchaseShoppingCart_test() {
        String str = "";
        str += proxy.login("user3", "33333");
        str = str.concat("\n");
        str = str.concat(proxy.searchProduct("p1"));
        str = str.concat("\n");
        str = str.concat(proxy.saveProductFromStoreToShoppingCart("store1", "p1"));
        str = str.concat("\n");
        str = str.concat(proxy.showShoppingCart());
        str = str.concat("\n");
//        str = str.concat(proxy.purchaseShoppingCart());
//        str = str.concat("\n");
        str = str.concat(proxy.logout());
        assertEquals("user logged in successfully\n" +
                            "showing all the products that were searched by the user\n" +
                            "product saved to shopping cart successfully\n" +
                            "showing user's shopping cart\n" +
                            "logout done successfully", str);
    }

    /**
     *  1) Login -> Requirement II.1.4
     *  2) Open store -> Requirement II.3.2
     *  3) Logout -> Requirement II.3.1
     **/
    @Test
    void login_openStore_and_logout(){
        String str = "";
        str += proxy.login("user5", "55555");
        str = str.concat("\n");
        Product newProd = new Product("newProd", 200);
        HashMap<Product, Integer> map = new HashMap<>();
        map.put(newProd, 100);
        str = str.concat(proxy.openStore("store2", map));
        str = str.concat("\n");
        str = str.concat(proxy.logout());
        assertEquals("user logged in successfully\n" +
                            "store was opened successfully\n" +
                            "logout done successfully", str);
    }

    /**
     *  1) Login - as StoreOwner -> Requirement II.1.4
     *  2) ShowStorePurchaseHistory -> Requirement II.4.11
     *  3) ShowStoreOfficials -> Requirement II.4.13
     *  4) Logout -> Requirement II.3.1
     **/
    @Test
    void login_showStorePurchaseHistory_showStoreOfficials_and_logout_test() {
        String str = proxy.login("user3", "33333");
        str = str.concat("\n");
        str = str.concat(proxy.showStorePurchaseHistory("store1"));
        str = str.concat("\n");
        str = str.concat(proxy.showStoreOfficials("store1"));
        str = str.concat("\n");
        str = str.concat(proxy.logout());
        assertEquals("user logged in successfully\n" +
                             "showing all the purchase history...\n" +
                             "showing all the officials...\n" +
                             "logout done successfully",str);
    }

    /**
     *  Scenario 3:
     *  1) Login - as StoreOwner
     *  2) Add store manager to a store.
     *  3) Add store owner to a store -> we'll fail this one with user that is already a manager/owner.
     *  4) Logout
     **/
    @Test
    void login_addNewManager_notAddingNewOwner_and_logout_test() {
        String str = proxy.login("user3", "33333");
        str = str.concat("\n");
        str = str.concat(proxy.addNewStoreManager("store1", "user5"));
        str = str.concat("\n");
        str = str.concat(proxy.addNewStoreOwner("store1", "user4"));
        str = str.concat("\n");
        str = str.concat(proxy.logout());
        assertEquals("user logged in successfully\n" +
                             "the user is now store manager\n" +
                             "fail - this user is already managing/owning a store in the system\n" +
                             "logout done successfully", str);
    }

    /**
     *  1) Register new user
     *  2) Login with this user
     *  3) Logout from that user
     *
     *  4) Login - as StoreOwner
     *  5) ShowStorePurchaseHistory
     *  6) ShowStoreOfficials
     *  7) Logout
     *
     *  8) Login - as StoreOwner
     *  9) Add store manager to a store.
     *  10) Add store owner to a store -> we'll fail this one with user that is already a manager/owner.
     *  11) Logout
     **/
    @Test
    void scenarios_1_2_3_test() {
        String str = proxy.register("user0", "00000");
        str = str.concat("\n");
        str = str.concat(proxy.login("user0", "00000"));
        str = str.concat("\n");
        str = str.concat(proxy.logout());
        str = str.concat("\n");

        str = str.concat(proxy.login("user3", "33333"));
        str = str.concat("\n");
        str = str.concat(proxy.showStorePurchaseHistory("store1"));
        str = str.concat("\n");
        str = str.concat(proxy.showStoreOfficials("store1"));
        str = str.concat("\n");
        str = str.concat(proxy.logout());
        str = str.concat("\n");

        str = str.concat(proxy.login("user3", "33333"));
        str = str.concat("\n");
        str = str.concat(proxy.addNewStoreManager("store1", "user5"));
        str = str.concat("\n");
        str = str.concat(proxy.addNewStoreOwner("store1", "user4"));
        str = str.concat("\n");
        str = str.concat(proxy.logout());

        assertEquals("the user registered successfully\n" +
                "user logged in successfully\n" +
                "logout done successfully\n" +
                "user logged in successfully\n" +
                "showing all the purchase history...\n" +
                "showing all the officials...\n" +
                "logout done successfully\n" +
                "user logged in successfully\n" +
                "the user is now store manager\n" +
                "fail - this user is already managing/owning a store in the system\n" +
                "logout done successfully", str);
    }

}
