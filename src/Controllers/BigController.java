package Controllers;


import ExternalConnections.Delivery.FedEx;
import ExternalConnections.Delivery.UPS;
import ExternalConnections.ExternalConnections;
import ExternalConnections.Payment.MasterCard;
import ExternalConnections.Payment.Visa;
import GlobalSystemServices.Log;
import ShoppingCart.InventoryProtector;
import ExternalConnections.PurchasePolicies;
import ShoppingCart.ShoppingCart;
import Store.Product;
import StorePermission.Permission;
import StorePermission.StoreRoles;
import User.Guest;
import User.Subscriber;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class BigController {
    private StoreController sc;
    private UserController us;
    Log my_log = Log.getLogger();



    //todo Guy - add function to get Inverntory Protectore
    public BigController() throws IOException {
        this.us = new UserController();
        this.sc = new StoreController();
        initiateExternalConnections();
        my_log.logger.info("System Started");
    }

    public void initiateExternalConnections() {
        ExternalConnections externalConnections = ExternalConnections.getInstance();
        externalConnections.addPayment(new Visa());
        externalConnections.addPayment(new MasterCard());
        externalConnections.addDelivery(new FedEx());
        externalConnections.addDelivery(new UPS());
    }

    //// user controller
    public void addSystemAdmin(String whoIsAdding,String user_toMakeAdmin) {
        my_log.logger.info("adding system admin");
        getUserController().addSystemAdmin(whoIsAdding,user_toMakeAdmin);
    }
    public boolean deleteUser(String whosDeleting,String whosBeingDeleted) throws NoPermissionException {
        my_log.logger.info("user"+whosDeleting+"is trying to delete user"+whosBeingDeleted);
        //try {
        sc.removeAllPermissionTo(whosBeingDeleted);
       // } catch (NoPermissionException e) {
       //     Log.getLogger().logger.warning("cant remove user permission"+ whosBeingDeleted +" by user "+whosDeleting+ "because:  "+ e.getMessage());
       //     return false;
        //}
        return getUserController().deleteUser(whosDeleting,whosBeingDeleted);
    }
    public boolean sign_up(String user_name, String password) {
        my_log.logger.info("user "+user_name+ " is trying to sign up");
        return getUserController().sign_up(user_name, password);
    }

    public boolean login(String user_name, String password) {
        my_log.logger.info("user "+user_name+ " is trying to login");
        return getUserController().login(user_name,password);
    }

    public boolean logout(String user_name) {
        my_log.logger.info("user "+user_name+ " is trying to logout");
        return getUserController().logout(user_name);
    }
    public void sendComplaint(String userId, String StoreName,String complaint ) {
        my_log.logger.info("user "+userId+ " is sending a complain");
        getUserController().sendComplaint(userId,StoreName,complaint);
    }

    public Guest getGuest(String id) {
        return getUserController().getGuest(id);
    }

    public String addGuest() {
        my_log.logger.info("adding guest");
        return getUserController().addGuest().name;
    }
    public String GuestExitSystem(String name) {
       return getUserController().GuestExitSystem(name);
    }

        public Subscriber getSystemAdmin() {
        return getUserController().getSystemAdmin();
    }

    public void add_subscriber(Subscriber s) {
        my_log.logger.info("adding subscriber");
        getUserController().add_subscriber(s);
    }
    public List<Guest> getGuest_list() {
        my_log.logger.info("getting guests list");

        return getUserController().getGuest_list();
    }

    public List<Subscriber> getUser_list() {
        my_log.logger.info("getting subscribers list");
        return getUserController().getUser_list();
    }
    public void Add_Query(String user_name,String query) {
        my_log.logger.info("user: "+user_name+ " is adding a query");
        getUserController().Add_Query(user_name,query);
    }
    public Subscriber get_subscriber(String user_name) {
        my_log.logger.info("getting subscriber " +user_name );
        return getUserController().get_subscriber(user_name);
    }
    public ShoppingCart getShoppingCart(String user_Id){
        my_log.logger.info("getting shopping cart for user: "+user_Id);
        return getUserController().getShoppingCart(user_Id);
    }
    public boolean containsStore(String user_id,String storeID) {
        return getUserController().containsStore(user_id,storeID);
    }
    public int removeProduct(String user_id,String productID, String storeID, int amount) {
        my_log.logger.info("user: "+user_id +"is trying to remove product with id:" +productID +"from store with id:"+ storeID);
        return getUserController().removeProduct(user_id,productID,storeID,amount);
    }

    public int addProduct(String user_id, String productID, String storeID, int amount, boolean auctionOrBid) {
        my_log.logger.info("user with id:"+user_id+ " is adding product");
        InventoryProtector inventoryProtector = sc.getInventoryProtector(storeID);
        return getUserController().addProduct(user_id,productID,storeID,amount,inventoryProtector,auctionOrBid);
    }
    public String getCartInventory(String user_id) {
        my_log.logger.info("getting cart inventory for user with id: "+user_id);
        return getUserController().getCartInventory(user_id);
    }
    public float purchaseCart(String user_id, String payment,String delivery) {
        my_log.logger.info("trying to purchase cart");
        return getUserController().purchaseCart(user_id,new PurchasePolicies(payment,delivery));
    }

    /// Store controller

    public void addNewProduct(String storeId, String userId, String productName, float price, int supply, String category) throws NoPermissionException {
        my_log.logger.info("adding new product");
        if(getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId))
        getStoreController().addNewProduct(storeId,userId,productName,price,supply,category);
        else
            throw new IllegalArgumentException("couldn't add new product because the given userId doesn't exist or is not logged in");
    }

    public String openNewStore( String userId, String storeName){
        my_log.logger.info("trying to opening store");
        if(getUserController().checkIfUserExists(userId) && getUserController().checkIfUserIsLoggedIn(userId)) {
            List<String> managers = new ArrayList<>();
            managers.add(userId);
            return getStoreController().openNewStore(storeName, managers);
        }
        else
            throw new IllegalArgumentException("couldn't open store because the given userId doesn't exist or is not logged in");
    }

    public void unfreezeStore(String storeId, String userId) throws NoPermissionException {
        my_log.logger.info("trying to unfreeze store");
        if(getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId))
            getStoreController().unfreezeStore(storeId,userId);
        else
            throw new IllegalArgumentException("couldn't close store because the given userId doesn't exist or is not logged in");
    }
    public void freezeStore(String storeId, String userId) throws NoPermissionException {
        my_log.logger.info("trying to freeze store");
        if(getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId))
            getStoreController().freezeStore(storeId,userId);
        else
            throw new IllegalArgumentException("couldn't open store because the given userId doesn't exist or is not logged in");
    }
    public List<StoreRoles> getInfoOnManagersOwners(String storeId, String userId) throws NoPermissionException {
        my_log.logger.info("getting info on managers owners");
        if(!getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId)){
            my_log.logger.warning("User doesn't exist or is not logged in or is not logged in");
            return null;
        }
        return getStoreController().getInfoOnManagersOwners(storeId,userId);
    }
    public void editProduct(String storeId, String userId, String productId, int newSupply, String newName, float newPrice, String category) throws NoPermissionException {
        my_log.logger.info("trying to edit product");
        if(getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId))
            getStoreController().editProduct(storeId,userId,productId,newSupply,newName,newPrice,category);
        else
            throw new IllegalArgumentException("couldn't edit product  because the given userId doesn't exist or is not logged in");
    }

    public void deleteProduct(String storeId, String userId, String productId) throws NoPermissionException {
        my_log.logger.info("trying to delete product with id:" + productId);
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

    public void createOwner(String storeId, String userIdGiving, String UserGettingPermissionId, List<Permission> permissions) throws NoPermissionException {
        if(getUserController().checkIfUserExists(userIdGiving) && getUserController().checkIfUserExists(UserGettingPermissionId)&&getUserController().checkIfUserIsLoggedIn(userIdGiving)){
            getStoreController().createOwner(storeId,userIdGiving,UserGettingPermissionId,permissions);
        }
        else
            throw new IllegalArgumentException("couldn't give permission because the given userId doesn't exist or is not logged in");
    }
    public void createManager(String storeId, String userIdGiving, String UserGettingPermissionId) throws NoPermissionException {
        if(getUserController().checkIfUserExists(userIdGiving) && getUserController().checkIfUserExists(UserGettingPermissionId)&&getUserController().checkIfUserIsLoggedIn(userIdGiving)){
            getStoreController().createManager(storeId,userIdGiving,UserGettingPermissionId);
        }
        else
            throw new IllegalArgumentException("couldn't give permission because the given userId doesn't exist or is not logged in");
    }




    public void addReviewToProduct(String storeId, String userId, String productId, String Title, String Body, float rating){
        if(!getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId))
            throw new IllegalArgumentException("User doesn't exist or is not logged in or is not logged in");
        getStoreController().addReviewToProduct(storeId,userId,productId,Title,Body,rating);
    }

    public String addNewThreadToForum(String storeId,String title, String userId){
        if(!getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId)){
            my_log.logger.warning("User doesn't exist or is not logged in or is not logged in");
            return null;
        }
        return getStoreController().addNewThreadToForum(storeId,title,userId);
    }

    public void postMessageToForum(String storeId, String threadId, String userId, String message) throws NoPermissionException {
        if(!getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId)){
            my_log.logger.warning("User doesn't exist or is not logged in or is not logged in");
        }
        getStoreController().postMessageToForum(storeId,threadId,userId,message);
    }


    public HashMap<String,List<Product>> getAllProductsAndStores(String userId){
        my_log.logger.info("getting a look at all products and stores");
        for(Guest g :getGuest_list()){
            if(g.name.equals(userId)){
               return getStoreController().getAllProductsAndStores();
            }
        }
        if(!getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId)){
            my_log.logger.warning("User doesn't exist or is not logged in or is not logged in");
        return null;
        }
        return getStoreController().getAllProductsAndStores();
    }

    public List<Product> SearchProductsAccordingName(String userId,String productName){
        my_log.logger.info("searching products according name");
        for(Guest g :getGuest_list()){
            if(g.name.equals(userId)){
                getStoreController().SearchProductsAccordingName(productName);
            }
        }
        if(!getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId)){
            my_log.logger.warning("User doesn't exist or is not logged in or is not logged in");
            return null;
        }
       return getStoreController().SearchProductsAccordingName(productName);
    }

    public List<Product> SearchProductsAccordingCategory(String userId,List<String> categories ){
        my_log.logger.info("searching products according category");
        for(Guest g :getGuest_list()){
            if(g.name.equals(userId)){
                getStoreController().SearchProductsAccordingCategory(categories);
            }
        }
        if(!getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId)){
            my_log.logger.warning("User doesn't exist or is not logged in or is not logged in");
            return null;
        }
        return getStoreController().SearchProductsAccordingCategory(categories);

    }
    public List<Product> SearchProductsAccordingPrice(String userId, float fromPrice, float toPrice ){
        my_log.logger.info("searching products according price");
        for(Guest g :getGuest_list()){
            if(g.name.equals(userId)){
                getStoreController().SearchProductsAccordingPrice(fromPrice,toPrice);
            }
        }
        if(!getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId)){
            my_log.logger.warning("User doesn't exist or is not logged in or is not logged in");
            return null;
        }
        return getStoreController().SearchProductsAccordingPrice(fromPrice,toPrice);

    }
    public List<Product> SearchProductsAccordingRating(String userId,float productRating){
        my_log.logger.info("searching products according rating");
        for(Guest g :getGuest_list()){
            if(g.name.equals(userId)){
               return getStoreController().SearchProductsAccordingRating(productRating);
            }
        }
        if(!getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId)){
            my_log.logger.warning("User doesn't exist or is not logged in or is not logged in");
        return null;}
        return getStoreController().SearchProductsAccordingRating(productRating);
    }

    public void deleteStore(String userId, String storeId) {
        my_log.logger.info("trying to delete store :"+storeId);
        getStoreController().deleteStore(userId,storeId);
    }

    public UserController getUserController() {
        return us;
    }

    public StoreController getStoreController() {
        return sc;
    }
}
