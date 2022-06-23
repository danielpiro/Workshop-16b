package com.example.demo.Tests;


import com.example.demo.ExternalConnections.Old.Delivery.DeliveryNames;
import com.example.demo.ExternalConnections.Old.Delivery.Payment.PaymentNames;
import com.example.demo.ExternalConnections.Old.Delivery.Payment.Visa;
import com.example.demo.History.PurchaseHistory;
import com.example.demo.ShoppingCart.ShoppingCart;
import com.example.demo.Store.Product;
import com.example.demo.StorePermission.Permission;
import com.example.demo.Tests.Bridge.Real;
import org.junit.jupiter.api.*;


import javax.naming.NoPermissionException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


public class AcceptanceTests {

    /*TODO: (Tagged with fail() in the tests)
     * 1) Parallel Use.
     * 2) Logging.
     * 3) External Services.
     */

  //  @Autowired
  //  private TestRestTemplate restTemplate;


    Real proxy;
    String storeId;

    private String product1;
    private String product2;
    private String product3;
    private String product4;


    //todo i think before all?
    @BeforeEach
    void setUp() throws Exception {
        proxy = new Real();
        proxy.openingMarket();






//
//        admin: user AdminID_0, password BigBoss
//        user1: store owner of store1 (store original owner)
//        user2: store manager of store1
//        user3: subscriber
//        guest: GuestID_0

        proxy.register("user1", "11111");
        proxy.login("user1", "11111");
        storeId = proxy.openStore("user1", "store1");
        // store0_id = StoreID_0
        proxy.register("user2", "22222");
        proxy.register("userOwnerToDestroy", "12345");


        proxy.addNewStoreManager(storeId, "user1", "user2");
        List<Permission> permission = new ArrayList<>();
        permission.add(Permission.INFO_OF_MANAGERS);
        permission.add(Permission.VIEW_FORUM);
        proxy.addNewStoreOwner(storeId, "user1","userOwnerToDestroy",permission);
        proxy.register("user3", "33333");
        proxy.getInToTheSystem(); // for guest

         product1 = proxy.addProductToStore(storeId, "user1", "p0", 5.0f, 0, "Other");
         product2 = proxy.addProductToStore(storeId, "user1", "p1", 10.0f, 5, "Other");
         product3 = proxy.addProductToStore(storeId, "user1", "p2", 15.0f, 10, "Other");
         product4 = proxy.addProductToStore(storeId, "user1", "p3", 20.0f, 15, "Other");
    }

    @AfterEach
    void tearDown() {
//        proxy.setUsers(new ArrayList<>());
//        proxy.setStores(new ArrayList<>());
    }

    @Test
    void pass_sanity_test_success_case() {
        assertTrue(true);
    }




    /**
     *  System requirement - I.1
     *  Important Note: This test is a must! otherwise all the tests can't have a proper set-up!
     **/
    @Test
    void opening_market_system_success_case_test() throws Exception {
//        -Check that all the external services' connection are valid
//        -Check that there is a system founder
//        -Check that all users and stores uploaded successfully

//        tearDown(); // Because set-up method is the openMarket

//        String result=  mockMvc.perform(get("/api/search/name?userId=dan&productName=pump" )
//                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();
//
//
//        ReturnValue rv = new Gson().fromJson(result,ReturnValue.class);
//        if (rv.isSuccess() == true)
//            fail();

        assertEquals("system opened successfully", proxy.openingMarket());
    }

    @Test
    void cart_invariant_failed_purchase() {
        proxy.increaseProductQuantityInShoppingCart("user1",product2,storeId,1,false);
        ShoppingCart scBefore = proxy.getShoppingCart("user1");
        proxy.removePayment(PaymentNames.Visa);
        proxy.removePayment(PaymentNames.Visa);

        try {
            proxy.purchaseShoppingCart("user1", PaymentNames.Visa, DeliveryNames.FedEx);
        }catch (Exception e){
            if (!e.getMessage().equals("could not purchase, because couldn't not obtain external connection of"+ PaymentNames.Visa))
                fail();
        }
        ShoppingCart scAfter = proxy.getShoppingCart("user1");

        assertTrue(scBefore.equals(scAfter));

    }

