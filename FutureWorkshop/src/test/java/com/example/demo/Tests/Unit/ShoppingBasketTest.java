//package com.example.demo.Tests.Unit;
//
//
//import com.example.demo.ShoppingCart.ShoppingBasket;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class ShoppingBasketTest {
//
//    FakeInventoryManager fakeInventoryManager = new FakeInventoryManager();
//    ShoppingBasket shoppingBasket;
//    @BeforeEach
//    void setUp()
//    {
//        shoppingBasket = new ShoppingBasket("Store_1",fakeInventoryManager);
//    }
//
//    @AfterEach
//    void tearDown() {
//
//    }
//    @Test
//    void addProduct() {
//        int ans = shoppingBasket.addProduct("Part1",20);
//        assertEquals(1,ans);
//    }
//
//    @Test
//    void removeProduct() {
//       shoppingBasket.addProduct("Part1",20);
//        int ans = shoppingBasket.removeProduct("Part1",20);
//        assertEquals(1,ans);
//    }
//
//    @Test
//    void getInventory() {
//        shoppingBasket.addProduct("Part1",20);
//        shoppingBasket.addProduct("Part2",20);
//        int a = shoppingBasket.getInventory().length();
//        assertTrue(a>10);
//    }
//
//    @Test
//    void purchase() {
//
//
//    }
//
//
//    @Test
//    void getStore() {
//        assertEquals(shoppingBasket.getStore(),"Store_1");
//
//    }
//
//
//
//}