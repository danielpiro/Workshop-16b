package com.company;

import java.util.ArrayList;
import java.util.HashMap;

public class Proxy implements BridgeInterface {
    private BridgeInterface real;

    ArrayList<User> users;
    ArrayList<Store> stores;

    public Proxy() {
        this.real = null;
        users = new ArrayList<>();
        stores = new ArrayList<>();
    }
    public Proxy(BridgeInterface real) {
        this.real = real;
        users = new ArrayList<>();
        stores = new ArrayList<>();
    }

    public BridgeInterface getReal() {
        return real;
    }

    public void setReal(BridgeInterface real) {
        this.real = real;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

    public void setStores(ArrayList<Store> stores) {
        this.stores = stores;
    }

    public void uploadUsersAndStores(){
        users.add(new User("user1", "11111", User.permission.SystemFounder));
        users.add(new User("user2", "22222", User.permission.SystemManager));
        users.add(new User("user3", "33333", User.permission.ShopOwner));
        users.add(new User("user4", "44444", User.permission.ShopManager));
        users.add(new User("user5", "55555", User.permission.Registered));
        users.add(new User("user6", "66666", User.permission.Buyer));
        users.add(new User("user7", "77777", User.permission.Visitor));

        Product p1 = new Product("p1", 1);
        Product p2 = new Product("p2", 2);
        Product p3 = new Product("p3", 3);
        HashMap<Product, Integer> products = new HashMap<>();
        products.put(p1, 5); products.put(p2, 15); products.put(p3, 25);
        stores.add(new Store("store1", products));
    }

    public void uploadUsersAndStores_FailTest1(){
        users.add(new User("user1", "11111", User.permission.SystemManager));
        users.add(new User("user2", "22222", User.permission.SystemManager));
        users.add(new User("user3", "33333", User.permission.ShopOwner));
        users.add(new User("user4", "44444", User.permission.ShopManager));
        users.add(new User("user5", "55555", User.permission.Registered));
        users.add(new User("user6", "66666", User.permission.Buyer));
        users.add(new User("user7", "77777", User.permission.Visitor));

        Product p1 = new Product("p1", 1);
        Product p2 = new Product("p2", 2);
        Product p3 = new Product("p3", 3);
        HashMap<Product, Integer> products = new HashMap<>();
        products.put(p1, 5); products.put(p2, 15); products.put(p3, 25);
        stores.add(new Store("store1", products));
    }

    public void uploadUsersAndStores_FailTest2(){
        users.add(new User("user1", "11111", User.permission.SystemFounder));
        users.add(new User("user2", "22222", User.permission.SystemManager));
        users.add(new User("user3", "33333", User.permission.ShopOwner));
        users.add(new User("user4", "44444", User.permission.ShopManager));
        users.add(new User("user5", "55555", User.permission.Registered));
        users.add(new User("user6", "66666", User.permission.Buyer));

        Product p1 = new Product("p1", 1);
        Product p2 = new Product("p2", 2);
        Product p3 = new Product("p3", 3);
        HashMap<Product, Integer> products = new HashMap<>();
        products.put(p1, 5); products.put(p2, 15); products.put(p3, 25);
        stores.add(new Store("store1", products));
    }

    /** requirement 1.b in V1 */
    public String parallelUse() { //Need to implement thread-base system
        if(real!=null)
            return real.parallelUse();
        throw new UnsupportedOperationException("Not Implemented Yet - Ask Backend Group");
    }

    /** requirement 1.c in V1 */
    public String systemLogging() { //Need to create log file (containing error logs)
        if(real!=null)
            return real.parallelUse();
        throw new UnsupportedOperationException("Not Implemented Yet - Ask Backend Group");
    }

    /** System requirement - I.1 */
    public String openingMarket(){
        if(real!=null)
            return real.openingMarket();

        if(users.isEmpty() && stores.isEmpty()) // just for tests
            uploadUsersAndStores();

        boolean extServices = true; //TODO
        if(!extServices)
            return "fail - connection with external services has failed";

        if(users.size() == 7 && stores.size() == 1){
            for (User u : users){
                if(u.getPermissionLevel() == User.permission.SystemFounder){
                    return "system opened successfully";
                }
            }
            return "fail - system doesn't have a founder";
        }
        return "fail - system didn't upload all the users or stores";
    }

    /** System requirement - I.2 */
    public String changeExternalService(int currServiceCode, String currServiceName,
                                 int newServiceCode, String newServiceName){
        //Valid ServiceCode includes only positive numbers
        //Valid ServiceName includes only letters
        if(real!=null)
            return real.changeExternalService(currServiceCode, currServiceName, newServiceCode, newServiceName);

        if(newServiceCode <= 0 || !newServiceName.matches("[a-zA-Z]+"))
            return "fail - invalid service code or name";
        else { //TODO: Make a proper service change
            currServiceCode = newServiceCode;
            currServiceName = newServiceName;
            return "change services done successfully";
        }
    }

    /** System requirement - I.2 */
    public String switchExternalService(int currServiceCode, String currServiceName,
                                 int newServiceCode, String newServiceName){
        //Valid ServiceCode includes only positive numbers
        //Valid ServiceName includes only letters
        if(real!=null)
            return real.switchExternalService(currServiceCode, currServiceName, newServiceCode, newServiceName);

        if(newServiceCode <= 0 || !newServiceName.matches("[a-zA-Z]+"))
            return "fail - invalid service code or name";
        else { //TODO: Make a proper service switch
            currServiceCode = newServiceCode;
            currServiceName = newServiceName;
            return "switch services done successfully";
        }
    }

    /** System requirement - I.2 */
    public String addExternalService(int serviceCode, String serviceName){
        //Valid ServiceCode includes only positive numbers
        //Valid ServiceName includes only letters
        if(real!=null)
            return real.addExternalService(serviceCode, serviceName);

        if(serviceCode <= 0 || !serviceName.matches("[a-zA-Z]+"))
            return "fail - invalid service code or name";
        else //TODO: Make a proper service adding
            return "adding services done successfully";
    }

    /** System requirement - I.3 */
    public String payment(){
        if(real!=null)
            return real.payment();

        //TODO: Make sure there is a payment service in the system
        return "Payment done successfully";
    }

    /** System requirement - I.4 */
    public String delivery(){
        if(real!=null)
            return real.delivery();

        //TODO: Make sure there is a delivery service in the system
        return "Delivery done successfully";
    }

    /** System requirement - I.5 */
    public String realtimeNotificationProductBought(){
        if(real!=null)
            return real.realtimeNotificationProductBought();

        //TODO: Make sure there is a realtime notifications in the system
        return "TODO!";
    }

    /** System requirement - I.5 */
    public String realtimeNotificationStoreClosed(){
        if(real!=null)
            return real.realtimeNotificationStoreClosed();

        //TODO: Make sure there is a realtime notifications in the system
        return "TODO!";
    }

    /** System requirement - I.5 */
    public String realtimeNotificationStoreReopened(){
        if(real!=null)
            return real.realtimeNotificationStoreReopened();

        //TODO: Make sure there is a realtime notifications in the system
        return "TODO!";
    }

    /** System requirement - I.5 */
    public String realtimeNotificationUserPermissionUpdate(){
        if(real!=null)
            return real.realtimeNotificationUserPermissionUpdate();

        //TODO: Make sure there is a realtime notifications in the system
        return "TODO!";
    }

    /** System requirement - I.6 */
    public String offlineNotificationProductBought(){
//        if(real!=null)
//            return real.offlineNotificationProductBought();
        return "TODO!";
    }

    /** System requirement - I.6 */
    public String offlineNotificationStoreClosed(){
//        if(real!=null)
//            return real.offlineNotificationStoreClosed();
        return "TODO!";
    }

    /** System requirement - I.6 */
    public String offlineNotificationStoreReopened(){
//        if(real!=null)
//            return real.offlineNotificationStoreReopened();
        return "TODO!";
    }

    /** System requirement - I.6 */
    public String offlineNotificationUserPermissionUpdate(){
//        if(real!=null)
//            return real.offlineNotificationUserPermissionUpdate();
        return "TODO!";
    }

    /** User requirement - II.1.1 */
    public String getInToTheSystem(){
//        if(real!=null)
//            return real.getInToTheSystem();
        return "TODO!";
    }

    /** User requirement - II.1.2 */
    public String getOutFromTheSystem(){
//        if(real!=null)
//            return real.getOutFromTheSystem();
        return "TODO!";
    }

    /** User requirement - II.1.3 */
    public String register(String username, String password){
//        if(real!=null)
//            return real.register();
        return "TODO!";
    }

    /** User requirement - II.1.4 */
    public String login(String username, String password){
//        if(real!=null)
//            return real.login();
        return "TODO!";
    }

    /** User requirement - II.2.1 */
    public String receiveSystemInfo(){
//        if(real!=null)
//            return real.receiveSystemInfo();
        return "TODO!";
    }

    /** User requirement - II.2.2 */
    public String searchProduct(String productName){
//        if(real!=null)
//            return real.searchProduct();
        return "TODO!";
    }

    /** User requirement - II.2.3 */
    public String saveProductFromStoreToShoppingCart(){
//        if(real!=null)
//            return real.saveProductFromStoreToShoppingCart();
        return "TODO!";
    }

    /** User requirement - II.2.4 */
    public String showShoppingCart(){
//        if(real!=null)
//            return real.showShoppingCart();
        return "TODO!";
    }
    /** User requirement - II.2.4 */
    //inc. by 1!
    public String increaseProductQuantityInShoppingCart(String productName){
//        if(real!=null)
//            return real.increaseProductQuantityInShoppingCart();
        return "TODO!";
    }
    /** User requirement - II.2.4 */
    //dec. by 1!
    public String decreaseProductQuantityInShoppingCart(String productName){
//        if(real!=null)
//            return real.decreaseProductQuantityInShoppingCart();
        return "TODO!";
    }
    /** User requirement - II.2.4 */
    public String removeProductFromShoppingCart(String productName){
//        if(real!=null)
//            return real.removeProductFromShoppingCart();
        return "TODO!";
    }

    /** User requirement - II.2.5 */
    public String purchaseShoppingCart(){
//        if(real!=null)
//            return real.purchaseShoppingCart();
        return "TODO!";
    }

    /** User requirement - II.3.1 */
    public String logout(){
//        if(real!=null)
//            return real.logout();
        return "TODO!";
    }

    /** User requirement - II.3.2 */
    public String openStore(String storeName, ArrayList<String> product){
//        if(real!=null)
//            return real.openStore();
        return "TODO!";
    }

    /** User requirement - II.4.1 */
    public String addProductToStore(String productName, int productQuantity){
//        if(real!=null)
//            return real.addProductToStore();
        return "TODO!";
    }
    /** User requirement - II.4.1 */
    public String removeProductFromStore(String productName){
//        if(real!=null)
//            return real.removeProductFromStore();
        return "TODO!";
    }
    /** User requirement - II.4.1 */
    public String editProductInStore(String productName, String newProductName, int newProductQuantity){
//        if(real!=null)
//            return real.editProductInStore();
        return "TODO!";
    }

    /** User requirement - II.4.2 */
    public String changeStorePolicy(String newStorePolicy){
//        if(real!=null)
//            return real.changeStorePolicy();
        return "TODO!";
    }

    /** User requirement - II.4.4 */
    public String addNewStoreOwner(String newStoreOwnerUserName){
//        if(real!=null)
//            return real.addNewStoreOwner();
        return "TODO!";
    }

    /** User requirement - II.4.6 */
    public String addNewStoreManager(String newStoreManagerUserName){
//        if(real!=null)
//            return real.addNewStoreManager();
        return "TODO!";
    }

    /** User requirement - II.4.7 */
    public String changeStoreManagerPermissions(String storeManagerUserName, User.permission newPermission){
//        if(real!=null)
//            return real.changeStoreManagerPermissions();
        return "TODO!";
    }

    /** User requirement - II.4.9 */
    public String closeStoreOwner(String storeName){
//        if(real!=null)
//            return real.closeStoreOwner();
        return "TODO!";
    }

    /** User requirement - II.4.11 */
    public String showStoreOfficials(String storeName){
//        if(real!=null)
//            return real.showStoreOfficials();
        return "TODO!";
    }

    /** User requirement - II.4.13 */
    public String showStorePurchaseHistory(String storeName){
//        if(real!=null)
//            return real.showStorePurchaseHistory();
        return "TODO!";
    }
}
