package com.example.demo.Tests.Bridge;



import com.example.demo.CustomExceptions.ExceptionHandler.ReturnValue;
import com.example.demo.ExternalConnections.Old.Delivery.Delivery;
import com.example.demo.ExternalConnections.Old.Delivery.DeliveryNames;
import com.example.demo.ExternalConnections.Old.Payment.Payment;
import com.example.demo.ExternalConnections.Old.Payment.PaymentNames;


import javax.naming.NoPermissionException;
import java.util.concurrent.ExecutionException;

public class Proxy  {
    private BridgeInterface real;

//    private ArrayList<Subscriber> users;
//    private ArrayList<Store> stores;
//    private Subscriber currentUserInSystem;

    public Proxy() {
        this.real = null;
//        users = new ArrayList<>();
//        stores = new ArrayList<>();
//        currentUserInSystem = null;
    }
    public Proxy(BridgeInterface real) {
        this.real = real;
//        users = new ArrayList<>();
//        stores = new ArrayList<>();
//        currentUserInSystem = null;
    }

    public BridgeInterface getReal() {
        return real;
    }

    public void setReal(BridgeInterface real) {
        this.real = real;
    }

//    public ArrayList<Subscriber> getUsers() {
//        return users;
//    }
//
//    public void setUsers(ArrayList<Subscriber> users) {
//        this.users = users;
//    }
//
//    public ArrayList<Store> getStores() {
//        return stores;
//    }
//
//    public void setStores(ArrayList<Store> stores) {
//        this.stores = stores;
//    }
//
//    public Subscriber getCurrentUserInSystem() {
//        return currentUserInSystem;
//    }
//
//    public void setCurrentUserInSystem(Subscriber currentUserInSystem) {
//        this.currentUserInSystem = currentUserInSystem;
//    }

//    public void uploadUsersAndStores() throws NoPermissionException {
//        Subscriber user1 = new Subscriber("user1", "11111");
//        Subscriber user2 = new Subscriber("user2", "22222");
//        Subscriber user3 = new Subscriber("user3", "33333");
//        Subscriber user4 = new Subscriber("user4", "44444");
//        Subscriber user5 = new Subscriber("user5", "55555");
//        Subscriber user6 = new Subscriber("user6", "66666");
//        Subscriber user7 = new Subscriber("user7", "77777");
//        users.add(user1);
//        users.add(user2);
//        users.add(user3);
//        users.add(user4);
//        users.add(user5);
//        users.add(user6);
//        users.add(user7);
//
//        List<String> officials = new ArrayList<>();
//        officials.add(user1.getName());
//        officials.add(user2.getName());
//        officials.add(user3.getName());
//        officials.add(user4.getName());
//        Store store1 = new Store("store1", IdGenerator.getInstance().getStoreId(), officials);
//        stores.add(store1);
//        store1.addNewProduct(user1.getName(), "p0", 5.0f, 10, "Other");
//        store1.addNewProduct(user1.getName(), "p1", 15.0f, 11, "Other");
//        store1.addNewProduct(user1.getName(), "p2", 25.0f, 12, "Other");
//        store1.addNewProduct(user1.getName(), "p3", 35.0f, 13, "Other");
//    }

    /** requirement 1.b in V1 */
    public String parallelUse() throws ExecutionException, InterruptedException { //Need to implement thread-base system
        if (real != null)
            return real.parallelUse();
        throw new UnsupportedOperationException("Not Implemented Yet!");
    }

    /** requirement 1.c in V1 */
    public String systemLogging() throws ExecutionException, InterruptedException { //Need to create log file (containing error logs)
        if (real != null)
            return real.parallelUse();
        throw new UnsupportedOperationException("Not Implemented Yet - Ask Backend Group");
    }

    /** System requirement - I.1 */
    public String openingMarket() throws NoPermissionException {
        if (real != null)
            return real.openingMarket();
        throw new UnsupportedOperationException("Not Implemented Yet!");
    }

    /** System requirement - I.2 */
    public String changeExternalService(int currServiceCode, String currServiceName,
                                        int newServiceCode, String newServiceName) {
        //Valid ServiceCode includes only positive numbers
        //Valid ServiceName includes only letters
        if (real != null)
            return real.changeExternalService(currServiceCode, currServiceName, newServiceCode, newServiceName);
        throw new UnsupportedOperationException("Not Implemented Yet!");
    }