    @Test
    void cart_invariant_failed_purchase_not_enough_in_stock() {
        proxy.increaseProductQuantityInShoppingCart("user1",product1,storeId,1,false);
        ShoppingCart scBefore = proxy.getShoppingCart("user1");


        try {
            proxy.purchaseShoppingCart("user1", PaymentNames.Visa, DeliveryNames.FedEx);
        }catch (Exception e){
            if (!e.getMessage().equals( "not enough items in stock in the store"))
                fail();
        }
        ShoppingCart scAfter = proxy.getShoppingCart("user1");

        assertTrue(scBefore.equals(scAfter));

    }


    @Test
    void guest_login_cart_invariant() {
        String guest = proxy.getGuest();

        proxy.increaseProductQuantityInShoppingCart(guest,product1,storeId,5,false);
        ShoppingCart sc = proxy.getShoppingCart(guest);
        String ans1 = sc.getCartInventory();

        System.out.println(ans1);
        proxy.loginFromGuest(guest,"user3","33333");


        sc = proxy.getShoppingCart("user3");
        String ans2 = sc.getCartInventory();
        System.out.println(ans2);
        assertEquals(ans1,ans2);
        assertTrue( sc.containsStore(storeId));

    }




    @Test
    void relogin_cart_invariant() {
       proxy.login("user1","11111");
       proxy.increaseProductQuantityInShoppingCart("user1",product1,storeId,5,false);
       ShoppingCart sc = proxy.getShoppingCart("user1");
        String ans1 = sc.getCartInventory();
        proxy.logout("user1");
        proxy.login("user1","11111");

         sc = proxy.getShoppingCart("user1");
        String ans2 = sc.getCartInventory();

        assertEquals(ans1,ans2);
        assertTrue( sc.containsStore(storeId));

    }




    @Test
    void remove_delivery_service_success_case() {
        //assertTrue(proxy.removeDelivery("UPS"));
        assertTrue(proxy.removeDelivery(DeliveryNames.FedEx));
    }
    @Test
    void add_delivery_service_success_case() {
        //assertTrue(proxy.removeDelivery("UPS"));
        assertTrue(proxy.removeDelivery(DeliveryNames.FedEx));
    }

    /**
     *  System requirement - I.3
     **/

    @Test
    void payment_success_case_test() {
//        -Check that the payment service connection is valid
//        -Check payment details
//        -Check payment service answer

        // "Payment done successfully" - returning 0 means paid successfully
        assertTrue( proxy.payment(PaymentNames.Visa, 50)>=0);
    }
    @Test
    void payment_fail_case_test() {
//        -Check that the payment service connection is valid
//        -Check payment details
//        -Check payment service answer
//              -> payment service returns an invalid answer (= the payment can't be done)

        // "Failed to do payment" - returning -1
        assertNotEquals(0, proxy.payment(PaymentNames.Visa, -50));
    }


    /**
     *  System requirement - I.4
     **/
    @Test
    void delivery_success_case_test() {
//        -Check that the delivery service connection is valid
//        -Check delivery details
//        -Check delivery service answer

        //"Delivery done successfully"
        assertTrue( proxy.delivery(DeliveryNames.FedEx, 2)>=0);
    }
    @Test
    void delivery_fail_case_test() {
//        -Check that the delivery service connection is valid
//        -Check delivery details
//        -Check delivery service answer
//              -> payment service returns an invalid answer (= the delivery can't be done)

        assertNotEquals(0, proxy.delivery(DeliveryNames.FedEx, -2));
    }













    @Test
    void search_product_success_case_test() throws Exception {
//        - User inserting the product he is looking for...
//        - System shows all the same products (from all stores).
//        - Check all the same products are presented to the costumer (will be successful).

        // "showing all the products that were searched by the user"
        // In this case we search for a specific product for test convenience..
        // List<Product> productList = new ArrayList<>();

//
//        String result=  mockMvc.perform(get("/api/search/name?userId=amit&productName=p1" )
//                    .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();
//
//
//        ReturnValue rv = new Gson().fromJson(result,ReturnValue.class);
//        if (rv.isSuccess() == false)
//            fail();
//        System.out.println(rv.getValue());

        //rv.getValue()








        assertEquals("p1", proxy.searchProduct("user1", "p1").get(0).getName());
    }





