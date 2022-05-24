package com.example.demo.Tests.Unit;


import com.example.demo.History.History;
import com.example.demo.History.PurchaseHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryTest {

    History history;



    @BeforeEach
    void setUp()
    {
        history = new History();
    }


    @Test
    void insertRecord() {
        assertTrue(history.insertRecord("user1","store1",1,"product1",10,5,null));

    }

    @Test
    void printHistory() {

    }

    @Test
    void getUserHistory() {
        List<PurchaseHistory> ph = new LinkedList<PurchaseHistory>();
        history.insertRecord("user1","store1",1,"product1",10,5,null);
        history.insertRecord("user1","store1",1,"product1",10,20,null);
         ph= history.getUserHistory("user1");
         assertEquals(ph.size(),2);
    }

    @Test
    void getStoreHistorySuccess() {
        List<PurchaseHistory> ph = new LinkedList<PurchaseHistory>();
        history.insertRecord("user1","store1",1,"product1",10,5,null);
        history.insertRecord("user1","store1",1,"product1",10,20,null);
        ph= history.getStoreHistory("store1");
        assertEquals(ph.size(),2);
    }

    @Test
    void getStoreHistoryFail() {
        List<PurchaseHistory> ph = new LinkedList<PurchaseHistory>();
        history.insertRecord("user1","store1",1,"product1",10,5,null);
        history.insertRecord("user1","store1",1,"product1",10,20,null);
        ph= history.getStoreHistory("store2");
        assertNotEquals(ph.size(),2);
    }
}