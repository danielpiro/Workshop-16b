package Tests.Unit;

import Controllers.MarketController;
import User.Guest;
import org.junit.Test;

import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;

class BigControllerTest {
    private static MarketController bigController;
    String g1;
    String g2;
    String g3;
    String g4;
    String g5;
    @BeforeEach
    void setUp() throws IOException {
        bigController = new MarketController();
        g1 =  bigController.addGuest();
        bigController.sign_up(g1,"abed15", "taweel1");
        g2= bigController.addGuest();
        bigController.sign_up(g2,"amit12", "peled1");
        g3=bigController.addGuest();
        bigController.sign_up(g3,"guy123", "porat1");
        bigController.login("abed15", "taweel1");
        g4= bigController.addGuest();
        g5= bigController.addGuest();

    }

    @org.junit.jupiter.api.Test
    public void sign_up() {
        assertTrue(bigController.sign_up(g4,"name", "pass"));
        assertEquals(bigController.getUser_list().get(bigController.getUser_list().size()-1).getName(),"name");
        assertEquals(5, bigController.getUser_list().size()); //Admin +1 new user + 3 already registered users(in setup)
        assertFalse(bigController.sign_up(g5,"abed15", "pass1111"));
        assertTrue(bigController.sign_up(g5,"newUser2", "newPass3"));
        assertEquals(6, bigController.getUser_list().size()); //
        assertEquals(bigController.getUser_list().get(bigController.getUser_list().size()-1).getName(),"newUser2");


    }

    @org.junit.jupiter.api.Test
    public void login() {
        assertTrue(bigController.login("amit12", "peled1"));
        assertEquals(true,bigController.get_subscriber("amit12").isLogged_in());;
        assertFalse(bigController.login("abed15", "taweel1"));
        assertEquals(true,bigController.get_subscriber("abed15").isLogged_in());;
        assertFalse(bigController.login("matthew", "224342"));
    }

    @org.junit.jupiter.api.Test
    public void addGuest() {
        assertEquals(2, bigController.getGuest_list().size());
       String s1 = bigController.addGuest();
       assertEquals(s1,bigController.getGuest_list().get(bigController.getGuest_list().size()-1).name);
        assertEquals(3, bigController.getGuest_list().size());
       String s2 = bigController.addGuest();
        assertEquals(4, bigController.getGuest_list().size());
        assertEquals(s2,bigController.getGuest_list().get(bigController.getGuest_list().size()-1).name);


    }

    @org.junit.jupiter.api.Test
    public void logout() {
        assertTrue(bigController.logout("abed15"));
        assertEquals(false,bigController.get_subscriber("abed15").isLogged_in());
        assertFalse(bigController.logout("guy123"));
        assertFalse(bigController.logout("user0"));
    }

    @org.junit.jupiter.api.Test
    public void GuestExitSystem() {
        assertEquals(null, bigController.GuestExitSystem("GuestID_5"));
        String guestId = bigController.addGuest();
        assertEquals(guestId,bigController.getGuest_list().get(bigController.getGuest_list().size()-1).name);
        assertEquals(guestId, bigController.GuestExitSystem(guestId));
    }


}