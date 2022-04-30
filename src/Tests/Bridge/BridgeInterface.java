package Tests.Bridge;

import ExternalConnections.Delivery.Delivery;
import ExternalConnections.Delivery.DeliveryNames;
import ExternalConnections.Payment.Payment;
import ExternalConnections.Payment.PaymentNames;
import History.PurchaseHistory;
import Store.Product;
import StorePermission.Permission;

import javax.naming.NoPermissionException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface BridgeInterface {

    /** requirement 1.b in V1 */
    String parallelUse() throws ExecutionException, InterruptedException; //Need to implement thread-base system

    /** requirement 1.c in V1 */
    String systemLogging() throws ExecutionException, InterruptedException; //Need to create log file (containing error logs)

    /** System requirement - I.1 */
    String openingMarket() throws NoPermissionException;

    /** System requirement - I.2 -> Headers*/
    String changeExternalService(int currServiceCode, String currServiceName,
                                 int newServiceCode, String newServiceName);
    String switchExternalService(int currServiceCode, String currServiceName,
                                 int newServiceCode, String newServiceName);
    String addExternalService(int serviceCode, String serviceName);

    /** System requirement - I.2 -> Method to implement the Headers (above)*/
    boolean removePayment(PaymentNames paymentRemove);
    boolean AddPayment(Payment payment);
    boolean removeDelivery(DeliveryNames deliveryRemove);
    boolean addDelivery(Delivery delivery);

    /** System requirement - I.3 */
    int payment(PaymentNames payment, float total);

    /** System requirement - I.4 */
    int delivery(DeliveryNames delivery, float weight);

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
    List<Product> searchProduct(String userId, String productName);

    /** User requirement - II.2.3 */
    boolean saveProductFromStoreToShoppingCart(String user_id, String productID, String storeID,
                                               int amount, boolean auctionOrBid);

    /** User requirement - II.2.4 */
    String showShoppingCart(String userId);

    /** User requirement - II.2.4 */
    boolean increaseProductQuantityInShoppingCart(String user_id,String productID, String storeID,
                                                  int amount ,boolean auctionOrBid);

    /** User requirement - II.2.4 */
    boolean decreaseProductQuantityInShoppingCart(String userId,String productID, String storeID, int amount);

    /** User requirement - II.2.4 */
    String removeProductFromShoppingCart(String productName);

    /** User requirement - II.2.5 */
    boolean purchaseShoppingCart(String userID,PaymentNames payment,DeliveryNames delivery);

    /** User requirement - II.3.1 */
    boolean logout(String userid);

    /** User requirement - II.3.2 */
    String openStore(String userID, String storeName);

    /** User requirement - II.4.1 */
    boolean addProductToStore(String storeId, String userId, String productName, float price,
                              int supply, String category);

    /** User requirement - II.4.1 */
    boolean removeProductFromStore(String storeId, String userId,String productId);

    /** User requirement - II.4.1 */
    boolean editProductInStore(String storeId, String userId, String productId,
                               int newSupply, String newName, float newPrice, String category);

    /** User requirement - II.4.2 */
    String changeStorePolicy(String storeName, String newStorePolicy);

    /** User requirement - II.4.4 */
    boolean addNewStoreOwner(String storeId, String userIdGiving, String UserGettingPermissionId,
                             List<Permission> permissions);

    /** User requirement - II.4.6 */
    boolean addNewStoreManager(String storeId, String userIdGiving, String UserGettingPermissionId);

    /** User requirement - II.4.7 */
    String changeStoreManagerPermissions(String storeName, String storeManagerUserName, String newPermission);

    /** User requirement - II.4.9 */
    boolean freezeStoreByOwner(String storeId, String userId);

    /** User requirement - II.4.10 */
    boolean unfreezeStoreByOwner(String storeId, String userId);

    /** User requirement - II.4.11 */
    boolean showStoreOfficials(String storeId, String userId);

    /** User requirement - II.4.13 */
    List<PurchaseHistory> showStorePurchaseHistory(String storeId);

    /** User requirement - II.4.13 */
    List<PurchaseHistory> showUserPurchaseHistory(String userID);

    /** User requirement - II.6.4 */
    String showPurchaseHistoryForSystemFounder(String storeOrUser, String name);
}