    /** System requirement - I.2 */
    public String switchExternalService(int currServiceCode, String currServiceName,
                                        int newServiceCode, String newServiceName) {
        //Valid ServiceCode includes only positive numbers
        //Valid ServiceName includes only letters
        if (real != null)
            return real.switchExternalService(currServiceCode, currServiceName, newServiceCode, newServiceName);
        throw new UnsupportedOperationException("Not Implemented Yet!");
    }

    /** System requirement - I.2 */
    public String addExternalService(int serviceCode, String serviceName){
        if(real!=null)
            return real.addExternalService(serviceCode, serviceName);
        throw new UnsupportedOperationException("Not Implemented Yet!");
    }

    /** System requirement - I.2 */
    public boolean removePayment(PaymentNames paymentRemove){
        if(real!=null)
            return real.removePayment(paymentRemove);
        throw new UnsupportedOperationException("Not Implemented Yet!");
    }

    /** System requirement - I.2 */
    public boolean AddPayment(Payment payment){
        if(real!=null)
            return real.AddPayment(payment);
        throw new UnsupportedOperationException("Not Implemented Yet!");
    }

    /** System requirement - I.2 */
    public boolean removeDelivery(DeliveryNames deliveryRemove){
        if(real!=null)
            return real.removeDelivery(deliveryRemove);
        throw new UnsupportedOperationException("Not Implemented Yet!");
    }

    /** System requirement - I.2 */
    public boolean addDelivery(Delivery delivery){
        if(real!=null)
            return real.addDelivery(delivery);
        throw new UnsupportedOperationException("Not Implemented Yet!");
    }

    /** System requirement - I.3 */
    public int payment(PaymentNames payment, float total) {
        if (real != null)
            return real.payment(payment, total);
        throw new UnsupportedOperationException("Not Implemented Yet!");
    }

    /** System requirement - I.4 */
    public int delivery(DeliveryNames delivery, float weight) {
        if (real != null)
            return real.delivery(delivery, weight);
        throw new UnsupportedOperationException("Not Implemented Yet!");
    }

    /** System requirement - I.5 */
    public String realtimeNotificationProductBought() {
        if (real != null)
            return real.realtimeNotificationProductBought();

        //TODO: Make sure there is a realtime notifications in the system
        throw new UnsupportedOperationException("Not Implemented Yet!");
    }

    /** System requirement - I.5 */
    public String realtimeNotificationStoreClosed(){
        if(real!=null)
            return real.realtimeNotificationStoreClosed();

        //TODO: Make sure there is a realtime notifications in the system
        throw new UnsupportedOperationException("Not Implemented Yet!");
    }

    /** System requirement - I.5 */
    public String realtimeNotificationStoreReopened() {
        if (real != null)
            return real.realtimeNotificationStoreReopened();

        //TODO: Make sure there is a realtime notifications in the system
        throw new UnsupportedOperationException("Not Implemented Yet!");
    }


    /** System requirement - I.5 */
    public String realtimeNotificationUserPermissionUpdate() {
        if (real != null)
            return real.realtimeNotificationUserPermissionUpdate();

        //TODO: Make sure there is a realtime notifications in the system
        throw new UnsupportedOperationException("Not Implemented Yet!");
    }

    /** System requirement - I.6 */
    public String offlineNotificationProductBought() {
        if (real != null)
            return real.offlineNotificationProductBought();

        //TODO: Make sure there is a offline notifications in the system
        throw new UnsupportedOperationException("Not Implemented Yet!");
    }

    /** System requirement - I.6 */
    public String offlineNotificationStoreClosed() {
        if (real != null)
            return real.offlineNotificationStoreClosed();

        //TODO: Make sure there is a offline notifications in the system
        throw new UnsupportedOperationException("Not Implemented Yet!");
    }

    /** System requirement - I.6 */
    public String offlineNotificationStoreReopened() {
        if (real != null)
            return real.offlineNotificationStoreReopened();

        //TODO: Make sure there is a offline notifications in the system
        throw new UnsupportedOperationException("Not Implemented Yet!");
    }

    /** System requirement - I.6 */
    public String offlineNotificationUserPermissionUpdate() {
        if (real != null)
            return real.offlineNotificationUserPermissionUpdate();

        //TODO: Make sure there is a offline notifications in the system
        throw new UnsupportedOperationException("Not Implemented Yet!");
    }

