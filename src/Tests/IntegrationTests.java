package Tests;

import com.company.Proxy;
import com.company.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTests {

    /*TODO: (Tagged with fail() in the tests)
     * 1) Parallel Use.
     * 2) Logging.
     * 3) External Services.
     */

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
     *  Scenario 2:
     *  1) Login - as StoreOwner
     *  2) ShowStorePurchaseHistory
     *  3) ShowStoreOfficials
     *  4) Logout
     **/
    @Test
    void scenario2_test() {
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
    void scenario3_test() {
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
     *  Scenarios 1, 2, 3 from IntegrationTests:
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
