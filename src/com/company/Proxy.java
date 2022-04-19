package com.company;

import java.util.ArrayList;

public class Proxy implements BridgeInterface {
    private BridgeInterface real;

    public Proxy() {
        this.real = null;
    }
    public Proxy(BridgeInterface real) {
        this.real = real;
    }

    public void setReal(BridgeInterface real) {
        this.real = real;
    }

    /** requirement 1.b in V1 */
    public String parallelUse() { //Need to implement thread-base system
        if(real!=null)
            return real.parallelUse();
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** requirement 1.c in V1 */
    public String systemLogging() { //Need to create log file (containing error logs)
        if(real!=null)
            return real.parallelUse();
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** System requirement - I.1 */
    public String openingMarket(){
//        if(real!=null)
//            return real.openingMarket();
        return "TODO!";
    }

    /** System requirement - I.2 */
    public String changeExternalService(int currServiceCode, String currServiceName,
                                 int newServiceCode, String newServiceName){
//        if(real!=null)
//            return real.changeExternalService();
        return "TODO!";
    }

    /** System requirement - I.2 */
    public String switchExternalService(int currServiceCode, String currServiceName,
                                 int newServiceCode, String newServiceName){
//        if(real!=null)
//            return real.switchExternalService();
        return "TODO!";
    }

    /** System requirement - I.2 */
    public String addExternalService(int serviceCode, String serviceName){
//        if(real!=null)
//            return real.addExternalService();
        return "TODO!";
    }

    /** System requirement - I.3 */
    public String payment(){
//        if(real!=null)
//            return real.payment();
        return "TODO!";
    }

    /** System requirement - I.4 */
    public String delivery(){
//        if(real!=null)
//            return real.delivery();
        return "TODO!";
    }

    /** System requirement - I.5 */
    public String realtimeNotificationProductBought(){
//        if(real!=null)
//            return real.realtimeNotificationProductBought();
        return "TODO!";
    }

    /** System requirement - I.5 */
    public String realtimeNotificationStoreClosed(){
//        if(real!=null)
//            return real.realtimeNotificationStoreClosed();
        return "TODO!";
    }

    /** System requirement - I.5 */
    public String realtimeNotificationStoreReopened(){
//        if(real!=null)
//            return real.realtimeNotificationStoreReopened();
        return "TODO!";
    }

    /** System requirement - I.5 */
    public String realtimeNotificationUserPermissionUpdate(){
//        if(real!=null)
//            return real.realtimeNotificationUserPermissionUpdate();
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
