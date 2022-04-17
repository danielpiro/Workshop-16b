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
        System.out.println("Check that all the external services connection are valid");
        System.out.println("Check that there is a system manager");
        fail();
    }
    @Test
    void opening_market_system_fail_case_test1() {
        // No system manager is exists in the system.
        System.out.println("Check that all the external services connection are valid");
        System.out.println("Check that there is a system manager");
        fail();
    }
    @Test
    void opening_market_system_fail_case_test2() {
        // No payment system is exists in the system.
        System.out.println("Check that all the external services connection are valid");
        System.out.println("Check that there is a system manager");
        fail();
    }
    @Test
    void opening_market_system_fail_case_test3() {
        // No delivery system is exists in the system.
        System.out.println("Check that all the external services connection are valid");
        System.out.println("Check that there is a system manager");
        fail();
    }
    @Test
    void opening_market_system_fail_case_test4() {
        // No security system is exists in the system.
        System.out.println("Check that all the external services connection are valid");
        System.out.println("Check that there is a system manager");
        fail();
    }

    /**
     *  System requirement - I.2
     **/
    // testing change/edit external service connection
    @Test
    void change_external_service_success_case_test() {
        System.out.println("Check that all actions in the system work well");
        System.out.println("Check that the change done successfully");
        fail();
    }
    @Test
    void change_external_service_fail_case_test1() {
        // the change itself failed
        System.out.println("Check that all actions in the system work well");
        System.out.println("Check that the change done successfully");
        fail();
    }
    @Test
    void change_external_service_fail_case_test2() {
        // the change caused an issue in the system actions
        System.out.println("Check that all actions in the system work well");
        System.out.println("Check that the change done successfully");
        fail();
    }

    // testing switch external service connection
    @Test
    void switch_external_service_success_case_test() {
        System.out.println("Check that all actions in the system work well");
        System.out.println("Check that the switch done successfully");
        fail();
    }
    @Test
    void switch_external_service_fail_case_test1() {
        // the change itself failed
        System.out.println("Check that all actions in the system work well");
        System.out.println("Check that the switch done successfully");
        fail();
    }
    @Test
    void switch_external_service_fail_case_test2() {
        // the change caused an issue in the system actions
        System.out.println("Check that all actions in the system work well");
        System.out.println("Check that the switch done successfully");
        fail();
    }

    // testing add external service connection
    @Test
    void add_external_service_success_case_test() {
        System.out.println("Check that all actions in the system work well");
        System.out.println("Check that the adding done successfully");
        fail();
    }
    @Test
    void add_external_service_fail_case_test1() {
        // the change itself failed
        System.out.println("Check that all actions in the system work well");
        System.out.println("Check that the adding done successfully");
        fail();
    }
    @Test
    void add_external_service_fail_case_test2() {
        // the change caused an issue in the system actions
        System.out.println("Check that all actions in the system work well");
        System.out.println("Check that the adding done successfully");
        fail();
    }

    /**
     *  System requirement - I.3
     **/
    
//endregion Functional Requirements

}