    /**
     *  User requirement - II.1.3
     **/
    @Test
    void register_success_case_test() {
//        - Perform Register (entering username and password) & validate registration details (will return TRUE)
//        - Check that the user is still "visitor/buyer" (and not a logged-in user - until he performs login).
//        - Send success message...

        assertTrue(proxy.register("user8", "88888"));
    }
    @Test
    void register_fail_case_test1() {
//        - Perform Register (entering username and password) & validate registration details
//              -> username already exists in system.
//        - Send failure message...

        assertFalse(proxy.register("user1", "11111"));
    }

    //todo Abed - cannot accept null in username or password **done**
    @Test
    void register_fail_case_test2() {
//        - Perform Register (entering username and password) & validate registration details
//              -> username is not match the requirements for proper username in the system.
//        - Send failure message...

        assertFalse(proxy.register(null, "11111"));
    }

    //todo Abed - cannot accept null in username or password **done**
    @Test
    void register_fail_case_test3() {
//        - Perform Register (entering username and password) & validate registration details
//              -> password is not match the requirements for proper password in the system.
//        - Send failure message...

        assertFalse(proxy.register("user0", null));
    }

    /**
     *  User requirement - II.1.4
     **/

    @Test
    void login_fail_case_test1() {
//        - Perform Login (entering username and password) & validate login details
//              -> username does not exists in system.
//        - Send failure message...

        assertFalse(proxy.login("user10", "11111"));
    }
    @Test
    void login_fail_case_test2() {
//        - Perform Login (entering username and password) & validate login details
//              -> password does not match the username's user in system.
//        - Send failure message...

        assertFalse(proxy.login("user1", "111111"));
    }
    @Test
    void login_fail_case_test3() {
//        - Perform Login (entering username and password) & validate login details
//              -> user is already logged in
//        - Send failure message...

        assertFalse(proxy.login("user1", "11111"));
    }





    @Test
    void search_product_fail_case_test() {
//        - User inserting the product he is looking for...
//        - System shows all the same products (from all stores).
//        - Check all the same products are presented to the costumer
//              -> will be empty/missing product/missing store.

        //"there's no such product in the system (in any store)"
        List<Product> productList = new ArrayList<>(); // list will stay empty
        assertEquals(productList, proxy.searchProduct("user1", "p10"));
    }

    /**
     *  User requirement - II.2.3
     **/




    @Test
    void save_products_from_store_to_shopping_cart_fail_case_test() {
//        - User selecting products from specific store.
//        - User perform save to these products.
//        - Check that the user is not a visitor.
//        - Check that all the saved products are in the user's shopping cart
//              -> the shopping cart will be empty/missing product.

        // "there's no such product in the store / there's no quantity left to this product"
        HashMap<String,List<Product>> products =proxy.getAllProductsAndStores("user1");
        assertFalse(proxy.saveProductFromStoreToShoppingCart("user1", "fuck you" , storeId,
                1, false));

    }



    @Test
    void decrease_product_quantity_in_shopping_cart_fail_case_test() {
//        - User selecting product from his shopping cart and decrease its quantity.
//        - Check that the product's quantity has decreased
//              ->  the product's quantity did not decreased

        // "fail - visitor user can't use shopping cart (need to be at least buyer)"
        boolean b = false;

        b = proxy.decreaseProductQuantityInShoppingCart("user00", product1, storeId,1);

        if(b)
            fail();
    }


    /**
     *  User requirement - II.3.1
     **/
    @Test
    void logout_success_case_test() {
//        - Perform logout.
//        - Check that user was logged-in.
//        - The system saving the user's shopping cart.
//        - The user is now a visitor (when login again, he will get his cart).

        // "logout done successfully"
        assertTrue(proxy.logout("user1"));
    }
    @Test
    void logout_fail_case_test() {
//        - Perform logout.
//        - Check that user was logged-in
//              -> the user is not logged in.
//        - Show fail massage...

        // "fail - this user isn't logged in"
        assertFalse(proxy.logout("user100"));
    }

