package Tests;

import com.company.Proxy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AcceptanceTests {

    /*TODO: (Tagged with fail() in the tests)
     * 1) Parallel Use.
     * 2) Logging.
     * 3) External Services.
     */

    Proxy proxy = new Proxy();

    @BeforeEach
    void setUp() {
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
