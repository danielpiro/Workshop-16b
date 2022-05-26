package com.example.demo.Tests.Unit.StorePartUnitTests;

import com.example.demo.CustomExceptions.Exception.StorePolicyViolatedException;
import com.example.demo.CustomExceptions.Exception.SupplyManagementException;
import com.example.demo.ExternalConnections.Delivery.DeliveryNames;
import com.example.demo.ExternalConnections.ExternalConnectionHolder;
import com.example.demo.ExternalConnections.Payment.PaymentNames;
import com.example.demo.ShoppingCart.UserInfo;
import com.example.demo.Store.InventoryManager;
import com.example.demo.Store.Product;
import com.example.demo.Store.ProductsCategories;
import com.example.demo.Store.StorePurchase.Discounts.Discount;
import com.example.demo.Store.StorePurchase.Discounts.PercentageDiscount;
import com.example.demo.Store.StorePurchase.predicates.DiscountPredicate;
import com.example.demo.Store.StorePurchase.predicates.PredImplementions.AlwaysTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class DiscountTests {
    InventoryManager invMan =  new InventoryManager();
    String userId1 = "userId1";
    String Product1Id;
    String Product2Id;
    String Product3Id;
    String Product4Id;
    @BeforeEach
    void setUp() throws SupplyManagementException {
        invMan = new InventoryManager();
        Product1Id = invMan.addNewProduct("t1", 5.5F, 4, ProductsCategories.Apps$Games.toString());
        Product2Id = invMan.addNewProduct("t2", 1F, 1, ProductsCategories.Apps$Games.toString());
        Product3Id = invMan.addNewProduct("t3", 3F, 2, ProductsCategories.Appliances.toString());
        Product4Id = invMan.addNewProduct("t4", 5F, 6, ProductsCategories.Other.toString());;
    }
    Discount setUpAlwaysTruePercentageDiscountWith20Percent(){
        DiscountPredicate discountPredicate = new AlwaysTrue();
        return new PercentageDiscount(20, discountPredicate);
    }
    float addProductsToCartInventoryManagerAndApplyDiscount(InventoryManager inv, HashMap<String,Integer> products) throws SupplyManagementException, StorePolicyViolatedException {
        return inv.reserve(products,new ExternalConnectionHolder(DeliveryNames.FedEx, PaymentNames.Visa),new UserInfo(18,userId1));
    }
    private HashMap<String, Integer> getProductsAmountHashMap() {
        HashMap<String,Integer> productsAmount = new HashMap<>();
        productsAmount.put(Product1Id,3);
        productsAmount.put(Product2Id,1);
        productsAmount.put(Product3Id,2);
        productsAmount.put(Product4Id,6);
        return productsAmount;
    }
    @Test
    void addDeleteDiscountGood(){
        try{
        DiscountPredicate discountPredicate = new AlwaysTrue();
        Discount d = new PercentageDiscount(20, discountPredicate);

        String discountId = invMan.addNewDiscount(d);
        List<Discount> ds = invMan.getDiscounts();

        assertTrue(ds.get(0).getDiscountId().equals(discountId));

        invMan.deleteDiscount(discountId);
        ds = invMan.getDiscounts();
        assertEquals(0,ds.size());

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }
    @Test
    void addDeleteDiscountBad(){
        try{
            DiscountPredicate discountPredicate = new AlwaysTrue();
            Discount d = new PercentageDiscount(20, discountPredicate);

            String discountId = invMan.addNewDiscount(d);
            List<Discount> ds = invMan.getDiscounts();

            assertTrue(ds.get(0).getDiscountId().equals(discountId));

            invMan.deleteDiscount(discountId+"1");
            ds = invMan.getDiscounts();
            assertNotEquals(0,ds.size());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    void addDiscountBad(){
        try{
            DiscountPredicate discountPredicate = new AlwaysTrue();
            Discount d = new PercentageDiscount(120, discountPredicate);
            fail();


        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }
    @Test
    void applyDiscountGood1(){
        try {
            Discount d = setUpAlwaysTruePercentageDiscountWith20Percent();
            HashMap<String, Integer> productsAmount = getProductsAmountHashMap();
            float priceBeforeDiscount = addProductsToCartInventoryManagerAndApplyDiscount(invMan, productsAmount);
            invMan.purchaseSuccessful(productsAmount,false);
            invMan.addNewDiscount(d);
            float priceAfterDiscount = addProductsToCartInventoryManagerAndApplyDiscount(invMan, productsAmount);
            assertEquals(priceAfterDiscount,priceBeforeDiscount*0.8F);
        }
        catch (SupplyManagementException|StorePolicyViolatedException e) {
            e.printStackTrace();
            fail();
        }
    }



}