    /**
     *  User requirement - II.3.2
     **/
    @Test
    void open_store_success_case_test() {
//        - User inserting new store details.
//        - The system checks these details (will be valid) & check user is registered/logged in.
//        - Opening store will be activated & check that new permissions were given to the user (as store owner).
//        - Show success message...

        proxy.login("user3", "33333");
        String s = proxy.openStore("user3", "store2");
        //"store was opened successfully"
        assertTrue(proxy.getAllProductsAndStores("user3").containsKey(s));
    }
//    @Test
//    void open_store_fail_case_test1() {
////        - User inserting new store details.
////        - The system checks these details (will be valid) & check user is registered/logged in ->
////                  -> store name is already exists.
////        - Show fail message...
//        proxy.login("user2", "22222");
//
//        assertEquals("fail - this store name is already taken", proxy.openStore("user2", "StoreID_0"));
//        //TODO: seems like store name is irrelevant! - Fix it!
//    }
    @Test
    void open_store_fail_case_test2() {
//        - User inserting new store details.
//        - The system checks these details (will be valid) & check user is registered/logged in ->
//                  -> user is not registered/logged-in to his user.
//        - Show fail message...

        // "fail - registration & login are required in order to open a store"
        assertEquals("failed to open store",
                proxy.openStore("user3", "store2")); //Not logged in
    }
    @Test
    void open_store_fail_case_test3() {
//        - User inserting new store details.
//        - The system checks these details (will be valid) & check user is registered/logged in ->
//                  -> user is not registered/logged-in to his user.
//        - Show fail message...

        // "fail - registration & login are required in order to open a store"
        assertEquals("failed to open store",
                proxy.openStore("user10", "store2")); //Not registered
    }
    @Test
    void open_store_fail_case_test4() {
//        - User inserting new store details.
//        - The system checks these details (will be valid) & check user is registered/logged in ->
//                  -> store details are invalid (not matching the store details required in the system).
//        - Show fail message...

        // "fail - store name is not valid"
        assertEquals("failed to open store", proxy.openStore("user2", "st2"));
    }

    /**
     *  User requirement - II.4.1
     **/
    @Test
    void store_management_add_product_success_case_test() {
//        - Check that the user is logged in as store owner.
//        - User inserting new product details.
//        - Adding product to store activated.
//        - Check that the product is now in the store (will be true).

        // "product was added to the store successfully"
        Object[] s = proxy.getAllProductsAndStores("user1").keySet().toArray();
        String str = s[0].toString();
        assertFalse(!proxy.addProductToStore(str, "user1", product4,
                    5.0f, 1, "Other").isEmpty());
    }
    @Test
    void store_management_add_product_fail_case_test1() {
//        - Check that the user is logged in as store owner.
//                  -> user isn't logged in as store owner.
//        - Show fail message...

        Object[] s = proxy.getAllProductsAndStores("user1").keySet().toArray();
        String str = s[0].toString();
        // "fail - user has to be at least shop owner and to be logged in"
        boolean b = false;

        assertFalse(!proxy.addProductToStore(str, "user2", product4,
                    5.0f, 1, "Other").isEmpty()); //user not logged in

    }
    @Test
    void store_management_add_product_fail_case_test2() {
//        - Check that the user is logged in as store owner.
//                  -> user isn't registered in as store owner.
//        - Show fail message...

        Object[] s = proxy.getAllProductsAndStores("user1").keySet().toArray();
        String str = s[0].toString();
        // "fail - user has to be at least shop owner and to be logged in"
        boolean b = false;

        assertFalse(!proxy.addProductToStore(str, "user44", product1,
                    5.0f, 1, "Other").isEmpty());

    }
//    @Test
//    void store_management_add_product_fail_case_test3() {
////        - Check that the user is logged in as store owner.
////        - User inserting new product details.
////                  -> the details inserted isn't match details info in the system.
////        - Show fail message...
//
//        // "fail - product name/price/quantity is not valid"
//        assertFalse(proxy.addProductToStore("StoreID_0", "user1", "ppp...",
//                5.0f, 1, "Other"));
//        //TODO: seems like there is no restriction on product name! Fix it!
//    }
//    @Test
//    void store_management_add_product_fail_case_test4() {
////        - Check that the user is logged in as store owner.
////        - User inserting new product details.
////                  -> the details inserted isn't match details info in the system.
////        - Show fail message...
//
//        // "fail - product name/price/quantity is not valid"
//        assertFalse(proxy.addProductToStore("StoreID_0", "user1", "p4",
//                        -5.0f, 1, "Other"));
//        //TODO: seems like there is no restriction on product price! Fix it!
//    }
    @Test
    void store_management_add_product_fail_case_test5() {
//        - Check that the user is logged in as store owner.
//        - User inserting new product details.
//                  -> the details inserted isn't match details info in the system.
//        - Show fail message...

        Object[] s = proxy.getAllProductsAndStores("user1").keySet().toArray();
        String str = s[0].toString();
        // "fail - product name/price/quantity is not valid"

            assertFalse(!proxy.addProductToStore(str, "user44", product1,
                    5.0f, -1, "Other").isEmpty());


    }

