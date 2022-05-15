package Tests.Unit;

import Controllers.MarketController;
import org.junit.Test;

import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;

class BigControllerTest {
    private static MarketController bigController;

    @BeforeEach
    void setUp() throws IOException {
        bigController = new MarketController();
        bigController.addGuest();
        bigController.addGuest();
        bigController.addGuest();
        bigController.addGuest();
        bigController.addGuest();
        bigController.sign_up("GuestID_0","abed15", "taweel1");
        bigController.sign_up("GuestID_1","amit12", "peled1");
        bigController.sign_up("GuestID_2","guy123", "porat1");
        bigController.login("abed15", "taweel1");

    }

    @org.junit.jupiter.api.Test
    public void sign_up() {
        assertTrue(bigController.sign_up("GuestID_3","name", "pass"));
        assertEquals(5, bigController.getUser_list().size()); //Admin +1 new user + 3 already registered users(in setup)
        assertFalse(bigController.sign_up("GuestID_4","abed15", "pass1111"));
        assertTrue(bigController.sign_up("GuestID_4","newUser2", "newPass3"));
        assertEquals(6, bigController.getUser_list().size()); //

    }

    @Test
    public void login() {
        assertTrue(bigController.login("amit12", "peled1"));
        assertFalse(bigController.login("abed15", "taweel1"));
        assertFalse(bigController.login("matthew", "224342"));
    }

    @Test
    public void addGuest() {
        assertEquals(0, bigController.getGuest_list().size());
        bigController.addGuest();
        assertEquals(1, bigController.getGuest_list().size());
        bigController.addGuest();
        assertEquals(2, bigController.getGuest_list().size());

    }

    @org.junit.jupiter.api.Test
    public void logout() {
        assertTrue(bigController.logout("abed15"));
        assertFalse(bigController.logout("guy123"));
        assertFalse(bigController.logout("user0"));
    }

    @org.junit.jupiter.api.Test
    public void GuestExitSystem() {
        assertEquals(null, bigController.GuestExitSystem("GuestID_4"));
        String guestId = bigController.addGuest();
        assertEquals(guestId, bigController.GuestExitSystem(guestId));
    }


}