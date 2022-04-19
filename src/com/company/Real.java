package com.company;

import java.util.ArrayList;

public class Real implements BridgeInterface{

    private MarketSystem msApp;

    public Real() {
        this.msApp = null;
    }
    public Real(MarketSystem msApp) {
        this.msApp = msApp;
    }

    public MarketSystem getMsApp() {
        return msApp;
    }

    public void setMsApp(MarketSystem msApp) {
        this.msApp = msApp;
    }

    /** requirement 1.b in V1 */
    public String parallelUse() { //Need to implement thread-base system
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** requirement 1.c in V1 */
    public String systemLogging() { //Need to create log file (containing error logs)
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** System requirement - I.1 */
    public String openingMarket(){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** System requirement - I.2 */
    public String changeExternalService(int currServiceCode, String currServiceName,
                                        int newServiceCode, String newServiceName){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** System requirement - I.2 */
    public String switchExternalService(int currServiceCode, String currServiceName,
                                        int newServiceCode, String newServiceName){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** System requirement - I.2 */
    public String addExternalService(int serviceCode, String serviceName){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** System requirement - I.3 */
    public String payment(){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** System requirement - I.4 */
    public String delivery(){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** System requirement - I.5 */
    public String realtimeNotificationProductBought(){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** System requirement - I.5 */
    public String realtimeNotificationStoreClosed(){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** System requirement - I.5 */
    public String realtimeNotificationStoreReopened(){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** System requirement - I.5 */
    public String realtimeNotificationUserPermissionUpdate(){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** System requirement - I.6 */
    public String offlineNotificationProductBought(){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** System requirement - I.6 */
    public String offlineNotificationStoreClosed(){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** System requirement - I.6 */
    public String offlineNotificationStoreReopened(){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** System requirement - I.6 */
    public String offlineNotificationUserPermissionUpdate(){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** User requirement - II.1.1 */
    public String getInToTheSystem(){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** User requirement - II.1.2 */
    public String getOutFromTheSystem(){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** User requirement - II.1.3 */
    public String register(String username, String password){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** User requirement - II.1.4 */
    public String login(String username, String password){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** User requirement - II.2.1 */
    public String receiveSystemInfo(){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** User requirement - II.2.2 */
    public String searchProduct(String productName){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** User requirement - II.2.3 */
    public String saveProductFromStoreToShoppingCart(){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** User requirement - II.2.4 */
    public String showShoppingCart(){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }
    /** User requirement - II.2.4 */
    //inc. by 1!
    public String increaseProductQuantityInShoppingCart(String productName){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }
    /** User requirement - II.2.4 */
    //dec. by 1!
    public String decreaseProductQuantityInShoppingCart(String productName){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }
    /** User requirement - II.2.4 */
    public String removeProductFromShoppingCart(String productName){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** User requirement - II.2.5 */
    public String purchaseShoppingCart(){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** User requirement - II.3.1 */
    public String logout(){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** User requirement - II.3.2 */
    public String openStore(String storeName, ArrayList<String> product){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** User requirement - II.4.1 */
    public String addProductToStore(String productName, int productQuantity){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }
    /** User requirement - II.4.1 */
    public String removeProductFromStore(String productName){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }
    /** User requirement - II.4.1 */
    public String editProductInStore(String productName, String newProductName, int newProductQuantity){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** User requirement - II.4.2 */
    public String changeStorePolicy(String newStorePolicy){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** User requirement - II.4.4 */
    public String addNewStoreOwner(String newStoreOwnerUserName){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** User requirement - II.4.6 */
    public String addNewStoreManager(String newStoreManagerUserName){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** User requirement - II.4.7 */
    public String changeStoreManagerPermissions(String storeManagerUserName, User.permission newPermission){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** User requirement - II.4.9 */
    public String closeStoreOwner(String storeName){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** User requirement - II.4.11 */
    public String showStoreOfficials(String storeName){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }

    /** User requirement - II.4.13 */
    public String showStorePurchaseHistory(String storeName){
        throw new UnsupportedOperationException("Not Implemented Yet");
    }
}