    @Test
    void store_management_remove_product_success_case_test() {
//        - Check that the user is logged in as store owner.
//        - User inserting an existing product details.
//        - Removing product from store activated.
//        - Check that the product is now not in the store (will be true).

        Object[] s = proxy.getAllProductsAndStores("user1").keySet().toArray();
        String str = s[0].toString();
        // "product was removed from the store successfully"
        boolean b = false;

        b=    proxy.removeProductFromStore(str, "user1", "p3");
        assertFalse(b);

    }
    @Test
    void store_management_remove_product_fail_case_test1() {
//        - Check that the user is logged in as store owner.
//               -> user isn't logged in as store owner.
//        - Show fail message...

        Object[] s = proxy.getAllProductsAndStores("user1").keySet().toArray();
        String str = s[0].toString();
        // "fail - user has to be at least shop owner and to be logged in"
        boolean b = false;

         b= proxy.removeProductFromStore(str, "user4", product3); //not logged in
        assertFalse(b);

    }
    @Test
    void store_management_remove_product_fail_case_test2() {
//        - Check that the user is logged in as store owner.
//              -> user isn't registered in as store owner.
//        - Show fail message...

        Object[] s = proxy.getAllProductsAndStores("user1").keySet().toArray();
        String str = s[0].toString();
        // "fail - user has to be at least shop owner and to be logged in"
        boolean b = false;

         b=   proxy.removeProductFromStore(str, "user44", product3); //not registered
        assertFalse(b);

    }
    @Test
    void store_management_remove_product_fail_case_test3() {
//        - Check that the user is logged in as store owner.
//        - User inserting existing product details.
//              -> there is no product in the store with these details.
//        - Show fail message...


        // "failed to remove product (check storeName or productName)"
        boolean b = proxy.removeProductFromStore(storeId, "user1", "");

        assertTrue(!b);
    }

    @Test
    void store_management_edit_product_success_case_test() {
//        - Check that the user is logged in as store owner.
//        - User inserting an existing product details & updated details to that product.
//        - Editing product in store activated.
//        - Check that the product is now updated (will be true).


        String str2 = "";
        for (List<Product> p : proxy.getAllProductsAndStores("user1").values()){
             str2 = p.get(0).getId();
             break;
        }
        // "product was edited in the store successfully"
        assertTrue(proxy.editProductInStore(storeId, "user1", product1, 50,
                                        "prod1", 10.0f, "Other"));
    }
    @Test
    void store_management_edit_product_fail_case_test1() {
//        - Check that the user is logged in as store owner.
//              -> user isn't logged in as store owner.
//        - Show fail message...


        // "fail - user has to be at least shop owner and to be logged in"


        boolean b = proxy.editProductInStore(storeId, "user2", product1, 50,
                "prod1", 10.0f, "Other"); // not logged in


        assertTrue(!b);

    }
    @Test
    void store_management_edit_product_fail_case_test2() {
//        - Check that the user is logged in as store owner.
//               -> user isn't registered as store owner.
//        - Show fail message...

        Object[] s = proxy.getAllProductsAndStores("user1").keySet().toArray();
        String str = s[0].toString();
        // "fail - user has to be at least shop owner and to be logged in"
        boolean b = false;

        b=proxy.editProductInStore(str, "user22", "ProductID_0", 50,
                    "prod1", 10.0f, "Other"); // not registered

        if(b)
            fail();
    }
    @Test
    void store_management_edit_product_fail_case_test3() {
//        - Check that the user is logged in as store owner.
//        - User inserting an existing product details & updated details to that product.
//               -> there is no product in the store with these details.
//        - Show fail message...

        Object[] s = proxy.getAllProductsAndStores("user1").keySet().toArray();
        String str = s[0].toString();
        // "fail - new product name/price/quantity is not valid"
        boolean b = false;

         b=   proxy.editProductInStore(str, "user1", "p1...", 50,
                    "newProd", 10.0f, "Other");
        if(b)
            fail();
    }
    @Test
    void store_management_edit_product_fail_case_test4() {
//        - Check that the user is logged in as store owner.
//        - User inserting an existing product details & updated details to that product.
//               -> there is no product in the store with these details.
//        - Show fail message...

        Object[] s = proxy.getAllProductsAndStores("user1").keySet().toArray();
        String str = s[0].toString();
        // "fail - product name/price/quantity is not valid"
        boolean b = false;

         b=   proxy.editProductInStore(str, "user1", "ProductID_0", 50,
                    "prod1", -10.0f, "Other");

        if(b)
            fail();
    }
    @Test
    void store_management_edit_product_fail_case_test5() {
//        - Check that the user is logged in as store owner.
//        - User inserting an existing product details & updated details to that product.
//                  -> there is no product in the store with these details.
//        - Show fail message...

        Object[] s = proxy.getAllProductsAndStores("user1").keySet().toArray();
        String str = s[0].toString();
        // "fail - product name/price/quantity is not valid"
        boolean b = false;

         b=   proxy.editProductInStore(str, "user1", "ProductID_0", -50,
                    "prod1", 10.0f, "Other");

        if(b)
            fail();
    }
    @Test
    void store_management_edit_product_fail_case_test6() {
//        - Check that the user is logged in as store owner.
//        - User inserting an existing product details & updated details to that product.
//                  -> there is no product in the store with these details.
//        - Show fail message...

        // "fail - product name/price/quantity is not valid"
        boolean b =  proxy.editProductInStore("store1...", "user1", "ProductID_0", 50,
                "prod1", 10.0f, "Other");

        assertTrue(!b);
    }

