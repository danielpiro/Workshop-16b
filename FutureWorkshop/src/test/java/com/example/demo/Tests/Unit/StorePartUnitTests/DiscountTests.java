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
import com.example.demo.Store.StorePurchase.Discounts.ConditionalPercentageDiscount;
import com.example.demo.Store.StorePurchase.Discounts.Discount;
import com.example.demo.Store.StorePurchase.Discounts.PercentageDiscount;
import com.example.demo.Store.StorePurchase.Policies.Policy;
import com.example.demo.Store.StorePurchase.Policies.PolicyBuilder;
import com.example.demo.Store.StorePurchase.PurchasableProduct;
import com.example.demo.Store.StorePurchase.predicates.DiscountPredicate;
import com.example.demo.Store.StorePurchase.predicates.PolicyPredicate;
import com.example.demo.Store.StorePurchase.predicates.PredImplementions.*;
import com.example.demo.Store.StorePurchase.predicates.PredImplementions.compsite.DiscountPredicateAnd;
import com.example.demo.Store.StorePurchase.predicates.PredImplementions.compsite.DiscountPredicateOr;
import com.example.demo.Store.StorePurchase.predicates.PredImplementions.compsite.PolicyPredicateAnd;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.annotations.Ignore;

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

    /**
     * discount all products 20 percent
     */
    @Test
    void applyPercentageDiscount1(){
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

    /**
     * discount on category that no product in cart have, so discount should do nothing
     */
    @Test
    void applyPercentageDiscount2(){
        try {
            List<ProductsCategories> category = new ArrayList<>();
            category.add(ProductsCategories.Handmade);
            DiscountPredicate discountPredicate = new CategoryPredicate(category);
            Discount d =  new PercentageDiscount(20, discountPredicate);
            HashMap<String, Integer> productsAmount = getProductsAmountHashMap();
            float priceBeforeDiscount = addProductsToCartInventoryManagerAndApplyDiscount(invMan, productsAmount);
            invMan.purchaseSuccessful(productsAmount,false);
            invMan.addNewDiscount(d);
            float priceAfterDiscount = addProductsToCartInventoryManagerAndApplyDiscount(invMan, productsAmount);
            assertEquals(priceAfterDiscount,priceBeforeDiscount);
        }
        catch (SupplyManagementException|StorePolicyViolatedException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * discount on category that some product in cart have, so discount should apply on some products
     */
    @Test
    void applyPercentageDiscount3(){
        try {
            List<ProductsCategories> category = new ArrayList<>();
            category.add(ProductsCategories.Apps$Games);
            DiscountPredicate discountPredicate = new CategoryPredicate(category);
            Discount d =  new PercentageDiscount(50, discountPredicate);
            HashMap<String, Integer> productsAmount = getProductsAmountHashMap();

            invMan.purchaseSuccessful(productsAmount,false);
            invMan.addNewDiscount(d);
            float priceAfterDiscount = addProductsToCartInventoryManagerAndApplyDiscount(invMan, productsAmount);
            float expectedPrice = ((invMan.getProductPrice(Product1Id)*0.5F)* 3)+
                    ((invMan.getProductPrice(Product2Id)*0.5F)* 1)+
                    ((invMan.getProductPrice(Product3Id))* 2)+
                    (invMan.getProductPrice(Product4Id)* 6);
            assertEquals(expectedPrice,priceAfterDiscount);
        }
        catch (SupplyManagementException|StorePolicyViolatedException e) {
            e.printStackTrace();

        }
    }

    /**
     * discount on one product from all products
     */
    @Test
    void applyPercentageDiscount4(){
        try {
            List<PurchasableProduct> products = new ArrayList<>();
            products.add(invMan.getProduct(Product1Id));
            DiscountPredicate discountPredicate = new ProductPredicate(products);
            Discount d =  new PercentageDiscount(50, discountPredicate);
            HashMap<String, Integer> productsAmount = getProductsAmountHashMap();

            invMan.purchaseSuccessful(productsAmount,false);
            invMan.addNewDiscount(d);
            float priceAfterDiscount = addProductsToCartInventoryManagerAndApplyDiscount(invMan, productsAmount);
            float expectedPrice = (invMan.getProductPrice(Product1Id)*0.5F)*3+
                    (invMan.getProductPrice(Product2Id))*1+
                    (invMan.getProductPrice(Product3Id))*2+
                    invMan.getProductPrice(Product4Id)*6;
            assertEquals(expectedPrice,priceAfterDiscount);
        }
        catch (SupplyManagementException|StorePolicyViolatedException e) {
            e.printStackTrace();

        }
    }

    /**
     * discount on products that have category and productId  (only one product will be discounted)
     */
    @Test
    void applyPercentageDiscount5(){
        try{
            List<PurchasableProduct> products = new ArrayList<>();
            products.add(invMan.getProduct(Product1Id));
            DiscountPredicate discountPredicateOnId = new ProductPredicate(products);
            List<ProductsCategories> category = new ArrayList<>();
            category.add(ProductsCategories.Apps$Games);
            DiscountPredicate discountPredicateOnCategory = new CategoryPredicate(category);
            Discount d =  new PercentageDiscount(50,new DiscountPredicateAnd(discountPredicateOnId,discountPredicateOnCategory));
            HashMap<String, Integer> productsAmount = getProductsAmountHashMap();

            invMan.purchaseSuccessful(productsAmount,false);
            invMan.addNewDiscount(d);
            float priceAfterDiscount = addProductsToCartInventoryManagerAndApplyDiscount(invMan, productsAmount);
            float expectedPrice = (invMan.getProductPrice(Product1Id)*0.5F)*3+
                    (invMan.getProductPrice(Product2Id))*1+
                    (invMan.getProductPrice(Product3Id))*2+
                    invMan.getProductPrice(Product4Id)*6;
            assertEquals(expectedPrice,priceAfterDiscount);
        }
        catch (SupplyManagementException|StorePolicyViolatedException e) {
            e.printStackTrace();

        }
    }

    /**
     * discount on products that have category and productId  (no product will be discounted)
     */
    @Test
    void applyPercentageDiscount6(){
        try{
            List<PurchasableProduct> products = new ArrayList<>();
            products.add(invMan.getProduct(Product4Id));
            DiscountPredicate discountPredicateOnId = new ProductPredicate(products);
            List<ProductsCategories> category = new ArrayList<>();
            category.add(ProductsCategories.Apps$Games);
            DiscountPredicate discountPredicateOnCategory = new CategoryPredicate(category);
            Discount d =  new PercentageDiscount(50,new DiscountPredicateAnd(discountPredicateOnId,discountPredicateOnCategory));
            HashMap<String, Integer> productsAmount = getProductsAmountHashMap();

            invMan.purchaseSuccessful(productsAmount,false);
            invMan.addNewDiscount(d);
            float priceAfterDiscount = addProductsToCartInventoryManagerAndApplyDiscount(invMan, productsAmount);
            float expectedPrice = (invMan.getProductPrice(Product1Id))*3+
                    (invMan.getProductPrice(Product2Id))*1+
                    (invMan.getProductPrice(Product3Id))*2+
                    invMan.getProductPrice(Product4Id)*6;
            assertEquals(expectedPrice,priceAfterDiscount);
        }
        catch (SupplyManagementException|StorePolicyViolatedException e) {
            e.printStackTrace();

        }
    }

    /**
     * discount on products that have (category and (productId or productId) )  (two product will be discounted)
     */
    @Test
    void applyPercentageDiscount7(){
        try{
            List<PurchasableProduct> products = new ArrayList<>();
            products.add(invMan.getProduct(Product1Id));
            DiscountPredicate discountPredicateOnId1 = new ProductPredicate(products);

            List<PurchasableProduct> products2 = new ArrayList<>();
            products2.add(invMan.getProduct(Product2Id));
            DiscountPredicate discountPredicateOnId2 = new ProductPredicate(products2);

            List<ProductsCategories> category = new ArrayList<>();
            category.add(ProductsCategories.Apps$Games);
            DiscountPredicate discountPredicateOnCategory = new CategoryPredicate(category);

            DiscountPredicate discountPredicateOr = new DiscountPredicateOr(discountPredicateOnId1,discountPredicateOnId2);
            DiscountPredicate discountPredicateAnd = new DiscountPredicateAnd(discountPredicateOnCategory,discountPredicateOr);
            Discount d =  new PercentageDiscount(50,discountPredicateAnd);

            HashMap<String, Integer> productsAmount = getProductsAmountHashMap();

            invMan.purchaseSuccessful(productsAmount,false);
            invMan.addNewDiscount(d);
            float priceAfterDiscount = addProductsToCartInventoryManagerAndApplyDiscount(invMan, productsAmount);
            float expectedPrice = (invMan.getProductPrice(Product1Id)*0.5F)*3+
                    (invMan.getProductPrice(Product2Id)*0.5F)*1+
                    (invMan.getProductPrice(Product3Id))*2+
                    invMan.getProductPrice(Product4Id)*6;
            assertEquals(expectedPrice,priceAfterDiscount);
        }
        catch (SupplyManagementException|StorePolicyViolatedException e) {
            e.printStackTrace();

        }
    }

    /**
     * discount on products that have (category and (productId xor productId) )  (no product will be discounted)
     */
    @Test
    void applyPercentageDiscount8(){
        try{

            List<PurchasableProduct> products = new ArrayList<>();
            products.add(invMan.getProduct(Product1Id));
            DiscountPredicate discountPredicateOnId1 = new ProductPredicate(products);

            List<PurchasableProduct> products2 = new ArrayList<>();
            products2.add(invMan.getProduct(Product3Id));
            DiscountPredicate discountPredicateOnId2 = new ProductPredicate(products2);

            List<ProductsCategories> category = new ArrayList<>();
            category.add(ProductsCategories.Other);
            DiscountPredicate discountPredicateOnCategory = new CategoryPredicate(category);

            DiscountPredicate discountPredicateOr = new DiscountPredicateOr(discountPredicateOnId1,discountPredicateOnId2);
            DiscountPredicate discountPredicateAnd = new DiscountPredicateAnd(discountPredicateOnCategory,discountPredicateOr);
            Discount d =  new PercentageDiscount(50,discountPredicateAnd);

            HashMap<String, Integer> productsAmount = getProductsAmountHashMap();

            invMan.purchaseSuccessful(productsAmount,false);
            invMan.addNewDiscount(d);
            float priceAfterDiscount = addProductsToCartInventoryManagerAndApplyDiscount(invMan, productsAmount);
            float expectedPrice = (invMan.getProductPrice(Product1Id))*3+
                    (invMan.getProductPrice(Product2Id))*1+
                    (invMan.getProductPrice(Product3Id))*2+
                    invMan.getProductPrice(Product4Id)*6;
            assertEquals(expectedPrice,priceAfterDiscount);
        }
        catch (SupplyManagementException|StorePolicyViolatedException e) {
            e.printStackTrace();

        }
    }

    @Test
    void applyConditionalDiscount1(){


        PolicyPredicate atLeast2Products = new CartPredicate(2);

        PolicyPredicate atLeastPrice4 = new PricePredicate(4);

        List<ProductsCategories> category = new ArrayList<>();
        category.add(ProductsCategories.Apps$Games);
        category.add(ProductsCategories.Appliances);
        PolicyPredicate haveCategory = new CategoryPredicate(category);

        PolicyPredicate andAll = new PolicyPredicateAnd(atLeast2Products,new PolicyPredicateAnd(atLeastPrice4,haveCategory));
        applyConditionalDiscount(andAll);
    }

    /**
     * apply ConditionalDiscount (uses the percentage discount from test "applyPercentageDiscount7" to build ConditionalDiscount)
     */
    void applyConditionalDiscount(PolicyPredicate policyPredicate){
        try{
            List<PurchasableProduct> products = new ArrayList<>();
            products.add(invMan.getProduct(Product1Id));
            DiscountPredicate discountPredicateOnId1 = new ProductPredicate(products);

            List<PurchasableProduct> products2 = new ArrayList<>();
            products2.add(invMan.getProduct(Product2Id));
            DiscountPredicate discountPredicateOnId2 = new ProductPredicate(products2);

            List<ProductsCategories> category = new ArrayList<>();
            category.add(ProductsCategories.Apps$Games);
            DiscountPredicate discountPredicateOnCategory = new CategoryPredicate(category);

            DiscountPredicate discountPredicateOr = new DiscountPredicateOr(discountPredicateOnId1,discountPredicateOnId2);
            DiscountPredicate discountPredicateAnd = new DiscountPredicateAnd(discountPredicateOnCategory,discountPredicateOr);

            PercentageDiscount percentageDiscount =  new PercentageDiscount(50,discountPredicateAnd);

            ConditionalPercentageDiscount conditionalPercentageDiscount = new ConditionalPercentageDiscount(percentageDiscount,policyPredicate);
            HashMap<String, Integer> productsAmount = getProductsAmountHashMap();

            invMan.purchaseSuccessful(productsAmount,false);
            invMan.addNewDiscount(conditionalPercentageDiscount);

            float priceAfterDiscount = addProductsToCartInventoryManagerAndApplyDiscount(invMan, productsAmount);
            float expectedPrice = (invMan.getProductPrice(Product1Id)*0.5F)*3+
                    (invMan.getProductPrice(Product2Id)*0.5F)*1+
                    (invMan.getProductPrice(Product3Id))*2+
                    invMan.getProductPrice(Product4Id)*6;
            assertEquals(expectedPrice,priceAfterDiscount);
        }
        catch (SupplyManagementException|StorePolicyViolatedException e) {
            e.printStackTrace();

        }
    }
}
