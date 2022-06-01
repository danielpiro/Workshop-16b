package com.example.demo.Tests.Unit.StorePartUnitTests;


import com.example.demo.CustomExceptions.Exception.CantPurchaseException;
import com.example.demo.CustomExceptions.Exception.StorePolicyViolatedException;
import com.example.demo.CustomExceptions.Exception.SupplyManagementException;
import com.example.demo.ExternalConnections.Delivery.DeliveryNames;
import com.example.demo.ExternalConnections.ExternalConnectionHolder;
import com.example.demo.ExternalConnections.Payment.PaymentNames;
import com.example.demo.ShoppingCart.UserInfo;
import com.example.demo.Store.InventoryManager;
import com.example.demo.Store.ProductsCategories;
import com.example.demo.Store.StorePurchase.Policies.Policy;
import com.example.demo.Store.StorePurchase.Policies.PolicyBuilder;
import com.example.demo.Store.StorePurchase.PurchasableProduct;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class checkPolicies {
    InventoryManager invMan =  new InventoryManager();
    String Product1Id;
    String Product2Id;
    String Product3Id;
    String Product4Id;
    PolicyBuilder builder = new PolicyBuilder();
    Policy NowHour = builder.newOnHoursOfTheDayPolicy(LocalDateTime.now().minusHours(1),LocalDateTime.now().plusHours(1));
    Policy notNowHour = builder.newOnHoursOfTheDayPolicy(LocalDateTime.now().plusHours(1),LocalDateTime.now().plusHours(2));
    Policy nowDayOfWeek = builder.newOnDaysOfTheWeekPolicy(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
    Policy notNowDayOfWeek = builder.newOnDaysOfTheWeekPolicy(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
    Policy nowDayOfMonth = builder.newOnDayOfMonthPolicy(LocalDateTime.now().minusDays(0),LocalDateTime.now().plusDays(1));
    Policy notNowDayOfMonth = builder.newOnDaysOfTheWeekPolicy(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
    Policy cartP= builder.newCartPolicy(2);
    Policy priceP = builder.newPricePredicate(3);
    Policy categoryP;
    Policy productType;
    Policy productTypeAndAmount;

    @BeforeEach
    void setUp() throws SupplyManagementException {
        setupInventory();
        List<ProductsCategories> categoriesList = new ArrayList<>();
        categoriesList.add(ProductsCategories.Apps$Games);
        categoriesList.add(ProductsCategories.Appliances);
        categoryP  = builder.newCategoryPolicy(categoriesList);
        //setupProductPredicate();
    }
    private void setupInventory() throws SupplyManagementException {
        invMan = new InventoryManager();
        //Product1Id = invMan.addNewProduct("t1", 5.5F, 4, ProductsCategories.Apps$Games.toString());
        //Product2Id = invMan.addNewProduct("t2", 1F, 1, ProductsCategories.Apps$Games.toString());
        //Product3Id = invMan.addNewProduct("t3", 3F, 2, ProductsCategories.Appliances.toString());
        //Product4Id = invMan.addNewProduct("t4", 5F, 6, ProductsCategories.Other.toString());;
    }
    private void setupProductPredicate() throws SupplyManagementException {
        PurchasableProduct p1=invMan.getProduct(Product1Id);
        PurchasableProduct p2=invMan.getProduct(Product2Id);
        PurchasableProduct p3=invMan.getProduct(Product3Id);
        HashMap<PurchasableProduct,Integer>  products = new HashMap<>();
        products.put(p1,1);
        products.put(p1,2);
        products.put(p1,3);
        productTypeAndAmount = builder.newProductWithAmountPolicy(products);
        productType = builder.newProductWithoutAmountPolicy(new ArrayList<>(products.keySet()));
    }

    @Test
    void addNewPolicy(){
        String policyId = invMan.addNewPolicy(NowHour);
        assertEquals(policyId,invMan.getPolicies().get(0).getPolicyId());
    }
    @Test
    void deletePolicy() {
            String policyId = invMan.addNewPolicy(NowHour);
            assertEquals(policyId, invMan.getPolicies().get(0).getPolicyId());
            invMan.deletePolicy(policyId);
            assertEquals(0, invMan.getPolicies().size());
    }

    @Test
    void buyingProcessGoodPolicy() {
        try{
            invMan.addNewPolicy(NowHour);
            invMan.addNewPolicy(nowDayOfMonth);
            invMan.addNewPolicy(nowDayOfWeek);
            invMan.addNewPolicy(cartP);
            tryToBuy();
        }
        catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }
    @Test
    void buyingProcessBadPolicy() {
        try{

//            String Id1 = invMan.addNewProduct("t1", 5.5F, 4, "Appliances");
//            String Id2 = invMan.addNewProduct("t2", 15.5F, 1, "Other");
//            String Id3 = invMan.addNewProduct("t3", 100F, 50, "Baby");
//            HashMap<String, Integer> productAmount = new HashMap<>();
//            productAmount.put(Id1, 2);
//            productAmount.put(Id2, 1);
//            productAmount.put(Id3, 50);
            try {
                invMan.addNewPolicy(notNowHour);
                tryToBuy();
                //invMan.reserve(productAmount, new ExternalConnectionHolder(DeliveryNames.FedEx, PaymentNames.Visa), new UserInfo(18, "guy"));
                fail();
            }catch (StorePolicyViolatedException e){
                e.printStackTrace();
                invMan = new InventoryManager();
            }
            try {
                invMan.addNewPolicy(notNowDayOfWeek);
                tryToBuy();
                //invMan.reserve(productAmount, new ExternalConnectionHolder(DeliveryNames.FedEx, PaymentNames.Visa), new UserInfo(18, "guy"));
                fail();
            }catch (StorePolicyViolatedException e){
                e.printStackTrace();
                invMan = new InventoryManager();
            }
            try {
                invMan.addNewPolicy(notNowDayOfMonth);
                //invMan.reserve(productAmount, new ExternalConnectionHolder(DeliveryNames.FedEx, PaymentNames.Visa), new UserInfo(18, "guy"));
                tryToBuy();
                fail();
            }catch (StorePolicyViolatedException e){
                e.printStackTrace();
                invMan = new InventoryManager();
            }
            try {
                Policy cartPolicy = builder.newCartPolicy(30);
                invMan.addNewPolicy(cartPolicy);
                tryToBuy();
                //invMan.reserve(productAmount, new ExternalConnectionHolder(DeliveryNames.FedEx, PaymentNames.Visa), new UserInfo(18, "guy"));
                fail();
            }catch (StorePolicyViolatedException e){
                e.printStackTrace();
                invMan = new InventoryManager();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void buyingProcessGoodCompositionPolicy() {
        try{

            invMan.addNewPolicy(cartP);
            invMan.addNewPolicy(priceP);
            Policy and1 = builder.AndGatePolicy(NowHour,nowDayOfMonth);
            Policy and2 = builder.AndGatePolicy(nowDayOfWeek,and1);
            invMan.addNewPolicy(and2);
            tryToBuy();
        }
        catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }
    @Test
    void buyingProcessGoodCompositionPolicy2() {
        try{

            invMan.addNewPolicy(cartP);
            invMan.addNewPolicy(priceP);
            Policy and1 = builder.AndGatePolicy(NowHour,nowDayOfMonth);
            Policy or1 = builder.OrGatePolicy(notNowDayOfMonth,and1);
            invMan.addNewPolicy(or1);
            tryToBuy();
        }
        catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }
    @Test
    void buyingProcessGoodCompositionPolicy3() {
        try{

            invMan.addNewPolicy(cartP);
            invMan.addNewPolicy(priceP);
            Policy and1 = builder.AndGatePolicy(NowHour,nowDayOfMonth);
            Policy or1 = builder.OrGatePolicy(notNowDayOfMonth,and1);
            Policy cond = builder.ConditioningGatePolicy(priceP,or1);
            invMan.addNewPolicy(cond);
            tryToBuy();
        }
        catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    private void tryToBuy() throws SupplyManagementException, CantPurchaseException, StorePolicyViolatedException {

        String Id1 = invMan.addNewProduct("t1", 5.5F, 4, "Appliances");
        String Id2 = invMan.addNewProduct("t2", 15.5F, 1, "Other");
        String Id3 = invMan.addNewProduct("t3", 100F, 50, "Handmade");
        HashMap<String, Integer> productAmount = new HashMap<>();
        productAmount.put(Id1, 2);
        productAmount.put(Id2, 1);
        productAmount.put(Id3, 50);
        invMan.reserve(productAmount, new ExternalConnectionHolder(DeliveryNames.FedEx, PaymentNames.Visa), new UserInfo(18, "guy"));
    }
}