    /**
     *  User requirement - II.4.2
     **/


    /**
     *  User requirement - II.4.4
     **/
    @Test
    void adding_store_owner_success_case_test() {
//        - Check that the user (adding the new store owner) is logged in as store owner.
//        - User inserting the new store owner details.
//        - Check that the new owner is registered to the system and not already a store owner.
//        - Adding store owner to the store is activated.
//        - Check that the new owner has been added (will be true).


        // "the user is now store owner"
        assertTrue(proxy.addNewStoreOwner(storeId, "user1", "user3",
                                        new ArrayList<>()/*permissions*/));
    }
    @Test
    void adding_store_owner_fail_case_test1() {
//        - Check that the user (adding the new store owner) is logged in as store owner.
//                  -> the user isn't logged in as a store owner.
//        - Show fail message...


        // "fail - user has to be at least shop owner and to be logged in"

        boolean b = proxy.addNewStoreOwner(storeId, "user2", "user3",
                new ArrayList<>()/*permissions*/); // not logged in

        if(b)
            fail();
    }
    @Test
    void adding_store_owner_fail_case_test2() {
//        - Check that the user (adding the new store owner) is logged in as store owner.
//                  -> the user isn't registered as a store owner.
//        - Show fail message...

        Object[] s = proxy.getAllProductsAndStores("user1").keySet().toArray();
        String str = s[0].toString();
        // "fail - user has to be at least shop owner and to be logged in"
        boolean b = false;
          b=  proxy.addNewStoreOwner(str, "user12", "user3",
                    new ArrayList<>()/*permissions*/); // not registered
        assertFalse(b);


    }
    @Test
    void adding_store_owner_fail_case_test3() {
//        - Check that the user (adding the new store owner) is logged in as store owner.
//        - User inserting the new store owner details.
//        - Check that the new owner is registered to the system and not already a store owner
//                  -> the new owner inserted isn't a user in the system
//        - Show fail message...

        Object[] s = proxy.getAllProductsAndStores("user1").keySet().toArray();
        String str = s[0].toString();
        // "fail - store name or username is invalid"
        boolean b = false;

         b=   proxy.addNewStoreOwner(str, "user1", "user44",
                    new ArrayList<>()/*permissions*/);
        assertFalse(b);

    }
    @Test
    void adding_store_owner_fail_case_test4() {
//        - Check that the user (adding the new store owner) is logged in as store owner.
//        - User inserting the new store owner details.
//        - Check that the new owner is registered to the system and not already a store owner
//                  -> the new owner inserted is already a store owner
//        - Show fail message...

        Object[] s = proxy.getAllProductsAndStores("user1").keySet().toArray();
        String str = s[0].toString();
        // "fail - this user is already managing/owning a store in the system"
        assertFalse(proxy.addNewStoreOwner(str, "user1", "user2",
                new ArrayList<>()/*permissions*/));

    }
    @Test
    void adding_store_owner_fail_case_test5() {
//        - Check that the user (adding the new store owner) is logged in as store owner.
//        - User inserting the new store owner details.
//        - Check that the new owner is registered to the system and not already a store owner
//                  -> the store details are invalid
//        - Show fail message...

        boolean b = false;
        try{
            proxy.addNewStoreOwner("Store1", "user1", "user3",
                    new ArrayList<>()/*permissions*/);
        }
        catch (Exception e){
            b = true;
            assertTrue(true);
        }
        if(!b)
            fail();
    }

