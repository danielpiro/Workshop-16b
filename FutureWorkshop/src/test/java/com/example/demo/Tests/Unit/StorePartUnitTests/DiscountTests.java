package com.example.demo.Tests.Unit.StorePartUnitTests;

import com.example.demo.CustomExceptions.Exception.StorePolicyViolatedException;
import com.example.demo.CustomExceptions.Exception.SupplyManagementException;
import com.example.demo.ExternalConnections.Delivery.DeliveryNames;
import com.example.demo.ExternalConnections.ExternalConnectionHolder;
import com.example.demo.ExternalConnections.Payment.PaymentNames;
import com.example.demo.ShoppingCart.UserInfo;
import com.example.demo.Store.InventoryManager;
import com.example.demo.Store.ProductsCategories;
import com.example.demo.Store.StorePurchase.Discounts.*;
import com.example.demo.Store.StorePurchase.PurchasableProduct;
import com.example.demo.Store.StorePurchase.predicates.DiscountPredicate;
import com.example.demo.Store.StorePurchase.predicates.PolicyPredicate;
import com.example.demo.Store.StorePurchase.predicates.PredImplementions.*;
import com.example.demo.Store.StorePurchase.predicates.PredImplementions.composite.DiscountPredicateAnd;
import com.example.demo.Store.StorePurchase.predicates.PredImplementions.composite.DiscountPredicateOr;
import com.example.demo.Store.StorePurchase.predicates.PredImplementions.composite.PolicyPredicateAnd;
import com.example.demo.Store.StorePurchase.predicates.PredImplementions.composite.PolicyPredicateXor;
import org.apache.tomcat.util.json.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.transaction.NotSupportedException;
import java.time.LocalDateTime;
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
            assertEquals(roundAvoid(priceAfterDiscount,2),roundAvoid(priceBeforeDiscount*0.8F,2));
        }
        catch (SupplyManagementException|StorePolicyViolatedException e) {
            e.printStackTrace();
            fail();
        }
    }
    @Test
    void applyPercentageDiscountDoesNotApply(){
        try {
            DiscountPredicate p = new PricePredicate(100000);
            Discount d = new PercentageDiscount(20,p);
            HashMap<String, Integer> productsAmount = getProductsAmountHashMap();
            float priceBeforeDiscount = addProductsToCartInventoryManagerAndApplyDiscount(invMan, productsAmount);
            invMan.purchaseSuccessful(productsAmount,false);
            invMan.addNewDiscount(d);
            float priceAfterDiscount = addProductsToCartInventoryManagerAndApplyDiscount(invMan, productsAmount);
            assertEquals(roundAvoid(priceAfterDiscount,2),roundAvoid(priceBeforeDiscount,2));
        }
        catch (SupplyManagementException|StorePolicyViolatedException e) {
            e.printStackTrace();
            fail();
        }
    }
    private float roundAvoid(float value, int places) {
        float scale = (float) Math.pow(10, places);
        return Math.round(value * scale) / scale;
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
            fail();
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
            fail();
        }
    }

    /**
     * discount on products that have category and productId  (only one product will be discounted)
     */
    @Test
    void applyPercentageDiscount5(){
        try{
            Discount d = Discount_50Percent__IdPred_and_categoryPred(Product1Id,ProductsCategories.Apps$Games);
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
            fail();
        }
    }

    private Discount Discount_50Percent__IdPred_and_categoryPred(String product1Id, ProductsCategories productsCategories) throws SupplyManagementException {
        List<PurchasableProduct> products = new ArrayList<>();
        products.add(invMan.getProduct(product1Id));
        DiscountPredicate discountPredicateOnId = new ProductPredicate(products);
        List<ProductsCategories> category = new ArrayList<>();
        category.add(productsCategories);
        DiscountPredicate discountPredicateOnCategory = new CategoryPredicate(category);
        Discount d =  new PercentageDiscount(50,new DiscountPredicateAnd(discountPredicateOnId,discountPredicateOnCategory));
        return d;
    }

    /**
     * discount on products that have category and productId  (no product will be discounted)
     */
    @Test
    void applyPercentageDiscount6(){
        try{
            Discount d = Discount_50Percent__IdPred_and_categoryPred(Product4Id,ProductsCategories.Apps$Games);

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
            fail();
        }
    }

    

    /**
     * discount on products that have (category and (productId or productId) )  (two product will be discounted)
     */
    @Test
    void applyPercentageDiscount7(){
        try{
            Discount d = discount_50percent__categoryPred_and_IdPred_or_IdPred(Product1Id,Product2Id,ProductsCategories.Apps$Games);

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
            fail();
        }
    }



    /**
     * discount on products that have (category and (productId xor productId) )  (no product will be discounted)
     */
    @Test
    void applyPercentageDiscount8(){
        try{
            Discount d = discount_50percent__categoryPred_and_IdPred_or_IdPred(Product1Id,Product3Id,ProductsCategories.Other);

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
            fail();
        }
    }

    private PercentageDiscount discount_50percent__categoryPred_and_IdPred_or_IdPred(String pId1,String pId2,ProductsCategories pCategory) throws SupplyManagementException {
        List<PurchasableProduct> products = new ArrayList<>();
        products.add(invMan.getProduct(pId1));
        DiscountPredicate discountPredicateOnId1 = new ProductPredicate(products);

        List<PurchasableProduct> products2 = new ArrayList<>();
        products2.add(invMan.getProduct(pId2));
        DiscountPredicate discountPredicateOnId2 = new ProductPredicate(products2);

        List<ProductsCategories> category = new ArrayList<>();
        category.add(pCategory);
        DiscountPredicate discountPredicateOnCategory = new CategoryPredicate(category);

        DiscountPredicate discountPredicateOr = new DiscountPredicateOr(discountPredicateOnId1,discountPredicateOnId2);
        DiscountPredicate discountPredicateAnd = new DiscountPredicateAnd(discountPredicateOnCategory,discountPredicateOr);
        PercentageDiscount d =  new PercentageDiscount(50,discountPredicateAnd);
        return d;
    }

    /**
     * add two discounts to inventory and check if they are applied one after the other
     */
    @Test
    void sequencePercentageDiscount1(){
        try {
            Discount d1 = discount_50percent__categoryPred_and_IdPred_or_IdPred(Product1Id,Product2Id,ProductsCategories.Apps$Games);
            Discount d2 = Discount_50Percent__IdPred_and_categoryPred(Product1Id,ProductsCategories.Apps$Games);
            HashMap<String, Integer> productsAmount = getProductsAmountHashMap();

            invMan.purchaseSuccessful(productsAmount,false);
            invMan.addNewDiscount(d1);
            invMan.addNewDiscount(d2);
            float priceAfterDiscount = addProductsToCartInventoryManagerAndApplyDiscount(invMan, productsAmount);
            float expectedPrice = (invMan.getProductPrice(Product1Id)*0.5F*0.5F)*3+
                    (invMan.getProductPrice(Product2Id)*0.5F)*1+
                    (invMan.getProductPrice(Product3Id))*2+
                    invMan.getProductPrice(Product4Id)*6;
            assertEquals(expectedPrice,priceAfterDiscount);

        } catch (SupplyManagementException | StorePolicyViolatedException e) {
            e.printStackTrace();
            fail();
        }
    }
    /**
     * add two discounts (only one will work) to inventory and check if only one applied
     */
    @Test
    void sequencePercentageDiscount2(){
        try {
            Discount d1 = discount_50percent__categoryPred_and_IdPred_or_IdPred(Product1Id,Product2Id,ProductsCategories.Apps$Games);
            Discount d2 = Discount_50Percent__IdPred_and_categoryPred(Product4Id,ProductsCategories.Apps$Games);
            HashMap<String, Integer> productsAmount = getProductsAmountHashMap();

            invMan.purchaseSuccessful(productsAmount,false);
            invMan.addNewDiscount(d1);
            invMan.addNewDiscount(d2);
            float priceAfterDiscount = addProductsToCartInventoryManagerAndApplyDiscount(invMan, productsAmount);
            float expectedPrice = (invMan.getProductPrice(Product1Id)*0.5F)*3+
                    (invMan.getProductPrice(Product2Id)*0.5F)*1+
                    (invMan.getProductPrice(Product3Id))*2+
                    invMan.getProductPrice(Product4Id)*6;
            assertEquals(expectedPrice,priceAfterDiscount);

        } catch (SupplyManagementException | StorePolicyViolatedException e) {
            e.printStackTrace();
            fail();
        }
    }
    /**
     * and composite policy that equals true
     */
    @Test
    void applyConditionalDiscount1(){


        PolicyPredicate atLeast2Products = new CartPredicate(2);

        PolicyPredicate atLeastPrice4 = new PricePredicate(4);

        List<ProductsCategories> category = new ArrayList<>();
        category.add(ProductsCategories.Apps$Games);
        category.add(ProductsCategories.Appliances);
        PolicyPredicate haveCategory = new CategoryPredicate(category);

        PolicyPredicate andAll = new PolicyPredicateAnd(atLeast2Products,new PolicyPredicateAnd(atLeastPrice4,haveCategory));
        applyConditionalDiscount(andAll,true);
    }

    /**
     * all predicates are true but test using xor gate, so test should fail
     */
    @Test
    void applyConditionalDiscount2(){


        PolicyPredicate atLeast2Products = new CartPredicate(2);

        PolicyPredicate atLeastPrice4 = new PricePredicate(4);

        List<ProductsCategories> category = new ArrayList<>();
        category.add(ProductsCategories.Apps$Games);
        category.add(ProductsCategories.Appliances);
        PolicyPredicate haveCategory = new CategoryPredicate(category);

        PolicyPredicate andXor = new PolicyPredicateAnd(atLeast2Products,new PolicyPredicateXor(atLeastPrice4,haveCategory));
        applyConditionalDiscount(andXor,false);
    }

    /**
     * apply ConditionalDiscount (uses the percentage discount from test "applyPercentageDiscount7" to build ConditionalDiscount)
     */
    void applyConditionalDiscount(PolicyPredicate policyPredicate,boolean shouldPass){
        try{
            ConditionalPercentageDiscount conditionalPercentageDiscount = ConditionalDiscount_50percent__categoryPred_and_IdPred_or_IdPred(policyPredicate, Product1Id, Product2Id, ProductsCategories.Apps$Games);

            HashMap<String, Integer> productsAmount = getProductsAmountHashMap();
            float priceBeforeDiscount = addProductsToCartInventoryManagerAndApplyDiscount(invMan, productsAmount);
            invMan.purchaseSuccessful(productsAmount,false);
            invMan.addNewDiscount(conditionalPercentageDiscount);

            float priceAfterDiscount = addProductsToCartInventoryManagerAndApplyDiscount(invMan, productsAmount);
            float expectedPrice = (invMan.getProductPrice(Product1Id)*0.5F)*3+
                    (invMan.getProductPrice(Product2Id)*0.5F)*1+
                    (invMan.getProductPrice(Product3Id))*2+
                    invMan.getProductPrice(Product4Id)*6;

            if(shouldPass)
                assertEquals(expectedPrice,priceAfterDiscount);
            else
                assertEquals(priceBeforeDiscount,priceAfterDiscount);
        }
        catch (SupplyManagementException|StorePolicyViolatedException e) {
            e.printStackTrace();
            fail();
        }
    }

    private ConditionalPercentageDiscount ConditionalDiscount_50percent__categoryPred_and_IdPred_or_IdPred(PolicyPredicate policyPredicate, String p1Id, String p2Id, ProductsCategories pCategory) throws SupplyManagementException {
        List<PurchasableProduct> products = new ArrayList<>();
        products.add(invMan.getProduct(p1Id));
        DiscountPredicate discountPredicateOnId1 = new ProductPredicate(products);

        List<PurchasableProduct> products2 = new ArrayList<>();
        products2.add(invMan.getProduct(p2Id));
        DiscountPredicate discountPredicateOnId2 = new ProductPredicate(products2);

        List<ProductsCategories> category = new ArrayList<>();
        category.add(pCategory);
        DiscountPredicate discountPredicateOnCategory = new CategoryPredicate(category);

        DiscountPredicate discountPredicateOr = new DiscountPredicateOr(discountPredicateOnId1,discountPredicateOnId2);
        DiscountPredicate discountPredicateAnd = new DiscountPredicateAnd(discountPredicateOnCategory,discountPredicateOr);

        PercentageDiscount percentageDiscount =  new PercentageDiscount(50,discountPredicateAnd);

        ConditionalPercentageDiscount conditionalPercentageDiscount = new ConditionalPercentageDiscount(percentageDiscount, policyPredicate);
        return conditionalPercentageDiscount;
    }

    /**
     * apply additionDiscount on {@link #Product1Id} and {@link #Product2Id} 50+50=100 percent
     */
    @Test
    void applyAdditionDiscount(){
        try {
            PercentageDiscount percentageDiscount1 = discount_50percent__categoryPred_and_IdPred_or_IdPred(Product1Id,Product2Id,ProductsCategories.Apps$Games);
            PercentageDiscount percentageDiscount2 = discount_50percent__categoryPred_and_IdPred_or_IdPred(Product2Id,Product3Id,ProductsCategories.Apps$Games);
            AdditionDiscount additionDiscount = new AdditionDiscount(percentageDiscount1,percentageDiscount2);
            HashMap<String, Integer> productsAmount = getProductsAmountHashMap();

            invMan.purchaseSuccessful(productsAmount,false);
            invMan.addNewDiscount(additionDiscount);
            float priceAfterDiscount = addProductsToCartInventoryManagerAndApplyDiscount(invMan, productsAmount);
            float expectedPrice = (invMan.getProductPrice(Product1Id)*0.5F)*3+
                    (invMan.getProductPrice(Product2Id)*0F)*1+
                    (invMan.getProductPrice(Product3Id))*2+
                    invMan.getProductPrice(Product4Id)*6;
            assertEquals(expectedPrice,priceAfterDiscount);
        } catch (SupplyManagementException | NotSupportedException | StorePolicyViolatedException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * apply max Discount on {@link #Product1Id} and {@link #Product2Id} 50+50=100 percent
     */
    @Test
    void applyMaxDiscount(){
        try {

            PercentageDiscount percentageDiscount1 = discount_50percent__categoryPred_and_IdPred_or_IdPred(Product1Id,Product2Id,ProductsCategories.Apps$Games);
            PercentageDiscount percentageDiscount2 = discount_50percent__categoryPred_and_IdPred_or_IdPred(Product2Id,Product3Id,ProductsCategories.Apps$Games);
            AdditionDiscount additionDiscount = new AdditionDiscount(percentageDiscount1,percentageDiscount2);
            MaxDiscount maxDiscount = new MaxDiscount(additionDiscount,percentageDiscount1);

            HashMap<String, Integer> productsAmount = getProductsAmountHashMap();

            invMan.purchaseSuccessful(productsAmount,false);
            invMan.addNewDiscount(maxDiscount);
            float priceAfterDiscount = addProductsToCartInventoryManagerAndApplyDiscount(invMan, productsAmount);
            float expectedPriceForAddition = (invMan.getProductPrice(Product1Id)*0.5F)*3+
                    (invMan.getProductPrice(Product2Id)*0F)*1+
                    (invMan.getProductPrice(Product3Id))*2+
                    invMan.getProductPrice(Product4Id)*6;
            float expectedPriceForPercentageDiscount1 = (invMan.getProductPrice(Product1Id)*0.5F)*3+
                    (invMan.getProductPrice(Product2Id)*0F)*1+
                    (invMan.getProductPrice(Product3Id))*2+
                    invMan.getProductPrice(Product4Id)*6;
            assertEquals(Math.min(expectedPriceForPercentageDiscount1,expectedPriceForAddition),priceAfterDiscount);
        } catch (SupplyManagementException | NotSupportedException | StorePolicyViolatedException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void temp() throws ParseException, SupplyManagementException {
        String onlyCategory = "{\"type1\":{\"type\": \"category\",\"data1\":[Baby,\"Appliances\"]}}";
        String onlyPrice = "{\"type1\":{\"type\": \"price\",\"data1\":1}}";
        String productWithoutAmount = "{\"type1\":{\"type\": \"productWithoutAmount\",\"data1\":[\"p1\",\"p2\"]}}";
        String productWithAmount = "{\"type1\":{\"type\": \"productWithAmount\",\"data1\":[[\"p1\",2],[\"p2\",3]]}}";
        String AlwaysTrue = "{\"type1\":{\"type\": \"AlwaysTrue\"}}";

        String PriceAndCategory = "{\"type1\":{\"type\": \"price\",\"data1\":1} , \"op1\":\"And\" , \"type2\":{\"type\": \"category\",\"data1\":[Baby,\"Appliances\"]}}";

        PercentageDiscount pd = new DiscountBuilder().newPercentageDiscount(5,PriceAndCategory);

        String onlyCart = "{\"type1\":{\"type\": \"cart\",\"data1\": 3}}";
        String onlyUserId = "{\"type1\":{\"type\": \"userId\",\"data1\": [\"id1\",\"id2\",\"id3\"]}}";
        String onlyUserAge = "{\"type1\":{\"type\": \"userAge\",\"data1\": 7,\"data2\": 10}}";
        String onlyOnHoursOfTheDay = "{\"type1\":{\"type\": \"OnHoursOfTheDay\",\"data1\": \""+ LocalDateTime.now() +"\",\"data2\": \""+LocalDateTime.now()+"\"} }";
        String onlyOnDaysOfTheWeek = "{\"type1\":{\"type\": \"OnDaysOfTheWeek\",\"data1\": \""+ LocalDateTime.now() +"\",\"data2\": \""+LocalDateTime.now()+"\"} }";
        String onlyOnDayOfMonth = "{\"type1\":{\"type\": \"OnDayOfMonth\",\"data1\": \""+ LocalDateTime.now() +"\",\"data2\": \""+LocalDateTime.now()+"\"} }";
        String OnDayOfMonthAndUserAgeOrCart = "{\"type1\":{\"type\": \"OnDayOfMonth\",\"data1\": \""+ LocalDateTime.now() +"\",\"data2\": \""+LocalDateTime.now()+"\"},\"op1\":\"And\",\"type2\":{\"type\": \"userAge\",\"data1\": 7,\"data2\": 10},\"op2\":\"Or\",\"type3\":{\"type\": \"cart\",\"data1\": 3} }";
        ConditionalPercentageDiscount dp= new DiscountBuilder().newConditionalDiscount(5,OnDayOfMonthAndUserAgeOrCart,PriceAndCategory);

        //System.out.println(dp);
    }

}


