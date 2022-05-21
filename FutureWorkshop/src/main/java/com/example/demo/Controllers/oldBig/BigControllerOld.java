package Controllers;


import CustomExceptions.CantPurchaseException;
import CustomExceptions.NotifyException;
import CustomExceptions.StorePolicyViolatedException;
import CustomExceptions.SupplyManagementException;
import CustomExceptions.UserException;
import ExternalConnections.Delivery.DeliveryNames;
import ExternalConnections.Delivery.FedEx;
import ExternalConnections.Delivery.UPS;
import ExternalConnections.ExternalConnections;
import ExternalConnections.Payment.MasterCard;
import ExternalConnections.Payment.PaymentNames;
import ExternalConnections.Payment.Visa;
import GlobalSystemServices.Log;
import History.PurchaseHistory;
import main.java.com.example.demo.NotificationsManagement.ComplaintNotification;
import main.java.com.example.demo.NotificationsManagement.NotificationManager;
import ShoppingCart.InventoryProtector;
import ExternalConnections.ExternalConnectionHolder;
import ShoppingCart.ShoppingCart;
import Store.Product;
import Store.Store;
import main.java.com.example.demo.Store.StorePurchase.Policies.Policy;
import StorePermission.Permission;
import StorePermission.StoreRoles;
import User.Guest;
import User.Subscriber;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BigController {
    private StoreController sc;
    private UserController us;
    Log my_log = Log.getLogger();

    public BigController() throws IOException {
        this.us = new UserController();
        this.sc = new StoreController();
        NotificationManager.buildNotificationManager(us);
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
        getUserController().addSystemAdmin(whoIsAdding,user_toMakeAdmin);
    }
    public boolean deleteUser(String whosDeleting,String whosBeingDeleted) throws NoPermissionException {
        my_log.logger.info("user"+whosDeleting+"is trying to delete user"+whosBeingDeleted);
        sc.removeRoleInHierarchy(whosBeingDeleted);

        return getUserController().deleteUser(whosDeleting,whosBeingDeleted);
    }
    public boolean sign_up(String guestId,String user_name, String password){
        my_log.logger.info("user "+user_name+ " is trying to sign up");
        return getUserController().sign_up(guestId,user_name, password);
    }
    public String getPermissionType(String username) {
       return getUserController().getPermissionType(username);
    }
        public void readComplaintNotification(String userid,int complaintNotificaionId) throws UserException {
     getUserController().readComplaintNotification(userid,complaintNotificaionId);
    }
    public void readStoreNotification(String userid,int storeNotificaionId) throws UserException {
        getUserController().readStoreNotification(userid,storeNotificaionId);
    }

        public boolean login(String user_name, String password) {
        return getUserController().login(user_name,password);
    }

    public boolean logout(String user_name) {
        return getUserController().logout(user_name);
    }
    public void sendComplaint(String userId, List<String> adminIds, ComplaintNotification complaintNotification) throws UserException {
        getUserController().sendComplaintTo(userId,adminIds,complaintNotification);
    }

    public Guest getGuest(String id) {
        return getUserController().getGuest(id);
    }

    public String addGuest() {
        return getUserController().addGuest().name;
    }
    public String GuestExitSystem(String name) {
       return getUserController().GuestExitSystem(name);
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
    public void Add_Query(String user_name,String query) throws UserException {
        getUserController().Add_Query(user_name,query);
    }
    public Subscriber get_subscriber(String user_name) {
        return getUserController().get_subscriber(user_name);
    }
    public ShoppingCart getShoppingCart(String user_Id) throws UserException {
        return getUserController().getShoppingCart(user_Id);
    }
    public boolean containsStore(String user_id,String storeID) throws UserException {
        return getUserController().containsStore(user_id,storeID);
    }
    public int removeProductFromCart(String user_id, String productID, String storeID, int amount) throws UserException {
        return getUserController().removeProduct(user_id,productID,storeID,amount);
    }

    public int addProductFromCart(String user_id, String productID, String storeID, int amount, boolean auctionOrBid) throws UserException {
        InventoryProtector inventoryProtector = sc.getInventoryProtector(storeID);
        return getUserController().addProduct(user_id,productID,storeID,amount,inventoryProtector,auctionOrBid);
    }
    public String getCartInventory(String user_id) throws UserException {
        return getUserController().getCartInventory(user_id);
    }
    public float purchaseCart(String user_id, PaymentNames payment, DeliveryNames delivery) throws SupplyManagementException, StorePolicyViolatedException, CantPurchaseException {
        return getUserController().purchaseCart(user_id,new ExternalConnectionHolder(delivery,payment));
    }

    /// Store controller

    public void addNewProductToStore(String storeId, String userId, String productName, float price, int supply, String category) throws NoPermissionException, SupplyManagementException {
        if(getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId))
        getStoreController().addNewProduct(storeId,userId,productName,price,supply,category);
        else
            throw new IllegalArgumentException("couldn't add new product because the given userId doesn't exist or is not logged in");
    }

    public void removeSomePermissions(String storeId, String userIdRemoving, String UserAffectedId, List<String> PerToRemove) throws NoPermissionException {
        if(getUserController().checkIfUserExists(userIdRemoving)&&getUserController().checkIfUserIsLoggedIn(userIdRemoving)) {
            sc.removeSomePermissions(storeId, userIdRemoving, UserAffectedId, PerToRemove);
        }
        else
            throw new IllegalArgumentException("couldn't add new product because the given userId doesn't exist or is not logged in");
    }

    public String openNewStore( String userId, String storeName) throws NoPermissionException {
        if(getUserController().checkIfUserExists(userId) && getUserController().checkIfUserIsLoggedIn(userId)) {
            List<String> managers = new ArrayList<>();
            managers.add(userId);
            return getStoreController().openNewStore(storeName, managers);
        }
        else
            throw new IllegalArgumentException("couldn't open store because the given userId doesn't exist or is not logged in");
    }

    public void unfreezeStore(String storeId, String userId) throws NoPermissionException {
        if(getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId))
            getStoreController().unfreezeStore(storeId,userId);
        else
            throw new IllegalArgumentException("couldn't close store because the given userId doesn't exist or is not logged in");
    }
    public void freezeStore(String storeId, String userId) throws NoPermissionException {
        if(getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId))
            getStoreController().freezeStore(storeId,userId);
        else
            throw new IllegalArgumentException("couldn't open store because the given userId doesn't exist or is not logged in");
    }
    public List<StoreRoles> getInfoOnManagersOwners(String storeId, String userId) throws NoPermissionException {
        if(!getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId)){
            my_log.logger.warning("User doesn't exist or is not logged in or is not logged in");
            return null;
        }
        return getStoreController().getInfoOnManagersOwners(storeId,userId);
    }
    public void editProduct(String storeId, String userId, String productId, int newSupply, String newName, float newPrice, String category) throws NoPermissionException, SupplyManagementException {
        if(getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId))
            getStoreController().editProduct(storeId,userId,productId,newSupply,newName,newPrice,category);
        else
            throw new IllegalArgumentException("couldn't edit product  because the given userId doesn't exist or is not logged in");
    }

    public void deleteProductFromStore(String storeId, String userId, String productId) throws NoPermissionException, SupplyManagementException {
        if(getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId))
            getStoreController().deleteProduct(storeId,userId,productId);
        else
            throw new IllegalArgumentException("couldn't delete product  because the given userId doesn't exist or is not logged in");
    }


    public void removePermissionTo(String storeId, String userIdRemoving,String UserAffectedId) throws NoPermissionException{
        if(getUserController().checkIfUserExists(userIdRemoving) && getUserController().checkIfUserExists(UserAffectedId)&&getUserController().checkIfUserIsLoggedIn(userIdRemoving))
            getStoreController().removeRoleInHierarchy(storeId,userIdRemoving,UserAffectedId);
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




    public void addReviewToProduct(String storeId, String userId, String productId, String Title, String Body, float rating) throws NoPermissionException, SupplyManagementException {
        if(!getUserController().checkIfUserExists(userId)&&getUserController().checkIfUserIsLoggedIn(userId))
            throw new IllegalArgumentException("User doesn't exist or is not logged in or is not logged in");
        getStoreController().addReviewToProduct(storeId,userId,productId,Title,Body,rating);
    }

    public String addNewThreadToForum(String storeId,String title, String userId) throws NoPermissionException {
        if(!getUserController().checkIfUserExists(userId)||!getUserController().checkIfUserIsLoggedIn(userId)){
            my_log.logger.warning("User doesn't exist or is not logged in or is not logged in");
            return null;
        }
        return getStoreController().addNewThreadToForum(storeId,title,userId);
    }

    public void postMessageToForum(String storeId, String threadId, String userId, String message) throws NoPermissionException, NotifyException, UserException {
        if(!getUserController().checkIfUserExists(userId)||!getUserController().checkIfUserIsLoggedIn(userId)){
            my_log.logger.warning("User doesn't exist or is not logged in or is not logged in");
        }
        getStoreController().RolePostMessageToForum(storeId,threadId,userId,message);
    }


    public HashMap<String,List<Product>> getAllProductsAndStores(String userId){
        for(Guest g :getGuest_list()){
            if(g.name.equals(userId)){
               return getStoreController().getAllProductsAndStores();
            }
        }
        if(!getUserController().checkIfUserExists(userId)||!getUserController().checkIfUserIsLoggedIn(userId)){
            my_log.logger.warning("User doesn't exist or is not logged in or is not logged in");
        return null;
        }
        return getStoreController().getAllProductsAndStores();
    }

    public List<Product> SearchProductsAccordingName(String userId,String productName){
        for(Guest g :getGuest_list()){
            if(g.name.equals(userId)){
                getStoreController().SearchProductsAccordingName(productName);
            }
        }
        if(!getUserController().checkIfUserExists(userId)||!getUserController().checkIfUserIsLoggedIn(userId)){
            return null;
        }
       return getStoreController().SearchProductsAccordingName(productName);
    }

    public List<Product> SearchProductsAccordingCategory(String userId,List<String> categories ){
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
        for(Guest g :getGuest_list()){
            if(g.name.equals(userId)){
                getStoreController().SearchProductsAccordingPrice(fromPrice,toPrice);
            }
        }
        if(!getUserController().checkIfUserExists(userId)||!getUserController().checkIfUserIsLoggedIn(userId)){
            return null;
        }
        return getStoreController().SearchProductsAccordingPrice(fromPrice,toPrice);

    }
    public List<Product> SearchProductsAccordingRating(String userId,float productRating){
        for(Guest g :getGuest_list()){
            if(g.name.equals(userId)){
               return getStoreController().SearchProductsAccordingRating(productRating);
            }
        }
        if(!getUserController().checkIfUserExists(userId)||!getUserController().checkIfUserIsLoggedIn(userId)){
            my_log.logger.warning("User doesn't exist or is not logged in or is not logged in");
        return null;}
        return getStoreController().SearchProductsAccordingRating(productRating);
    }

    public void deleteStore(String userId, String storeId) throws NoPermissionException {
        getStoreController().deleteStore(userId,storeId);
    }
    public String addNewPolicy(String storeId,String userId, Policy policy) throws NoPermissionException {//todo need to use policyBuilder to create policy
        if(!getUserController().checkIfUserExists(userId)||!getUserController().checkIfUserIsLoggedIn(userId)){
            my_log.logger.warning("User doesn't exist or is not logged in or is not logged in");
            return null;
        }
        return sc.addNewPolicy(storeId,userId,policy);
    }
    public void deletePolicy(String storeId,String userId, String policyId) throws NoPermissionException {
        if(!getUserController().checkIfUserExists(userId)||!getUserController().checkIfUserIsLoggedIn(userId)){
            my_log.logger.warning("User doesn't exist or is not logged in or is not logged in");
        }
        sc.deletePolicy(storeId,userId,policyId);
    }
    private void checkIfUserHaveRoleInStore(){
        //todo
    }
    public List<Permission> getUserPermission(String StoreId,String userId){
       return sc.getUserPermission(StoreId,userId);
    }

    /**
     * @return - manger/owner/no title
     */
    public String getTitle(String StoreId, String userIf){
        return sc.getTitle(StoreId,userIf);
    }
    public List<Policy> getPolices(String storeId,String userId) throws NoPermissionException {
        if(!getUserController().checkIfUserExists(userId)||!getUserController().checkIfUserIsLoggedIn(userId)){
            my_log.logger.warning("User doesn't exist or is not logged in or is not logged in");
            return null;
        }
        return sc.getPolices(storeId,userId);
    }
    public List<PurchaseHistory> getStoreHistory(String storeId, String userIdRequesting) throws NoPermissionException{
        if(!getUserController().checkIfUserExists(userIdRequesting)||!getUserController().checkIfUserIsLoggedIn(userIdRequesting)){
            my_log.logger.warning("User doesn't exist or is not logged in or is not logged in");
            return null;
        }
        return sc.getStoreHistory(storeId, userIdRequesting);
    }
    public List<PurchaseHistory> getStoreHistory(String userIdRequesting, String storeId, String userId) throws NoPermissionException{
        if(!getUserController().checkIfUserExists(userId)||!getUserController().checkIfUserIsLoggedIn(userId)){
            my_log.logger.warning("User doesn't exist or is not logged in or is not logged in");
            return null;
        }
        return sc.getStoreHistory(userIdRequesting, storeId, userId);
    }
    public List<Store> getAllStoresByStoreName(String name){
        return sc.getAllStoresByStoreName(name);
    }
    private UserController getUserController() {
        return us;
    }

    private StoreController getStoreController() {
        return sc;
    }
}