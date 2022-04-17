package Tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitTests {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    private void pass() {
        assertTrue(true);
    }

    @Test
    void sanity_test_success_case() {
        pass();
    }

    @Test
    void sanity_test_fail_case() {
        int expected = 1;
        int gotten = 2;
        if(expected != gotten){
            pass();
        }
        else{
            fail();
        }
    }

//region Service-Level Requirements
    // Service-Level Requirements
    // ==========================

    /**
     *  Need to implement thread-base system
     *  (requirement 1.b in V1)
     **/
    @Test
    void parallel_use_success_case_test() {
        System.out.println("Need to implement thread-base system");
        fail();
    }
    @Test
    void parallel_use_fail_case_test() {
        System.out.println("Need to implement thread-base system");
        fail();
    }

    /**
     *  Need to create log file - contains error logs
     *  (requirement 1.c in V1)
     **/
    @Test
    void check_log_success_case_test() {
        System.out.println("Need to create log file - contains error logs");
        fail();
    }
    @Test
    void check_log_fail_case_test() {
        System.out.println("Need to create log file - contains error logs");
        fail();
    }
//endregion Service-Level Requirements

//region Functional Requirements
    // Functional Requirements
    // ==========================

    /**
     *  System requirement - I.1
     **/
    @Test
    void opening_market_system_success_case_test() {
        System.out.println("-Check that all the external services connection are valid\n" +
                           "-Check that there is a system manager");
        fail();
    }
    @Test
    void opening_market_system_fail_case_test1() {
        // No system manager is exists in the system.
        System.out.println("-Check that all the external services connection are valid\n" +
                           "-Check that there is a system manager");
        fail();
    }
    @Test
    void opening_market_system_fail_case_test2() {
        // No payment system is exists in the system.
        System.out.println("-Check that all the external services connection are valid\n" +
                           "-Check that there is a system manager");
        fail();
    }
    @Test
    void opening_market_system_fail_case_test3() {
        // No delivery system is exists in the system.
        System.out.println("-Check that all the external services connection are valid\n" +
                           "-Check that there is a system manager");
        fail();
    }
    @Test
    void opening_market_system_fail_case_test4() {
        // No security system is exists in the system.
        System.out.println("-Check that all the external services connection are valid\n" +
                           "-Check that there is a system manager");
        fail();
    }

    /**
     *  System requirement - I.2
     **/
    // testing change/edit external service connection
    @Test
    void change_external_service_success_case_test() {
        System.out.println("-Check that all actions in the system work well\n" +
                           "-Check that the change done successfully");
        fail();
    }
    @Test
    void change_external_service_fail_case_test1() {
        // the change itself failed
        System.out.println("-Check that all actions in the system work well\n" +
                           "-Check that the change done successfully");
        fail();
    }
    @Test
    void change_external_service_fail_case_test2() {
        // the change caused an issue in the system actions
        System.out.println("-Check that all actions in the system work well\n" +
                           "-Check that the change done successfully");
        fail();
    }

    // testing switch external service connection
    @Test
    void switch_external_service_success_case_test() {
        System.out.println("-Check that all actions in the system work well\n" +
                           "-Check that the switch done successfully");
        fail();
    }
    @Test
    void switch_external_service_fail_case_test1() {
        // the change itself failed
        System.out.println("-Check that all actions in the system work well\n" +
                           "-Check that the switch done successfully");
        fail();
    }
    @Test
    void switch_external_service_fail_case_test2() {
        // the change caused an issue in the system actions
        System.out.println("-Check that all actions in the system work well\n" +
                           "-Check that the switch done successfully");
        fail();
    }

    // testing add external service connection
    @Test
    void add_external_service_success_case_test() {
        System.out.println("-Check that all actions in the system work well\n" +
                           "-Check that the adding done successfully");
        fail();
    }
    @Test
    void add_external_service_fail_case_test1() {
        // the change itself failed
        System.out.println("-Check that all actions in the system work well\n" +
                           "-Check that the adding done successfully");
        fail();
    }
    @Test
    void add_external_service_fail_case_test2() {
        // the change caused an issue in the system actions
        System.out.println("-Check that all actions in the system work well\n" +
                           "-Check that the adding done successfully");
        fail();
    }

    /**
     *  System requirement - I.3
     **/
    @Test
    void payment_success_case_test() {
        System.out.println("""
                -Check that the payment service connection is valid
                -Check payment details
                -Check payment service answer
                """);
        fail();
    }
    @Test
    void payment_fail_case_test1() {
        // payment service connection lost
        System.out.println("""
                -Check that the payment service connection is valid");
                -Check payment details
                -Check payment service answer
                """);
        fail();
    }
    @Test
    void payment_fail_case_test2() {
        // payment service returns an invalid answer (= the payment can't be done)
        System.out.println("""
                -Check that the payment services connection is valid
                -Check payment details
                -Check payment service answer
                """);
        fail();
    }

    /**
     *  System requirement - I.4
     **/
    @Test
    void delivery_success_case_test() {
        System.out.println("""
                -Check that the delivery service connection is valid
                -Check delivery details
                -Check delivery service answer
                """);
        fail();
    }
    @Test
    void delivery_fail_case_test1() {
        // payment service connection lost
        System.out.println("""
                -Check that the delivery service connection is valid
                -Check delivery details
                -Check delivery service answer
                """);
        fail();
    }
    @Test
    void delivery_fail_case_test2() {
        // payment service returns an invalid answer (= the payment can't be done)
        System.out.println("""
                -Check that the delivery service connection is valid
                -Check delivery details
                -Check delivery service answer
                """);
        fail();
    }

    /**
     *  System requirement - I.5
     **/
    // A costumer bought a product from a store
    @Test
    void realtime_notification_product_bought_success_case_test() {
        System.out.println("""
                - Product purchase was activated - a user logged in and bought a product
                - Check that the store manager is logged in (will be true in this case)
                - Check the message that just sent (will simulate the realtime message sent)
                """);
        fail();
    }
    @Test
    void realtime_notification_product_bought_fail_case_test() {
        System.out.println("""
                - Product purchase was activated - a user logged in and bought a product
                - Check that the store manager is logged in (will be true in this case)
                - Check the message that just sent (will simulate the realtime message sent)
                    -> MESSAGE DIDN'T ARRIVE
                """);
        fail();
    }

    // A store was closed, then re-opened
    @Test
    void realtime_notification_store_closed_and_opened_success_case_test() {
        System.out.println("""
                - Store owner closed his store (after logged in as store owner)
                - Check that another store manager (of the same store) is logged in (will be true in this case)
                - The store owner close the store
                - Check the message that just sent (will simulate the realtime message sent)
                - The store owner open the store
                - Check that the store manager is logged in (will be true in this case)
                - Check the message that just sent (will simulate the realtime message sent)
                """);
        fail();
    }
    @Test
    void realtime_notification_store_closed_and_opened_fail_case_test1() {
        System.out.println("""
                - Store owner closed his store (after logged in as store owner)
                - Check that another store manager (of the same store) is logged in (will be true in this case)
                - The store owner close the store
                - Check the message that just sent (will simulate the realtime message sent)
                    -> MESSAGE DIDN'T ARRIVE
                """);
        fail();
    }
    @Test
    void realtime_notification_store_closed_and_opened_fail_case_test2() {
        System.out.println("""
                - Store owner closed his store (after logged in as store owner)
                - Check that another store manager (of the same store) is logged in (will be true in this case)
                - The store owner close the store
                - Check the message that just sent (will simulate the realtime message sent)
                - The store owner open the store
                - Check that the store manager is logged in (will be true in this case)
                - Check the message that just sent (will simulate the realtime message sent)
                    -> MESSAGE DIDN'T ARRIVE
                """);
        fail();
    }

    // A costumer's permissions was removed
    @Test
    void realtime_notification_user_permission_update_success_case_test() {
        System.out.println("""
                - Store owner update one of the store manager permissions (after logged in as store owner)
                - Check that the store manager is logged in (will be true in this case)
                - Check the message that just sent (will simulate the realtime message sent)
                """);
        fail();
    }
    @Test
    void realtime_notification_user_permission_update_fail_case_test() {
        System.out.println("""
                - Store owner update one of the store manager permissions (after logged in as store owner)
                - Check that the store manager is logged in (will be true in this case)
                - Check the message that just sent (will simulate the realtime message sent)
                    -> MESSAGE DIDN'T ARRIVE
                """);
        fail();
    }

    /**
     *  System requirement - I.6
     **/
    // A costumer bought a product from a store
    @Test
    void offline_notification_product_bought_success_case_test() {
        System.out.println("""
                - Product purchase was activated - a user logged in and bought a product
                - Check that the store manager is logged in (will be true in this case)
                - Check the message that just sent (will simulate the offline message sent)
                """);
        fail();
    }
    @Test
    void offline_notification_product_bought_fail_case_test() {
        System.out.println("""
                - Product purchase was activated - a user logged in and bought a product
                - Check that the store manager is logged in (will be true in this case)
                - Check the message that just sent (will simulate the offline message sent)
                    -> MESSAGE DIDN'T ARRIVE
                """);
        fail();
    }

    // A store was closed, then re-opened
    @Test
    void offline_notification_store_closed_and_opened_success_case_test() {
        System.out.println("""
                - Store owner closed his store (after logged in as store owner)
                - Check that another store manager (of the same store) is logged in (will be true in this case)
                - The store owner close the store
                - Check the message that just sent (will simulate the offline message sent)
                - The store owner open the store
                - Check that the store manager is logged in (will be true in this case)
                - Check the message that just sent (will simulate the offline message sent)
                """);
        fail();
    }
    @Test
    void offline_notification_store_closed_and_opened_fail_case_test1() {
        System.out.println("""
                - Store owner closed his store (after logged in as store owner)
                - Check that another store manager (of the same store) is logged in (will be true in this case)
                - The store owner close the store
                - Check the message that just sent (will simulate the offline message sent)
                    -> MESSAGE DIDN'T ARRIVE
                """);
        fail();
    }
    @Test
    void offline_notification_store_closed_and_opened_fail_case_test2() {
        System.out.println("""
                - Store owner closed his store (after logged in as store owner)
                - Check that another store manager (of the same store) is logged in (will be true in this case)
                - The store owner close the store
                - Check the message that just sent (will simulate the offline message sent)
                - The store owner open the store
                - Check that the store manager is logged in (will be true in this case)
                - Check the message that just sent (will simulate the offline message sent)
                    -> MESSAGE DIDN'T ARRIVE
                """);
        fail();
    }

    // A costumer's permissions was removed
    @Test
    void offline_notification_user_permission_update_success_case_test() {
        System.out.println("""
                - Store owner update one of the store manager permissions (after logged in as store owner)
                - Check that the store manager is logged in (will be true in this case)
                - Check the message that just sent (will simulate the offline message sent)
                """);
        fail();
    }
    @Test
    void offline_notification_user_permission_update_fail_case_test() {
        System.out.println("""
                - Store owner update one of the store manager permissions (after logged in as store owner)
                - Check that the store manager is logged in (will be true in this case)
                - Check the message that just sent (will simulate the offline message sent)
                    -> MESSAGE DIDN'T ARRIVE
                """);
        fail();
    }


//endregion Functional Requirements

}