    /** User requirement - II.1.1 */
    public String getInToTheSystem() {
        if (real != null)
            return real.getInToTheSystem();
        throw new UnsupportedOperationException("Not Implemented Yet!");
    }

    /** User requirement - II.1.2 */
    public String getOutFromTheSystem(String name){
        if(real!=null)
            return real.getOutFromTheSystem(name);
        throw new UnsupportedOperationException("Not Implemented Yet!");
    }

    /** User requirement - II.1.3 */
    public boolean register(String guestId,String username, String password){
        if(real!=null)
            return real.register(guestId,username, password);
        throw new UnsupportedOperationException("Not Implemented Yet!");
    }


    /** User requirement - II.1.4 */
    public boolean login(String username, String password){
        if(real!=null)
            return real.login(username, password);
        throw new UnsupportedOperationException("Not Implemented Yet!");
    }

    /** User requirement - II.2.1 */
    public String receiveSystemInfo(){
        if(real!=null)
            return real.receiveSystemInfo();
        throw new UnsupportedOperationException("Not Implemented Yet!");
    }

    /** User requirement - II.2.2 */
    public ReturnValue searchProduct(String userId, String productName){
        if(real!=null)
            return real.searchProduct(userId, productName);
        throw new UnsupportedOperationException("Not Implemented Yet!");
    }
//
//    /** User requirement - II.2.3 */
//    public boolean saveProductFromStoreToShoppingCart(String user_id, String productID, String storeID,
//                                                     int amount, boolean auctionOrBid){
//        if(real!=null)
//            return real.saveProductFromStoreToShoppingCart(user_id, productID, storeID, amount, auctionOrBid);
//        throw new UnsupportedOperationException("Not Implemented Yet!");
//    }
//
//    /** User requirement - II.2.4 */
//    public String showShoppingCart(String userId){
//        if(real!=null)
//            return real.showShoppingCart(userId);
//        throw new UnsupportedOperationException("Not Implemented Yet!");
//    }
//
//    /** User requirement - II.2.4 */
//    public boolean increaseProductQuantityInShoppingCart(String user_id,String productID, String storeID,
//                                                        int amount ,boolean auctionOrBid){
//        if(real!=null)
//            return real.increaseProductQuantityInShoppingCart(user_id, productID, storeID, amount, auctionOrBid);
//        throw new UnsupportedOperationException("Not Implemented Yet!");
//    }
//
//    /** User requirement - II.2.4 */
//    public boolean decreaseProductQuantityInShoppingCart(String userId,String productID, String storeID, int amount){
//        if(real!=null)
//            return real.decreaseProductQuantityInShoppingCart(userId, productID, storeID, amount);
//        throw new UnsupportedOperationException("Not Implemented Yet!");
//    }
//
//    /** User requirement - II.2.4 */
//    public String removeProductFromShoppingCart(String productName){
//        if(real!=null)
//            return real.removeProductFromShoppingCart(productName);
//        throw new UnsupportedOperationException("Not Implemented Yet!");
//    }
//
//    /** User requirement - II.2.5 */
//    public boolean purchaseShoppingCart(String userID,PaymentNames payment,DeliveryNames delivery){
//        if(real!=null)
//            return real.purchaseShoppingCart(userID, payment, delivery);
//
//        //TODO: Need to add external services...
//        throw new UnsupportedOperationException("Not Implemented Yet!");
//    }
//
//    /** User requirement - II.3.1 */
//    public boolean logout(String userid){
//        if(real!=null)
//            return real.logout(userid);
//        throw new UnsupportedOperationException("Not Implemented Yet!");
//    }
//
//    /** User requirement - II.3.2 */
//    public String openStore(String userID, String storeName){
//        if(real!=null)
//            return real.openStore(userID, storeName);
//        throw new UnsupportedOperationException("Not Implemented Yet!");
//    }
//
//    /** User requirement - II.4.1 */
//    public boolean addProductToStore(String storeId, String userId, String productName, float price,
//                                     int supply, String category){
//        if(real!=null)
//            return real.addProductToStore(storeId, userId, productName, price, supply, category);
//        throw new UnsupportedOperationException("Not Implemented Yet!");
//    }
//
//    /** User requirement - II.4.1 */
//    public boolean removeProductFromStore(String storeId, String userId,String productId){
//        if(real!=null)
//            return real.removeProductFromStore(storeId, userId, productId);
//        throw new UnsupportedOperationException("Not Implemented Yet!");
//    }
//
//    /** User requirement - II.4.1 */
//    public boolean editProductInStore(String storeId, String userId, String productId,
//                                     int newSupply, String newName, float newPrice, String category){
//        if(real!=null)
//            return real.editProductInStore(storeId, userId, productId, newSupply, newName, newPrice, category);
//        throw new UnsupportedOperationException("Not Implemented Yet!");
//    }
//
//    /** User requirement - II.4.2 */
//    public String changeStorePolicy(String storeName, String newStorePolicy){
//        if(real!=null)
//            return real.changeStorePolicy(storeName, newStorePolicy);
//        throw new UnsupportedOperationException("Not Implemented Yet!");
//    }
//
//    /** User requirement - II.4.4 */
//    public boolean addNewStoreOwner(String storeId, String userIdGiving, String UserGettingPermissionId,
//                                   List<Permission> permissions){
//        if(real!=null)
//            return real.addNewStoreOwner(storeId, userIdGiving, UserGettingPermissionId, permissions);
//        throw new UnsupportedOperationException("Not Implemented Yet!");
//    }
//
//    /** User requirement - II.4.6 */
//    public boolean addNewStoreManager(String storeId, String userIdGiving, String UserGettingPermissionId){
//        if(real!=null)
//            return real.addNewStoreManager(storeId, userIdGiving, UserGettingPermissionId);
//        throw new UnsupportedOperationException("Not Implemented Yet!");
//    }
//
//    /** User requirement - II.4.7 */
//    public boolean changeStoreManagerPermissions(String storeId, String userIdRemoving, String UserAffectedId, List<String> PerToRemove) throws NoPermissionException {
//        if(real!=null)
//            return real.changeStoreManagerPermissions(storeId, userIdRemoving, UserAffectedId, PerToRemove);
//        throw new UnsupportedOperationException("Not Implemented Yet!");
//    }
//
//    /** User requirement - II.4.9 */
//    public boolean freezeStoreByOwner(String storeId, String userId){
//        if(real!=null)
//            return real.freezeStoreByOwner(storeId, userId);
//        throw new UnsupportedOperationException("Not Implemented Yet!");
//    }
//
//    /** User requirement - II.4.10 */
//    public boolean unfreezeStoreByOwner(String storeId, String userId){
//        if(real!=null)
//            return real.unfreezeStoreByOwner(storeId, userId);
//        throw new UnsupportedOperationException("Not Implemented Yet!");
//    }
//
//    /** User requirement - II.4.11 */
//    public boolean showStoreOfficials(String storeId, String userId){
//        if(real!=null)
//            return real.showStoreOfficials(storeId, userId);
//        throw new UnsupportedOperationException("Not Implemented Yet!");
//    }
//
//    /** User requirement - II.4.13 */
//    public List<PurchaseHistory> showStorePurchaseHistory(String storeId){
//        if(real!=null)
//            return real.showStorePurchaseHistory(storeId);
//        throw new UnsupportedOperationException("Not Implemented Yet!");
//    }
//
//    /** User requirement - II.4.13 */
//    public List<PurchaseHistory> showUserPurchaseHistory(String userId){
//        if(real!=null)
//            return real.showUserPurchaseHistory(userId);
//        throw new UnsupportedOperationException("Not Implemented Yet!");
//    }
//
//    /** User requirement - II.6.4 */
//    public String showPurchaseHistoryForSystemFounder(String storeOrUser, String name){
//        if(real!=null)
//            return real.showPurchaseHistoryForSystemFounder(storeOrUser, name);
//        throw new UnsupportedOperationException("Not Implemented Yet!");
//    }
//
//    //Helper Methods
//    public HashMap<String,List<Product>> getAllProductsAndStores(String userId){
//        if(real!=null)
//            return real.getAllProductsAndStores(userId);
//        throw new UnsupportedOperationException("Not Implemented Yet!");
//    }
//
//    @Override
//    public List<Guest> getGuest_list() {
//        if(real!=null)
//            return real.getGuest_list();
//        throw new UnsupportedOperationException("Not Implemented Yet!");
//    }
}
