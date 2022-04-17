package Tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AcceptanceTests {

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
