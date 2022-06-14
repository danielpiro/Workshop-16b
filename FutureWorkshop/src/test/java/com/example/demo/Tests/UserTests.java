package com.example.demo.Tests;

import com.example.demo.Controllers.BigController;
import com.example.demo.Controllers.UserController;
import com.example.demo.CustomExceptions.Exception.UserException;
import com.example.demo.User.Guest;
import com.example.demo.User.Subscriber;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTests {
    UserController userController;
    Subscriber admin;

    @BeforeEach
    void setUp() throws IOException, UserException {
        userController = new UserController();
        userController.sign_up("abed", "taw12");
        userController.sign_up("guy", "taw12");
        admin = userController.getSystemAdmin();
        admin.setLogged_in(true);
    }

    @org.junit.jupiter.api.Test
    void guest() throws UserException, InterruptedException {
        Guest g1 = userController.addGuest();
        Guest g2 = userController.addGuest();
        assertEquals(true, userController.checkIfGuestExists(g1.name));
        assertEquals(true, userController.checkIfGuestExists(g2.name));
        assertEquals(g1.name, userController.getGuest(g1.name).name);
        assertEquals(g2.name, userController.getGuest(g2.name).name);
        assertEquals(false, userController.get_subscriber("abed").isLogged_in());
        userController.login(g1.name, "abed", "taw12");
        assertEquals(true, userController.get_subscriber("abed").isLogged_in());
        userController.logout("abed");
        assertEquals(false, userController.get_subscriber("abed").isLogged_in());

    }

    @org.junit.jupiter.api.Test
    void notGuest() throws UserException, InterruptedException {
        userController.sign_up("delphin", "rixy");
        assertEquals("delphin", userController.get_subscriber("delphin").name);
        userController.sign_up("lokum", "rixy");
        assertEquals("lokum", userController.get_subscriber("lokum").name);
        userController.login("delphin", "rixy");
        assertEquals(true, userController.get_subscriber("delphin").isLogged_in());
        userController.logout("delphin");
        assertEquals(false, userController.get_subscriber("delphin").isLogged_in());
    }

    @org.junit.jupiter.api.Test
    void Admin() throws UserException {
        assertEquals(1,userController.getSystemAdmins().size());
        Subscriber admin1 = userController.getSystemAdmin();
        userController.addSystemAdmin(admin1.getName(),"abed");
        assertTrue(userController.checkIfAdmin("abed"));
        assertEquals(2,userController.getSystemAdmins().size());


    }
}