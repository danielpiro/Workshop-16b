package com.company;

import java.util.ArrayList;

public interface BridgeInterface {
    /** requirement 1.b in V1 */
    String parallelUse(); //Need to implement thread-base system

    /** requirement 1.c in V1 */
    String systemLogging(); //Need to create log file (containing error logs)

    /** System requirement - I.1 */
    String openingMarket();

    /** System requirement - I.2 */
    String changeExternalService(int currServiceCode, String currServiceName,
                                 int newServiceCode, String newServiceName);
    /** System requirement - I.2 */
    String switchExternalService(int currServiceCode, String currServiceName,
                                 int newServiceCode, String newServiceName);
    /** System requirement - I.2 */
    String addExternalService(int serviceCode, String serviceName);

    /** System requirement - I.3 */
    String payment();

    /** System requirement - I.4 */
    String delivery();

    /** System requirement - I.5 */
    String realtimeNotificationProductBought();

    /** System requirement - I.5 */
    String realtimeNotificationStoreClosed();

    /** System requirement - I.5 */
    String realtimeNotificationStoreReopened();

    /** System requirement - I.5 */
    String realtimeNotificationUserPermissionUpdate();

    /** System requirement - I.6 */
    String offlineNotificationProductBought();

    /** System requirement - I.6 */
    String offlineNotificationStoreClosed();

    /** System requirement - I.6 */
    String offlineNotificationStoreReopened();

    /** System requirement - I.6 */
    String offlineNotificationUserPermissionUpdate();

    /** User requirement - II.1.1 */
    String getInToTheSystem();

    /** User requirement - II.1.2 */
    String getOutFromTheSystem();

    /** User requirement - II.1.3 */
    String register(String username, String password);

    /** User requirement - II.1.4 */
    String login(String username, String password);

    /** User requirement - II.2.1 */
    String receiveSystemInfo();

    /** User requirement - II.2.2 */
    String searchProduct(String productName);

    /** User requirement - II.2.3 */
    String saveProductFromStoreToShoppingCart();

    /** User requirement - II.2.4 */
    String showShoppingCart();
    /** User requirement - II.2.4 */
    String increaseProductQuantityInShoppingCart(String productName); //inc. by 1!
    /** User requirement - II.2.4 */
    String decreaseProductQuantityInShoppingCart(String productName); //dec. by 1!
    /** User requirement - II.2.4 */
    String removeProductFromShoppingCart(String productName);

    /** User requirement - II.2.5 */
    String purchaseShoppingCart();

    /** User requirement - II.3.1 */
    String logout();

    /** User requirement - II.3.2 */
    String openStore(String storeName, ArrayList<String> product);

    /** User requirement - II.4.1 */
    String addProductToStore(String productName, int productQuantity);
    /** User requirement - II.4.1 */
    String removeProductFromStore(String productName);
    /** User requirement - II.4.1 */
    String editProductInStore(String productName, String newProductName, int newProductQuantity);

    /** User requirement - II.4.2 */
    String changeStorePolicy(String newStorePolicy);

    /** User requirement - II.4.4 */
    String addNewStoreOwner(String newStoreOwnerUserName);

    /** User requirement - II.4.6 */
    String addNewStoreManager(String newStoreManagerUserName);

    /** User requirement - II.4.7 */
    String changeStoreManagerPermissions(String storeManagerUserName, User.permission newPermission);

    /** User requirement - II.4.9 */
    String closeStoreOwner(String storeName);

    /** User requirement - II.4.11 */
    String showStoreOfficials(String storeName);

    /** User requirement - II.4.13 */
    String showStorePurchaseHistory(String storeName);
}