    /**
     *  User requirement - II.4.6
     **/
    @Test
    void adding_store_manager_success_case_test() {
//        - Check that the user (adding the new store manager) is logged in as store owner.
//        - User inserting the new store manager details.
//        - Check that the new owner is registered to the system and not already a store owner or store manager.
//        - Adding store manager to the store is activated.
//        - Check that the new manager has been added (will be true).


        // "the user is now store manager"
        assertTrue(proxy.addNewStoreManager(storeId, "user1", "user3"));
    }
    @Test
    void adding_store_manager_fail_case_test1() {
//        - Check that the user (adding the new store owner) is logged in as store owner.
//                  -> the user isn't logged in as a store owner.
//        - Show fail message...

        Object[] s = proxy.getAllProductsAndStores("user1").keySet().toArray();
        String str = s[0].toString();
        // "fail - user has to be at least shop owner and to be logged in"
        boolean b = proxy.addNewStoreManager(str, "user2", "user3"); //not logged in

        if(b)
            fail();
    }
    @Test
    void adding_store_manager_fail_case_test2() {
//        - Check that the user (adding the new store owner) is logged in as store owner.
//                  -> the user isn't registered as a store owner.
//        - Show fail message...

        Object[] s = proxy.getAllProductsAndStores("user1").keySet().toArray();
        String str = s[0].toString();
        // "fail - user has to be at least shop owner and to be logged in"
        boolean b = proxy.addNewStoreManager(str, "user12", "user3"); //not registered
        if(b)
            fail();
    }
    @Test
    void adding_store_manager_fail_case_test3() {
//        - Check that the user (adding the new store manager) is logged in as store owner.
//        - User inserting the new store manager details.
//        - Check that the new owner is registered to the system and not already a store owner or store manager
//                  -> the new manager inserted isn't a user in the system
//        - Show fail message...

        // "fail - store name or username is invalid"
        boolean b = false;

        b= proxy.addNewStoreManager("store1...", "user1...", "user5");

        if(b)
            fail();
    }
    @Test
    void adding_store_manager_fail_case_test4() {
//        - Check that the user (adding the new store manager) is logged in as store owner.
//        - User inserting the new store manager details.
//        - Check that the new manager is registered to the system and not already a store owner or store manager
//                  -> the new owner inserted is already a store owner or store manager
//        - Show fail message...

        Object[] s = proxy.getAllProductsAndStores("user1").keySet().toArray();
        String str = s[0].toString();
        assertFalse(proxy.addNewStoreManager(str, "user1", "user2"));
    }

