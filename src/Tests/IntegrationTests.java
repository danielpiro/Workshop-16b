package Tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTests {

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

}
