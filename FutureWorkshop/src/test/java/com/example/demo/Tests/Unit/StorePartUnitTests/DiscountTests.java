package com.example.demo.Tests.Unit.StorePartUnitTests;

import com.example.demo.CustomExceptions.Exception.SupplyManagementException;
import com.example.demo.Store.InventoryManager;
import com.example.demo.Store.ProductsCategories;
import com.example.demo.Store.StorePurchase.Discounts.Discount;
import com.example.demo.Store.StorePurchase.Discounts.PercentageDiscount;
import com.example.demo.Store.StorePurchase.predicates.DiscountPredicate;
import com.example.demo.Store.StorePurchase.predicates.PredImplementions.AlwaysTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class DiscountTests {
    InventoryManager invMan =  new InventoryManager();
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
    @Test
    void addDiscountDelete(){
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


}
