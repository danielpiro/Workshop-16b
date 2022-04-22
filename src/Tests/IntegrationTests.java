package Tests;

import com.company.Proxy;
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

    @BeforeEach
    void setUp() {
        //set all system's users, stores & products
        proxy.openingMarket();
    }

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
     *  Scenario 1:
     *  1) Register new user
     *  2) Login with this user
     *  3) Logout from that user
     **/
    @Test
    void scenario1_test() {
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

}
