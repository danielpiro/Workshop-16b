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
    void parallel_use_test() {
        System.out.println("Need to implement thread-base system");
        fail();
    }

    /**
     *  Need to create log file - contains error logs
     *  (requirement 1.c in V1)
     **/
    @Test
    void check_log_test() {
        System.out.println("Need to create log file - contains error logs");
        fail();
    }
//endregion Service-Level Requirements

//region Functional Requirements
    // Functional Requirements
    // ==========================

    /**
     *
     **/
    @Test
    void test1() {
        pass();
    }

    /**
     *
     **/
    @Test
    void test2() {
        pass();
    }
//endregion Functional Requirements

}