    /**
     *  User requirement - II.4.7
     **/
    @Test
    void change_store_manager_permissions_success_case_test() {
//        - Check that the user (changing the store manager permissions) is logged in as store owner.
//        - User changing the store manager permissions.
//        - Check that the manager's permissions has been updated (will be true).

//        assertEquals("store manager permission has changed successfully",
//                proxy.changeStoreManagerPermissions("StoreID_0", "user3", "ShopOwner"));

        try {
            List<String> permission = new ArrayList<>();
            permission.add("INFO_OF_MANAGERS");
            assertTrue(proxy.changeStoreManagerPermissions(storeId, "user1", "userOwnerToDestroy",permission));

        } catch (NoPermissionException e) {
            e.printStackTrace();
            fail();
        }
    }
    @Test
    void change_store_manager_permissions_fail_case_test1() {
//        - Check that the user (changing the store manager permissions) is logged in as store owner.
//                  -> the user isn't logged/registered in as a store owner.
//        - Show fail message...
        try {
            List<String> permission = new ArrayList<>();
            permission.add("INFO_OF_MANAGERS");
            proxy.logout("user1");
            assertFalse(proxy.changeStoreManagerPermissions(storeId, "user1", "userOwnerToDestroy",permission));
            fail();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }


    /**
     *  User requirement - II.4.9
     **/
    @Test
    void close_store_success_case_test() {
//        - Check that the user is logged in as store owner.
//        - User closing the store.
//        - Check that the store is closed (will be true).


        // "the store has closed successfully"
        assertTrue(proxy.freezeStoreByOwner(storeId, "user1"));
    }
    @Test
    void close_store_fail_case_test1() {
//        - Check that the user is logged in as store owner.
//                  -> the user isn't a store owner.
//        - Show fail message...


        // "fail - user has to be at least shop owner and to be logged in"
        boolean b = proxy.freezeStoreByOwner(storeId, "user2"); //not logged in

        if(b)
            fail();
    }
    @Test
    void close_store_fail_case_test2() {
//        - Check that the user is logged in as store owner.
//                  -> the user isn't logged in.
//        - Show fail message...


        // "fail - user has to be at least shop owner and to be logged in"
        boolean b = false;

        b=    proxy.freezeStoreByOwner(storeId, "user22"); //not registered
        assertFalse(b);

    }

    /**
     *  User requirement - II.4.10
     **/
    @Test
    void unfreeze_store_success_case_test() {
//        - Check that the user is logged in as store owner.
//        - User re-open the store.
//        - Check that the store is open (will be true).


        proxy.freezeStoreByOwner(storeId, "user1");
        assertTrue(proxy.unfreezeStoreByOwner(storeId, "user1"));
    }
    @Test
    void unfreeze_store_fail_case_test1() {
//        - Check that the user is logged in as store owner.
//                  -> the user isn't a store owner.
//        - Show fail message...


        // "fail - user has to be at least shop owner and to be logged in"
        boolean b = proxy.unfreezeStoreByOwner(storeId, "user2"); //not logged in

        if(b)
            fail();
    }
    @Test
    void unfreeze_store_fail_case_test2() {
//        - Check that the user is logged in as store owner.
//                  -> the user isn't logged in.
//        - Show fail message...


        // "fail - user has to be at least shop owner and to be logged in"
        boolean b = false;

         b=   proxy.unfreezeStoreByOwner(storeId, "user44"); //not registered
        assertFalse(b);

    }

    /**
     *  User requirement - II.4.11
     **/
    @Test
    void show_store_officials_success_case_test() { //officials = store owners/store managers
//        - Check that the user is logged in as store owner.
//        - Activate presenting all store's official (will show all the officials correctly).


        // "showing all the officials..."
        assertTrue(proxy.showStoreOfficials(storeId, "user1"));
    }
    @Test
    void show_store_officials_fail_case_test1() { //officials = store owners/store managers
        //- Check that the user is logged in as store owner.
        //          -> the user isn't a store owner.
        //- Show fail message...


        // "fail - user has to be at least shop owner and to be logged in"
        assertFalse(proxy.showStoreOfficials(storeId, "user3")); //not store owner
    }
    @Test
    void show_store_officials_fail_case_test2() { //officials = store owners/store managers
        //- Check that the user is logged in as store owner.
        //          -> the user isn't logged in as store owner.
        //- Show fail message...


        // "fail - user has to be at least shop owner and to be logged in"
        assertFalse(proxy.showStoreOfficials(storeId, "user2")); //not logged in
    }
    @Test
    void show_store_officials_fail_case_test3() { //officials = store owners/store managers
//        - Check that the user is logged in as store owner.
//        - Activate presenting all store's official
//                  -> will fail to show the officials / will miss at least one of them

        // "fail - the user is not owner on that store (Check store name)"
        boolean b = false;

         b=   proxy.showStoreOfficials("s1", "user1");
        assertFalse(b);


    }

    /**
     *  User requirement - II.4.13
     **/
    @Test
    void show_store_purchase_history_success_case_test() {
//        - Check that the user is logged in as store owner.
//        - Activate presenting all store's purchase history (will show it all correctly).


        List<PurchaseHistory> list = new ArrayList<>(); //TODO: When purchase done, check this method with few purchases
        // "showing all the purchase history..."
        assertEquals(list, proxy.showStorePurchaseHistory(storeId));
    }
    @Test
    void show_store_purchase_history_fail_case_test() {
        //- Check that the user is logged in as store owner.
        //- Activate presenting all store's purchase history (will show it all correctly)
        //          -> will not show all purchase history / will miss at least one of the purchases

        List<PurchaseHistory> list = new ArrayList<>();
        // "fail - the user is not owner on that store (Check store name)"
        assertEquals(list, proxy.showStorePurchaseHistory("store1..."));
    }


}
