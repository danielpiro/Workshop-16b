package Tests.Bridge;

import Views.ProductView;

import java.util.HashMap;
import java.util.List;

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
    String getOutFromTheSystem(String name);

    /** User requirement - II.1.3 */
    boolean register(String username, String password);

    /** User requirement - II.1.4 */
    boolean login(String username, String password);

    /** User requirement - II.2.1 */
    String receiveSystemInfo();

    /** User requirement - II.2.2 */
    List<ProductView> searchProduct(String userId,String productName);

    /** User requirement - II.2.3 */
    String saveProductFromStoreToShoppingCart(String storeName, String productName);

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
    boolean logout(String userid);

    /** User requirement - II.3.2 */
    String openStore(String storeName, HashMap<Product, Integer> products);

    /** User requirement - II.4.1 */
    String addProductToStore(String storeName, String productName, int productPrice, int productQuantity);
    /** User requirement - II.4.1 */
    String removeProductFromStore(String storeName, String productName);
    /** User requirement - II.4.1 */
    String editProductInStore(String storeName, String productName, String newProductName,
                              int newProductPrice, int newProductQuantity);

    /** User requirement - II.4.2 */
    String changeStorePolicy(String storeName, String newStorePolicy);

    /** User requirement - II.4.4 */
    String addNewStoreOwner(String storeName, String newStoreOwnerUserName);

    /** User requirement - II.4.6 */
    String addNewStoreManager(String storeName, String newStoreManagerUserName);

    /** User requirement - II.4.7 */
    String changeStoreManagerPermissions(String storeName, String storeManagerUserName, User.permission newPermission);

    /** User requirement - II.4.9 */
    String closeStoreByOwner(String storeName);

    /** User requirement - II.4.11 */
    String showStoreOfficials(String storeName);

    /** User requirement - II.4.13 */
    List<PurchaseHistory> showStorePurchaseHistory(String storeName);

    /** User requirement - II.6.4 */
    String showPurchaseHistoryForSystemFounder(String storeOrUser, String name);
}
