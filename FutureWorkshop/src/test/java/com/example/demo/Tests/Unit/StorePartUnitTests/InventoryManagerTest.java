package com.example.demo.Tests.Unit.StorePartUnitTests;


import com.example.demo.CustomExceptions.Exception.SupplyManagementException;
import com.example.demo.Database.Service.DatabaseService;
import com.example.demo.ExternalConnections.Old.Delivery.DeliveryNames;
import com.example.demo.ExternalConnections.Old.ExternalConnectionHolder;
import com.example.demo.ExternalConnections.Old.Payment.PaymentNames;
import com.example.demo.ShoppingCart.UserInfo;
import com.example.demo.Store.InventoryManager;
import com.example.demo.Store.Product;
import com.example.demo.Store.ProductsCategories;
import com.example.demo.Store.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class InventoryManagerTest {
    InventoryManager invMan = new InventoryManager();
    @Autowired
    DatabaseService databaseService;

    @BeforeEach
    void setUp() {
        invMan = new InventoryManager();
    }

    @Test
    void editProductSupplyGood() {
        try {
            invMan.addNewProduct("t1", 5.5F, 4, "Appliances");
            List<Product> products=invMan.getAllProducts(p->p.getSupply()==4);
            String pId = products.get(0).getId();
            invMan.editProduct(pId, 7, "t2", 3F, "Other");
            products=invMan.getAllProducts(p->p.getPrice()==3F);
            assertTrue(products.get(0).getCategory().equals(ProductsCategories.Other) && products.get(0).getName().equals("t2") && products.get(0).getSupply()==7);
        } catch (SupplyManagementException e) {
            e.printStackTrace();
            fail();
        }

    }



    @Test
    void editProductSupplyBad() {
        try {
            invMan.addNewProduct("t1", 5.5F, 4, "Appliances");
            List<Product> products = invMan.getAllProducts(p -> p.getSupply() == 4);
            String pId = products.get(0).getId();
            invMan.editProduct(pId, -1, "t2", 3F, "Other");
            products = invMan.getAllProducts(p -> p.getPrice() == 3F);
            assertNotEquals(-1, products.get(0).getSupply());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void addNewProductGood() {
        try {
            invMan.addNewProduct("t1", 5.5F, 4, ProductsCategories.Appliances.toString());
            invMan.addNewProduct("t2", 15.5F, 1, ProductsCategories.Other.toString());
            invMan.addNewProduct("t3", 100F, 50, ProductsCategories.Baby.toString());
            List<Product> pL1 = invMan.getAllProducts(p -> p.getName().equals("t1"));
            List<Product> pL2 = invMan.getAllProducts(p -> p.getSupply() == 1);
            List<Product> pL3 = invMan.getAllProducts(p -> p.getPrice() == 100F);
            assertTrue(pL1.get(0).getName().equals("t1") && pL2.get(0).getName().equals("t2") && pL3.get(0).getName().equals("t3"));
        } catch (SupplyManagementException e) {
            e.printStackTrace();
            fail();
        }

    }
    @Test
    void addNewProductBad() {
        try {
            invMan.addNewProduct("t1", 5.5F, 4, "Appliances");
        } catch (SupplyManagementException e) {
            e.printStackTrace();
            fail();
        }
        try {
            invMan.addNewProduct("t1", 5.2F, 1, "Other");
            fail();
        }catch (Exception e){
            assertTrue(true,e.toString());
        }


    }
    @Test
    void deleteProduct() {
        String Id = null;
        try {
            Id = invMan.addNewProduct("t1", 5.5F, 4, "Appliances");
            invMan.deleteProduct(Id);
            assertEquals(invMan.getAllProducts(p -> true).size(), 0);
        } catch (SupplyManagementException e) {
            e.printStackTrace();
        }

    }


    @Test
    void addProductReviewGood() {
        try {
            String Id = invMan.addNewProduct("t1", 5.5F, 4, "Appliances");
            invMan.addProductReview(Id,"fakeId", "title", "problem", 3, databaseService);
            invMan.addProductReview(Id,"fakeId", "title", "problem", 2, databaseService);
            List<Review> revs = invMan.getProduct(Id).getReviews();
            assertTrue(revs.size() == 2 && revs.get(1).getBody().equals("problem") && invMan.getProduct(Id).getRating() == 2.5);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    @Test
    void addProductReviewBad() {
        try {
            String Id = invMan.addNewProduct("t1", 5.5F, 4, "Appliances");
            invMan.addProductReview(Id,"fakeId", "title", "problem", 3, databaseService);
            invMan.addProductReview(Id,"fakeId", "title", "problem", 6, databaseService);
            List<Review> revs = invMan.getProduct(Id).getReviews();
            assertFalse(revs.size() == 2 && revs.get(1).getBody().equals("problem") && invMan.getProduct(Id).getRating() == 2.5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void buyingProcessBad() {
        try{
            String Id1 = invMan.addNewProduct("t1", 5.5F, 4, "Appliances");
            HashMap<String,Integer> productAmount = new HashMap<>();
            productAmount.put(Id1, 5);
            invMan.reserve(productAmount, new ExternalConnectionHolder(DeliveryNames.FedEx, PaymentNames.Visa), new UserInfo(18, "guy"));
            fail();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    void buyingProcessGood() {
        try {
            String Id1 = invMan.addNewProduct("t1", 5.5F, 4, "Appliances");
            String Id2 = invMan.addNewProduct("t2", 15.5F, 1, "Other");
            String Id3 = invMan.addNewProduct("t3", 100F, 50, "Baby");
            HashMap<String, Integer> productAmount = new HashMap<>();
            productAmount.put(Id1, 2);
            productAmount.put(Id2, 1);
            productAmount.put(Id3, 50);
            invMan.reserve(productAmount, new ExternalConnectionHolder(DeliveryNames.FedEx, PaymentNames.Visa), new UserInfo(18, "guy"));

            HashMap<String, Integer> buySum1 = new HashMap<>();
            buySum1.put(Id1, 1);
            buySum1.put(Id2, 0);
            buySum1.put(Id3, 20);
            HashMap<String, Integer> buySum2 = new HashMap<>();
            buySum2.put(Id1, 1);
            buySum2.put(Id2, 1);
            buySum2.put(Id3, 20);
            invMan.purchaseSuccessful(buySum1, true);
            invMan.purchaseSuccessful(buySum2, true);

            HashMap<String, Integer> release = new HashMap<>();
            release.put(Id3, 10);
            invMan.purchaseSuccessful(release, false);
            int reserved1 = invMan.getProduct(Id1).getReservedSupply();
            int free1 = invMan.getProduct(Id1).getSupply();
            int reserved2 = invMan.getProduct(Id2).getReservedSupply();
            int free2 = invMan.getProduct(Id2).getSupply();
            int reserved3 = invMan.getProduct(Id3).getReservedSupply();
            int free3 = invMan.getProduct(Id3).getSupply();
            assertTrue(free1 == 2 && free2 == 0 && free3 == 10 &&
                    reserved1==0 && reserved2 == 0 && reserved3 ==0);

        }
        catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }





}
