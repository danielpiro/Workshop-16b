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
     *  1) Register
     *  2) Login
     *  3) Logout
     **/
    @Test
    void scenario1_test() {

        assertTrue(true);
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

        assertTrue(true);
    }

}
