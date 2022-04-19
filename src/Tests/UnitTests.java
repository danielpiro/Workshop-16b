package Tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitTests {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    private void pass() {
        assertTrue(true);
    }

//    @Test
//    void sanity_test_success_case() {
//        pass();
//    }
//
//    @Test
//    void sanity_test_fail_case() {
//        assertTrue(false);
//    }

//region Service-Level Requirements

    // Service-Level Requirements
    // ==========================

    /**
     *  Need to implement thread-base system
     *  (requirement 1.b in V1)
     **/
    @Test
    void parallel_use_success_case_test() {
        System.out.println("Need to implement thread-base system");
        fail();
    }
    @Test
    void parallel_use_fail_case_test() {
        System.out.println("Need to implement thread-base system");
        fail();
    }

    /**
     *  Need to create log file - contains error logs
     *  (requirement 1.c in V1)
     **/
    @Test
    void check_log_success_case_test() {
        System.out.println("Need to create log file - contains error logs");
        fail();
    }
    @Test
    void check_log_fail_case_test() {
        System.out.println("Need to create log file - contains error logs");
        fail();
    }

//endregion Service-Level Requirements


//region Functional Requirements

    // Functional Requirements
    // ==========================

//region System requirements

    /**
     *  System requirement - I.1
     **/
    @Test
    void opening_market_system_success_case_test() {
        System.out.println("-Check that all the external services connection are valid\n" +
                           "-Check that there is a system manager");
        fail();
    }
    @Test
    void opening_market_system_fail_case_test1() {
        // No system manager is exists in the system.
        System.out.println("-Check that all the external services connection are valid\n" +
                           "-Check that there is a system manager");
        fail();
    }
    @Test
    void opening_market_system_fail_case_test2() {
        // No payment system is exists in the system.
        System.out.println("-Check that all the external services connection are valid\n" +
                           "-Check that there is a system manager");
        fail();
    }
    @Test
    void opening_market_system_fail_case_test3() {
        // No delivery system is exists in the system.
        System.out.println("-Check that all the external services connection are valid\n" +
                           "-Check that there is a system manager");
        fail();
    }
    @Test
    void opening_market_system_fail_case_test4() {
        // No security system is exists in the system.
        System.out.println("-Check that all the external services connection are valid\n" +
                           "-Check that there is a system manager");
        fail();
    }

    /**
     *  System requirement - I.2
     **/
    // testing change/edit external service connection
    @Test
    void change_external_service_success_case_test() {
        System.out.println("-Check that all actions in the system work well\n" +
                           "-Check that the change done successfully");
        fail();
    }
    @Test
    void change_external_service_fail_case_test1() {
        // the change itself failed
        System.out.println("-Check that all actions in the system work well\n" +
                           "-Check that the change done successfully");
        fail();
    }
    @Test
    void change_external_service_fail_case_test2() {
        // the change caused an issue in the system actions
        System.out.println("-Check that all actions in the system work well\n" +
                           "-Check that the change done successfully");
        fail();
    }

    // testing switch external service connection
    @Test
    void switch_external_service_success_case_test() {
        System.out.println("-Check that all actions in the system work well\n" +
                           "-Check that the switch done successfully");
        fail();
    }
    @Test
    void switch_external_service_fail_case_test1() {
        // the change itself failed
        System.out.println("-Check that all actions in the system work well\n" +
                           "-Check that the switch done successfully");
        fail();
    }
    @Test
    void switch_external_service_fail_case_test2() {
        // the change caused an issue in the system actions
        System.out.println("-Check that all actions in the system work well\n" +
                           "-Check that the switch done successfully");
        fail();
    }

    // testing add external service connection
    @Test
    void add_external_service_success_case_test() {
        System.out.println("-Check that all actions in the system work well\n" +
                           "-Check that the adding done successfully");
        fail();
    }
    @Test
    void add_external_service_fail_case_test1() {
        // the change itself failed
        System.out.println("-Check that all actions in the system work well\n" +
                           "-Check that the adding done successfully");
        fail();
    }
    @Test
    void add_external_service_fail_case_test2() {
        // the change caused an issue in the system actions
        System.out.println("-Check that all actions in the system work well\n" +
                           "-Check that the adding done successfully");
        fail();
    }

    /**
     *  System requirement - I.3
     **/
    @Test
    void payment_success_case_test() {
        System.out.println("""
                -Check that the payment service connection is valid
                -Check payment details
                -Check payment service answer
                """);
        fail();
    }
    @Test
    void payment_fail_case_test1() {
        // payment service connection lost
        System.out.println("""
                -Check that the payment service connection is valid");
                -Check payment details
                -Check payment service answer
                """);
        fail();
    }
    @Test
    void payment_fail_case_test2() {
        // payment service returns an invalid answer (= the payment can't be done)
        System.out.println("""
                -Check that the payment services connection is valid
                -Check payment details
                -Check payment service answer
                """);
        fail();
    }

    /**
     *  System requirement - I.4
     **/
    @Test
    void delivery_success_case_test() {
        System.out.println("""
                -Check that the delivery service connection is valid
                -Check delivery details
                -Check delivery service answer
                """);
        fail();
    }
    @Test
    void delivery_fail_case_test1() {
        // payment service connection lost
        System.out.println("""
                -Check that the delivery service connection is valid
                -Check delivery details
                -Check delivery service answer
                """);
        fail();
    }
    @Test
    void delivery_fail_case_test2() {
        // payment service returns an invalid answer (= the payment can't be done)
        System.out.println("""
                -Check that the delivery service connection is valid
                -Check delivery details
                -Check delivery service answer
                """);
        fail();
    }

    /**
     *  System requirement - I.5
     **/
    // A user bought a product from a store
    @Test
    void realtime_notification_product_bought_success_case_test() {
        System.out.println("""
                - Product purchase was activated - a user logged in and bought a product
                - Check that the store manager is logged in (will be true in this case)
                - Check the message that just sent (will simulate the realtime message sent)
                """);
        fail();
    }
    @Test
    void realtime_notification_product_bought_fail_case_test() {
        System.out.println("""
                - Product purchase was activated - a user logged in and bought a product
                - Check that the store manager is logged in (will be true in this case)
                - Check the message that just sent (will simulate the realtime message sent)
                    -> MESSAGE DIDN'T ARRIVE
                """);
        fail();
    }

    // A store was closed, then re-opened
    @Test
    void realtime_notification_store_closed_and_opened_success_case_test() {
        System.out.println("""
                - Store owner closed his store (after logged in as store owner)
                - Check that another store manager (of the same store) is logged in (will be true in this case)
                - The store owner close the store
                - Check the message that just sent (will simulate the realtime message sent)
                - The store owner open the store
                - Check that the store manager is logged in (will be true in this case)
                - Check the message that just sent (will simulate the realtime message sent)
                """);
        fail();
    }
    @Test
    void realtime_notification_store_closed_and_opened_fail_case_test1() {
        System.out.println("""
                - Store owner closed his store (after logged in as store owner)
                - Check that another store manager (of the same store) is logged in (will be true in this case)
                - The store owner close the store
                - Check the message that just sent (will simulate the realtime message sent)
                    -> MESSAGE DIDN'T ARRIVE
                """);
        fail();
    }
    @Test
    void realtime_notification_store_closed_and_opened_fail_case_test2() {
        System.out.println("""
                - Store owner closed his store (after logged in as store owner)
                - Check that another store manager (of the same store) is logged in (will be true in this case)
                - The store owner close the store
                - Check the message that just sent (will simulate the realtime message sent)
                - The store owner open the store
                - Check that the store manager is logged in (will be true in this case)
                - Check the message that just sent (will simulate the realtime message sent)
                    -> MESSAGE DIDN'T ARRIVE
                """);
        fail();
    }

    // A user's permissions was removed
    @Test
    void realtime_notification_user_permission_update_success_case_test() {
        System.out.println("""
                - Store owner update one of the store manager permissions (after logged in as store owner)
                - Check that the store manager is logged in (will be true in this case)
                - Check the message that just sent (will simulate the realtime message sent)
                """);
        fail();
    }
    @Test
    void realtime_notification_user_permission_update_fail_case_test() {
        System.out.println("""
                - Store owner update one of the store manager permissions (after logged in as store owner)
                - Check that the store manager is logged in (will be true in this case)
                - Check the message that just sent (will simulate the realtime message sent)
                    -> MESSAGE DIDN'T ARRIVE
                """);
        fail();
    }

    /**
     *  System requirement - I.6
     **/
    // A user bought a product from a store
    @Test
    void offline_notification_product_bought_success_case_test() {
        System.out.println("""
                - Product purchase was activated - a user logged in and bought a product
                - Check that the store manager is logged in (will be true in this case)
                - Check the message that just sent (will simulate the offline message sent)
                """);
        fail();
    }
    @Test
    void offline_notification_product_bought_fail_case_test() {
        System.out.println("""
                - Product purchase was activated - a user logged in and bought a product
                - Check that the store manager is logged in (will be true in this case)
                - Check the message that just sent (will simulate the offline message sent)
                    -> MESSAGE DIDN'T ARRIVE
                """);
        fail();
    }

    // A store was closed, then re-opened
    @Test
    void offline_notification_store_closed_and_opened_success_case_test() {
        System.out.println("""
                - Store owner closed his store (after logged in as store owner)
                - Check that another store manager (of the same store) is logged in (will be true in this case)
                - The store owner close the store
                - Check the message that just sent (will simulate the offline message sent)
                - The store owner open the store
                - Check that the store manager is logged in (will be true in this case)
                - Check the message that just sent (will simulate the offline message sent)
                """);
        fail();
    }
    @Test
    void offline_notification_store_closed_and_opened_fail_case_test1() {
        System.out.println("""
                - Store owner closed his store (after logged in as store owner)
                - Check that another store manager (of the same store) is logged in (will be true in this case)
                - The store owner close the store
                - Check the message that just sent (will simulate the offline message sent)
                    -> MESSAGE DIDN'T ARRIVE
                """);
        fail();
    }
    @Test
    void offline_notification_store_closed_and_opened_fail_case_test2() {
        System.out.println("""
                - Store owner closed his store (after logged in as store owner)
                - Check that another store manager (of the same store) is logged in (will be true in this case)
                - The store owner close the store
                - Check the message that just sent (will simulate the offline message sent)
                - The store owner open the store
                - Check that the store manager is logged in (will be true in this case)
                - Check the message that just sent (will simulate the offline message sent)
                    -> MESSAGE DIDN'T ARRIVE
                """);
        fail();
    }

    // A user's permissions was removed
    @Test
    void offline_notification_user_permission_update_success_case_test() {
        System.out.println("""
                - Store owner update one of the store manager permissions (after logged in as store owner)
                - Check that the store manager is logged in (will be true in this case)
                - Check the message that just sent (will simulate the offline message sent)
                """);
        fail();
    }
    @Test
    void offline_notification_user_permission_update_fail_case_test() {
        System.out.println("""
                - Store owner update one of the store manager permissions (after logged in as store owner)
                - Check that the store manager is logged in (will be true in this case)
                - Check the message that just sent (will simulate the offline message sent)
                    -> MESSAGE DIDN'T ARRIVE
                """);
        fail();
    }

//endregion System requirements

//region User requirements

    /**
     *  User requirement - II.1.1
     **/
    @Test
    void get_in_to_the_system_success_case_test() {
        System.out.println("""
                - Check that the user was "visitor" before get in to the system.
                - Perform getting in to the visitor (include shopping cart).
                - Check that the "visitor" became "buyer" after getting in to the system.
                """);
        fail();
    }
    @Test
    void get_in_to_the_system_fail_case_test1() {
        System.out.println("""
                - Check that the user was "visitor" before get in to the system
                    -> this user is not a "visitor".
                """);
        fail();
    }
    @Test
    void get_in_to_the_system_fail_case_test2() {
        System.out.println("""
                - Check that the user was "visitor" before get in to the system.
                - Perform getting in to the visitor (include shopping cart)
                    -> this user did not get a shopping cart.
                """);
        fail();
    }

    /**
     *  User requirement - II.1.2
     **/
    @Test
    void get_out_of_the_system_success_case_test() {
        System.out.println("""
                - Check that the user was a "buyer" before get out of the system.
                - Perform getting out of the system (include emptying the shopping cart and not "buyer" anymore).
                - Send success message...
                """);
        fail();
    }
    @Test
    void get_out_of_the_system_fail_case_test1() {
        System.out.println("""
                - Check that the user was a "buyer" before get out of the system.
                    -> this user was already out.
                - Send proper fail message...
                """);
        fail();
    }
    @Test
    void get_out_of_the_system_fail_case_test2() {
        System.out.println("""
                - Check that the user was a "buyer" before get out of the system.
                - Perform getting out of the system (include emptying the shopping cart and not "buyer" anymore).
                    -> the shopping cart did not got empty.
                - Send proper fail message...
                """);
        fail();
    }
    @Test
    void get_out_of_the_system_fail_case_test3() {
        System.out.println("""
                - Check that the user was a "buyer" before get out of the system.
                - Perform getting out of the system (include emptying the shopping cart and not "buyer" anymore).
                    -> the user stayed as "buyer".
                - Send proper fail message...
                """);
        fail();
    }

    /**
     *  User requirement - II.1.3
     **/
    @Test
    void register_success_case_test() {
        System.out.println("""
                - Perform Register (entering username and password) & validate registration details (will return TRUE)
                - Check that the user is still "visitor/buyer" (and not a logged-in user - until he performs login).
                - Send success message...
                """);
        fail();
    }
    @Test
    void register_fail_case_test1() {
        System.out.println("""
                - Perform Register (entering username and password) & validate registration details
                    -> username already exists in system.
                - Send failure message...
                """);
        fail();
    }
    @Test
    void register_fail_case_test2() {
        System.out.println("""
                - Perform Register (entering username and password) & validate registration details
                    -> username is not match the requirements for proper username in the system.
                - Send failure message...
                """);
        fail();
    }
    @Test
    void register_fail_case_test3() {
        System.out.println("""
                - Perform Register (entering username and password) & validate registration details
                    -> password is not match the requirements for proper username in the system.
                - Send failure message...
                """);
        fail();
    }

    /**
     *  User requirement - II.1.4
     **/
    @Test
    void login_success_case_test() {
        System.out.println("""
                - Perform Login (entering username and password) & validate login details (will return TRUE)
                - Check that the user is logged-in (shopping cart, permissions...).
                - Send success message...
                """);
        fail();
    }
    @Test
    void login_fail_case_test1() {
        System.out.println("""
                - Perform Login (entering username and password) & validate login details
                    -> username does not exists in system.
                - Send failure message...
                """);
        fail();
    }
    @Test
    void login_fail_case_test2() {
        System.out.println("""
                - Perform Login (entering username and password) & validate login details
                    -> password does not match the username's user in system.
                - Send failure message...
                """);
        fail();
    }

    /**
     *  User requirement - II.2.1
     **/
    @Test
    void receive_system_info_success_case_test() {
        System.out.println("""
                - Check all stores and products are presented to the costumer (will be successful).
                """);
        fail();
    }
    @Test
    void receive_system_info_fail_case_test() {
        System.out.println("""
                - Check all stores and products are presented to the costumer
                    -> will be empty/missing product/missing store.
                - Send failure message...
                """);
        fail();
    }

    /**
     *  User requirement - II.2.2
     **/
    @Test
    void search_product_success_case_test() {
        System.out.println("""
                - User inserting the product he is looking for...
                - System shows all the same products (from all stores).
                - Check all the same products are presented to the costumer (will be successful).
                """);
        fail();
    }
    @Test
    void search_product_fail_case_test() {
        System.out.println("""
                - User inserting the product he is looking for...
                - System shows all the same products (from all stores).
                - Check all the same products are presented to the costumer
                    -> will be empty/missing product/missing store.
                """);
        fail();
    }

    /**
     *  User requirement - II.2.3
     **/
    @Test
    void save_products_from_store_success_case_test() {
        System.out.println("""
                - User selecting products from specific store.
                - User perform save to these products.
                - Check that all the saved products are in the user's shopping cart (will be successful).
                """);
        fail();
    }
    @Test
    void save_products_from_store_fail_case_test() {
        System.out.println("""
                - User selecting products from specific store.
                - User perform save to these products.
                - Check that all the saved products are in the user's shopping cart
                    -> the shopping cart will be empty/missing product.
                """);
        fail();
    }

    /**
     *  User requirement - II.2.4
     **/
    @Test
    void show_shopping_cart_success_case_test() {
        System.out.println("""
                - Check all shopping cart info presented to the costumer (will be successful).
                """);
        fail();
    }
    @Test
    void show_shopping_cart_fail_case_test() {
        System.out.println("""
                - Check all shopping cart info presented to the costumer (will be successful).
                    -> will be empty/missing product/missing store.
                - Send failure message...
                """);
        fail();
    }
    @Test
    void increase_product_quantity_in_shopping_cart_success_case_test() {
        System.out.println("""
                - User selecting product from his shopping cart and increase its quantity.
                - Check that the product's quantity has increased (will be successful).
                """);
        fail();
    }
    @Test
    void increase_product_quantity_in_shopping_cart_fail_case_test() {
        System.out.println("""
                - User selecting product from his shopping cart and increase its quantity.
                - Check that the product's quantity has increased
                    ->  the product's quantity did not increased
                """);
        fail();
    }
    @Test
    void decrease_product_quantity_in_shopping_cart_success_case_test() {
        System.out.println("""
                - User selecting product from his shopping cart and decrease its quantity.
                - Check that the product's quantity has decreased (will be successful).
                """);
        fail();
    }
    @Test
    void decrease_product_quantity_in_shopping_cart_fail_case_test() {
        System.out.println("""
                - User selecting product from his shopping cart and decrease its quantity.
                - Check that the product's quantity has decreased
                    ->  the product's quantity did not decreased
                """);
        fail();
    }
    @Test
    void remove_product_from_shopping_cart_success_case_test() {
        System.out.println("""
                - User selecting product from his shopping cart and remove it from cart.
                - Check that the product isn't in the cart (will be successful).
                """);
        fail();
    }
    @Test
    void remove_product_from_shopping_cart_fail_case_test() {
        System.out.println("""
                - User selecting product from his shopping cart and remove it from cart.
                - Check that the product isn't in the cart
                    ->  the product is still in the cart
                """);
        fail();
    }

    /**
     *  User requirement - II.2.5
     **/
    @Test
    void purchase_shopping_cart_success_case_test() {
        System.out.println("""
                - Perform purchase of the shopping cart.
                - Check all external services returns required answers.
                - Show success message to the user (later on - will need to update quantity in the system).
                """);
        fail();
    }
    @Test
    void purchase_shopping_cart_fail_case_test() {
        System.out.println("""
                - Perform purchase of the shopping cart.
                - Check all external services returns required answers -> will return error message.
                - Show failure message to the user.
                """);
        fail();
    }

    /**
     *  User requirement - II.3.1
     **/
    @Test
    void logout_success_case_test() {
        System.out.println("""
                - Perform logout.
                - Check that user was logged-in.
                - The system saving the user's shopping cart.
                - The user is now a visitor (when login again, he will get his cart).
                """);
        fail();
    }
    @Test
    void logout_fail_case_test1() {
        System.out.println("""
                - Perform logout.
                - Check that user was logged-in
                    -> the user is not logged in.
                - Show fail massage...
                """);
        fail();
    }
    @Test
    void logout_fail_case_test2() {
        System.out.println("""
                - Perform logout.
                - Check that user was logged-in (in this case the user was logged in).
                - The system saving the user's shopping cart.
                    -> saving cart has failed.
                - Show fail massage...
                """);
        fail();
    }
    @Test
    void logout_success_case_test3() {
        System.out.println("""
                - Perform logout.
                - Check that user was logged-in.
                - The system saving the user's shopping cart.
                - Check that the user is now a visitor
                    -> But, he isn't...
                """);
        fail();
    }

    /**
     *  User requirement - II.3.2
     **/
    @Test
    void open_store_success_case_test() {
        System.out.println("""
                - User inserting new store details.
                - The system checks these details (will be valid) & check user is registered/logged in.
                - Opening store will be activated & check that new permissions were given to the user (as store owner).
                - Show success message...
                """);
        fail();
    }
    @Test
    void open_store_fail_case_test1() {
        System.out.println("""
                - User inserting new store details.
                - The system checks these details (will be valid) & check user is registered/logged in ->
                    store name is already exists.
                - Show fail message...
                """);
        fail();
    }
    @Test
    void open_store_fail_case_test2() {
        System.out.println("""
                - User inserting new store details.
                - The system checks these details (will be valid) & check user is registered/logged in ->
                    user is not registered/logged-in to his user.
                - Show fail message...
                """);
        fail();
    }
    @Test
    void open_store_fail_case_test3() {
        System.out.println("""
                - User inserting new store details.
                - The system checks these details (will be valid) & check user is registered/logged in ->
                    store details are invalid (not matching the store details required in the system).
                - Show fail message...
                """);
        fail();
    }
    @Test
    void open_store_fail_case_test4() {
        System.out.println("""
                - User inserting new store details.
                - The system checks these details (will be valid) & check user is registered/logged in.
                - Opening store will be activated & check that new permissions were given to the user (as store owner)
                    -> new permission were not given
                - Show fail message...
                """);
        fail();
    }

    /**
     *  User requirement - II.4.1
     **/
    @Test
    void store_management_add_product_success_case_test() {
        System.out.println("""
                - Check that the user is logged in as store owner.
                - User inserting new product details.
                - Adding product to store activated.
                - Check that the product is now in the store (will be true).
                """);
        fail();
    }
    @Test
    void store_management_add_product_fail_case_test1() {
        System.out.println("""
                - Check that the user is logged in as store owner.
                    -> user isn't logged in as store owner.
                - Show fail message...
                """);
        fail();
    }
    @Test
    void store_management_add_product_fail_case_test2() {
        System.out.println("""
                - Check that the user is logged in as store owner.
                - User inserting new product details.
                    -> the details inserted isn't match details info in the system.
                - Show fail message...
                """);
        fail();
    }
    @Test
    void store_management_add_product_fail_case_test3() {
        System.out.println("""
                - Check that the user is logged in as store owner.
                - User inserting new product details.
                - Adding product to store activated.
                - Check that the product is now in the store (will be FALSE).
                """);
        fail();
    }

    @Test
    void store_management_remove_product_success_case_test() {
        System.out.println("""
                - Check that the user is logged in as store owner.
                - User inserting an existing product details.
                - Removing product from store activated.
                - Check that the product is now not in the store (will be true).
                """);
        fail();
    }
    @Test
    void store_management_remove_product_fail_case_test1() {
        System.out.println("""
                - Check that the user is logged in as store owner.
                    -> user isn't logged in as store owner.
                - Show fail message...
                """);
        fail();
    }
    @Test
    void store_management_remove_product_fail_case_test2() {
        System.out.println("""
                - Check that the user is logged in as store owner.
                - User inserting existing product details.
                    -> there is no product in the store with these details.
                - Show fail message...
                """);
        fail();
    }
    @Test
    void store_management_remove_product_fail_case_test3() {
        System.out.println("""
                - Check that the user is logged in as store owner.
                - User inserting existing product details.
                - Removing product from store activated.
                - Check that the product is now not in the store (will be FALSE).
                """);
        fail();
    }

    @Test
    void store_management_edit_product_success_case_test() {
        System.out.println("""
                - Check that the user is logged in as store owner.
                - User inserting an existing product details & updated details to that product.
                - Editing product in store activated.
                - Check that the product is now updated (will be true).
                """);
        fail();
    }
    @Test
    void store_management_edit_product_fail_case_test1() {
        System.out.println("""
                - Check that the user is logged in as store owner.
                    -> user isn't logged in as store owner.
                - Show fail message...
                """);
        fail();
    }
    @Test
    void store_management_edit_product_fail_case_test2() {
        System.out.println("""
                - Check that the user is logged in as store owner.
                - User inserting an existing product details & updated details to that product.
                    -> there is no product in the store with these details.
                - Show fail message...
                """);
        fail();
    }
    @Test
    void store_management_edit_product_fail_case_test3() {
        System.out.println("""
                - Check that the user is logged in as store owner.
                - User inserting an existing product details & updated details to that product.
                    -> the new product details doesn't match the system required details for product.
                - Show fail message...
                """);
        fail();
    }
    @Test
    void store_management_edit_product_fail_case_test4() {
        System.out.println("""
                - Check that the user is logged in as store owner.
                - User inserting existing product details & updated details to that product.
                - Editing product in store activated.
                - Check that the product is now updated (will be FALSE).
                """);
        fail();
    }

    /**
     *  User requirement - II.4.2
     **/
    @Test
    void change_store_policy_success_case_test() {
        System.out.println("""
                - Check that the user is logged in as store owner.
                - User inserting the new policy details.
                - Changing store policy is activated.
                - Check that the store policy is now updated (will be true).
                """);
        fail();
    }
    @Test
    void change_store_policy_fail_case_test1() {
        System.out.println("""
                - Check that the user is logged in as store owner.
                    -> User isn't logged in as a store owner.
                - Show fail message...
                """);
        fail();
    }
    @Test
    void change_store_policy_fail_case_test2() {
        System.out.println("""
                - Check that the user is logged in as store owner.
                - User inserting the new policy details.
                    -> details doesn't match to a policy in the system
                - Show fail message...
                """);
        fail();
    }
    @Test
    void change_store_policy_fail_case_test3() {
        System.out.println("""
                - Check that the user is logged in as store owner.
                - User inserting the new policy details.
                - Changing store policy is activated.
                - Check that the store policy is now updated (will be FALSE).
                """);
        fail();
    }

    /**
     *  User requirement - II.4.4
     **/
    @Test
    void adding_store_owner_success_case_test() {
        System.out.println("""
                - Check that the user (adding the new store owner) is logged in as store owner.
                - User inserting the new store owner details.
                - Check that the new owner is registered to the system and not already a store owner.
                - Adding store owner to the store is activated.
                - Check that the new owner has been added (will be true).
                """);
        fail();
    }
    @Test
    void adding_store_owner_fail_case_test1() {
        System.out.println("""
                - Check that the user (adding the new store owner) is logged in as store owner.
                    -> the user isn't logged in as a store owner.
                - Show fail message...
                """);
        fail();
    }
    @Test
    void adding_store_owner_fail_case_test2() {
        System.out.println("""
                - Check that the user (adding the new store owner) is logged in as store owner.
                - User inserting the new store owner details.
                - Check that the new owner is registered to the system and not already a store owner
                    -> the new owner inserted isn't a user in the system
                - Show fail message...
                """);
        fail();
    }
    @Test
    void adding_store_owner_fail_case_test3() {
        System.out.println("""
                - Check that the user (adding the new store owner) is logged in as store owner.
                - User inserting the new store owner details.
                - Check that the new owner is registered to the system and not already a store owner
                    -> the new owner inserted is already a store owner
                - Show fail message...
                """);
        fail();
    }
    @Test
    void adding_store_owner_success_case_test4() {
        System.out.println("""
                - Check that the user (adding the new store owner) is logged in as store owner.
                - User inserting the new store owner details.
                - Check that the new owner is registered to the system and not already a store owner.
                - Adding store owner to the store is activated.
                - Check that the new owner has been added (will be FALSE).
                """);
        fail();
    }

    /**
     *  User requirement - II.4.6
     **/
    @Test
    void adding_store_manager_success_case_test() {
        System.out.println("""
                - Check that the user (adding the new store manager) is logged in as store owner.
                - User inserting the new store manager details.
                - Check that the new owner is registered to the system and not already a store owner or store manager.
                - Adding store manager to the store is activated.
                - Check that the new manager has been added (will be true).
                """);
        fail();
    }
    @Test
    void adding_store_manager_fail_case_test1() {
        System.out.println("""
                - Check that the user (adding the new store owner) is logged in as store owner.
                    -> the user isn't logged in as a store owner.
                - Show fail message...
                """);
        fail();
    }
    @Test
    void adding_store_manager_fail_case_test2() {
        System.out.println("""
                - Check that the user (adding the new store manager) is logged in as store owner.
                - User inserting the new store manager details.
                - Check that the new owner is registered to the system and not already a store owner or store manager
                    -> the new manager inserted isn't a user in the system
                - Show fail message...
                """);
        fail();
    }
    @Test
    void adding_store_manager_fail_case_test3() {
        System.out.println("""
                - Check that the user (adding the new store manager) is logged in as store owner.
                - User inserting the new store manager details.
                - Check that the new manager is registered to the system and not already a store owner or store manager
                    -> the new owner inserted is already a store owner or store manager
                - Show fail message...
                """);
        fail();
    }
    @Test
    void adding_store_manager_success_case_test4() {
        System.out.println("""
                - Check that the user (adding the new store manager) is logged in as store owner.
                - User inserting the new store manager details.
                - Check that the new manager is registered to the system and not already a store owner or store manager.
                - Adding store manager to the store is activated.
                - Check that the new manager has been added (will be FALSE).
                """);
        fail();
    }

    /**
     *  User requirement - II.4.7
     **/
    @Test
    void change_store_manager_permissions_success_case_test() {
        System.out.println("""
                - Check that the user (changing the store manager permissions) is logged in as store owner.
                - User changing the store manager permissions.
                - Check that the manager's permissions has been updated (will be true).
                """);
        fail();
    }
    @Test
    void change_store_manager_permissions_fail_case_test1() {
        System.out.println("""
                - Check that the user (changing the store manager permissions) is logged in as store owner.
                    -> the user isn't logged in as a store owner.
                - Show fail message...
                """);
        fail();
    }
    @Test
    void change_store_manager_permissions_fail_case_test2() {
        System.out.println("""
                - Check that the user (changing the store manager permissions) is logged in as store owner.
                - User changing the store manager permissions.
                - Check that the manager's permissions has been updated (will be FALSE).
                """);
        fail();
    }
    /**
     *  User requirement - II.4.8
     **/
    @Test
    void close_store_success_case_test() {
        System.out.println("""
                - Check that the user (changing the store manager permissions) is logged in as store owner.
                - User changing the store manager permissions.
                - Check that the manager's permissions has been updated (will be true).
                """);
        fail();
    }


//endregion User requirements

//endregion Functional Requirements

}