package Controllers;


import ShoppingCart.InventoryProtector;
import ExternalConnections.PurchasePolicies;
import ShoppingCart.ShoppingCart;
import Store.Store;
import StorePermission.Permission;
import StorePermission.StoreRoles;
import User.Guest;
import User.Subscriber;
import Views.ProductView;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class BigController {
    private StoreController sc;
    private UserController us;

    public BigController() throws IOException {
        this.us = new UserController();
        this.sc = new StoreController();
    }
    //// user controller

    public void sign_up(String user_name, String password) {
        getUserController().sign_up(user_name, password);
    }

    public void login(String user_name, String password) {
        try {
            getUserController().login(user_name, password);
        }catch (Exception e)
        {}
    }

    public void logout(String user_name) {
        getUserController().logout(user_name);
    }
    public void sendComplaint(String userId, String StoreName,String complaint ) {
        getUserController().sendComplaint(userId,StoreName,complaint);
    }

    public Guest getGuest(String id) {
        return getUserController().getGuest(id);
    }

    public Guest addGuest() {
        return getUserController().addGuest();
    }

    public Subscriber getSystemAdmin() {
        return getUserController().getSystemAdmin();
    }

    public void add_subscriber(Subscriber s) {
       getUserController().add_subscriber(s);
    }
    public List<Guest> getGuest_list() {
        return getUserController().getGuest_list();
    }

    public List<Subscriber> getUser_list() {
        return getUserController().getUser_list();
    }
    public void Add_Query(String user_name,String query) {
       getUserController().Add_Query(user_name,query);
    }
    public Subscriber get_subscriber(String user_name) {
      return getUserController().get_subscriber(user_name);
    }
    public ShoppingCart getShoppingCart(String user_Id){
        return getUserController().getShoppingCart(user_Id);
    }
    public boolean containsStore(String user_id,String storeID) {
        return getUserController().containsStore(user_id,storeID);
    }
    public int removeProduct(String user_id,String productID, String storeID, int amount) {
        return getUserController().removeProduct(user_id,productID,storeID,amount);
    }
    public int addProduct(String user_id,String productID, String storeID, int amount,boolean auctionOrBid) {
        return getUserController().addProduct(user_id,productID,storeID,amount,auctionOrBid);
    }
    public int addProduct(String user_id, String productID, String storeID, int amount, InventoryProtector inventoryProtector, boolean auctionOrBid) {
        return getUserController().addProduct(user_id,productID,storeID,amount,inventoryProtector,auctionOrBid);
    }
    public String getCartInventory(String user_id) {
        return getUserController().getCartInventory(user_id);
    }
    public float purchaseCart(String user_id, PurchasePolicies purchasePolicies) {
        return getUserController().purchaseCart(user_id,purchasePolicies);
    }
    public boolean recordPurchase (String user_id) {
        return getUserController().recordPurchase(user_id);
    }

    /// Store controller

    public void addNewProduct(String storeId, String userId, String productName, float price, int supply, String category) throws NoPermissionException {
        if(getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId))
        getStoreController().addNewProduct(storeId,userId,productName,price,supply,category);
        else
            throw new IllegalArgumentException("couldn't add new product because the given userId doesn't exist or is not logged in");
    }
    public void closeStore(String storeId, String userId) throws NoPermissionException {
        if(getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId))
            getStoreController().closeStore(storeId,userId);
        else
            throw new IllegalArgumentException("couldn't close store because the given userId doesn't exist or is not logged in");
    }
    public void openStore(String storeId, String userId) throws NoPermissionException {
        if(getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId))
            getStoreController().openStore(storeId,userId);
        else
            throw new IllegalArgumentException("couldn't open store because the given userId doesn't exist or is not logged in");
    }
    public List<StoreRoles> getInfoOnManagers(String storeId, String userId) throws NoPermissionException {
        if(!getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId))
            throw new IllegalArgumentException("User doesn't exist or is not logged in");
        getStoreController().getInfoOnManagers(storeId,userId);
        return Collections.EMPTY_LIST;
    }
    public void editProduct(String storeId, String userId, String productId, int newSupply, String newName, float newPrice, String category) throws NoPermissionException {
        if(getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId))
            getStoreController().editProduct(storeId,userId,productId,newSupply,newName,newPrice,category);
        else
            throw new IllegalArgumentException("couldn't edit product  because the given userId doesn't exist or is not logged in");
    }

    public void deleteProduct(String storeId, String userId, String productId) throws NoPermissionException {
        if(getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId))
            getStoreController().deleteProduct(storeId,userId,productId);
        else
            throw new IllegalArgumentException("couldn't delete product  because the given userId doesn't exist or is not logged in");
    }


    public void removePermissionTo(String storeId, String userIdRemoving,String UserAffectedId) throws NoPermissionException{
        if(getUserController().checkIfUserExists(userIdRemoving) && getUserController().checkIfUserExists(UserAffectedId)&&getUserController().checkIfUserIsLoggedIn(userIdRemoving))
            getStoreController().removePermissionTo(storeId,userIdRemoving,UserAffectedId);
        else
            throw new IllegalArgumentException("couldn't remove permission because the given userId doesn't exist or is not logged in");
    }

    public void givePermissionTo(String storeId, String userIdGiving,String UserGettingPermissionId,List<Permission> permissions) throws NoPermissionException {
        if(getUserController().checkIfUserExists(userIdGiving) && getUserController().checkIfUserExists(UserGettingPermissionId)&&getUserController().checkIfUserIsLoggedIn(userIdGiving)){
            getStoreController().givePermissionTo(storeId,userIdGiving,UserGettingPermissionId,permissions);
        }
        throw new IllegalArgumentException("couldn't give permission because the given userId doesn't exist or is not logged in");
    }




    public void addReviewToProduct(String storeId, String userId, String productId, String Title, String Body, float rating){
        if(!getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId))
            throw new IllegalArgumentException("User doesn't exist or is not logged in or is not logged in");
        getStoreController().addReviewToProduct(storeId,userId,productId,Title,Body,rating);
    }

    public String addNewThreadToForum(String storeId,String title, String userId){
        if(!getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId))
            throw new IllegalArgumentException("User doesn't exist or is not logged in or is not logged in");
        return getStoreController().addNewThreadToForum(storeId,title,userId);
    }

    public void postMessageToForum(String storeId, String threadId, String userId, String message) throws NoPermissionException {
        if(!getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId))
            throw new IllegalArgumentException("User doesn't exist or is not logged in or is not logged in");
        getStoreController().postMessageToForum(storeId,threadId,userId,message);
    }


    private HashMap<String,List<ProductView>> getAllProductsAndStores(String userId){
        if(!getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId))
            throw new IllegalArgumentException("User doesn't exist or is not logged in or is not logged in");
        getStoreController().getAllProductsAndStores();
        return (HashMap<String, List<ProductView>>) Collections.EMPTY_MAP;
    }

    public List<ProductView> SearchProductsAccordingName(String userId,String productName){

        if(!getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId))
            throw new IllegalArgumentException("User doesn't exist or is not logged in");
        getStoreController().SearchProductsAccordingName(productName);
        return Collections.EMPTY_LIST;
    }

    public List<ProductView> SearchProductsAccordingCategory(String userId,List<String> categories ){
        if(!getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId))
            throw new IllegalArgumentException("User doesn't exist or is not logged in");
        getStoreController().SearchProductsAccordingCategory(categories);
        return Collections.EMPTY_LIST;

    }
    public List<ProductView> SearchProductsAccordingPrice(String userId, float fromPrice, float toPrice ){
        if(!getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId))
            throw new IllegalArgumentException("User doesn't exist or is not logged in");
        getStoreController().SearchProductsAccordingPrice(fromPrice,toPrice);
        return Collections.EMPTY_LIST;

    }
    public List<ProductView> SearchProductsAccordingRating(String userId,float productRating){
        if(!getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId))
            throw new IllegalArgumentException("User doesn't exist or is not logged in");
        getStoreController().SearchProductsAccordingRating(productRating);
        return Collections.EMPTY_LIST;
    }




        public UserController getUserController() {
        return us;
    }

    public StoreController getStoreController() {
        return sc;
    }
}
