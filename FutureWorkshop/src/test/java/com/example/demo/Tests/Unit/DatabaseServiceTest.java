package com.example.demo.Tests.Unit;

import com.example.demo.Database.DTOobjects.Cart.ShoppingBasketDTO;
import com.example.demo.Database.DTOobjects.User.UserDTO;
import com.example.demo.Database.Service.DatabaseService;
import com.example.demo.ShoppingCart.ShoppingBasket;
import com.example.demo.ShoppingCart.ShoppingCart;
import com.example.demo.Store.Review;
import com.example.demo.User.Subscriber;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import javax.validation.constraints.AssertTrue;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DatabaseServiceTest {

    @Autowired
    DatabaseService databaseService;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void removeShoppingCart() {
        databaseService.deleteShoppingCart("guy");
    }

    @Test
    void getShoppingCart() {
        ShoppingCart sc = new ShoppingCart("dan");

        sc.addProduct("product1","store1",5);
        sc.addProduct("product2","store1",8);
        sc.addProduct("product3","store1",1);
        sc.addProduct("product1","store2",5);
        sc.addProduct("product2","store2",2);

        databaseService.saveShoppingCart(sc);

        ShoppingCart scGuy = new ShoppingCart("guy");

        scGuy.addProduct("product1","store1",5);
        scGuy.addProduct("product2","store1",8);
        scGuy.addProduct("product3","store1",1);
        scGuy.addProduct("product1","store2",5);
        scGuy.addProduct("product2","store2",2);

        databaseService.saveShoppingCart(scGuy);



        ShoppingCart sc2 =databaseService.getShoppingCart("dan");

        assertTrue(sc.equals(sc));

    }

    @Test
    void saveShoppingCart() {

        ShoppingCart sc = new ShoppingCart("dan");

        sc.addProduct("product1","store1",5);
        sc.addProduct("product2","store1",8);
        sc.addProduct("product3","store1",1);
        sc.addProduct("product1","store2",5);
        sc.addProduct("product2","store2",2);

        databaseService.saveShoppingCart(sc);
    }


    @Test
    void addUser() {
        Subscriber sb = new Subscriber("dan","rotman");
        databaseService.saveUser(sb);

    }

    @Test
    void findUser() {
        Subscriber sb = new Subscriber("dan","rotman");
        databaseService.saveUser(sb);
        List<UserDTO> ls = databaseService.findUserbyName("dan");
        UserDTO user= ls.get(0);


        assertTrue(user.getName().equals("dan"));

    }




}