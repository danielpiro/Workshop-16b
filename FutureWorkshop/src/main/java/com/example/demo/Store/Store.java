package com.example.demo.Store;

//import org.junit.platform.engine.support.hierarchical.ThrowableCollector;

import com.example.demo.CustomExceptions.Exception.NotifyException;
import com.example.demo.CustomExceptions.Exception.SupplyManagementException;
import com.example.demo.CustomExceptions.Exception.UserException;
import com.example.demo.Database.DTOobjects.Store.Permissions.StoreRoleDTO;
import com.example.demo.Database.DTOobjects.Store.StoreDTO;
import com.example.demo.Database.Service.DatabaseService;
import com.example.demo.GlobalSystemServices.IdGenerator;
import com.example.demo.History.History;
import com.example.demo.History.PurchaseHistory;
import com.example.demo.NotificationsManagement.NotificationManager;
import com.example.demo.NotificationsManagement.NotificationSubject;
import com.example.demo.NotificationsManagement.StoreNotification;
import com.example.demo.NotificationsManagement.getStoreInfo;
import com.example.demo.ShoppingCart.InventoryProtector;
import com.example.demo.Store.Forum.Forum;
import com.example.demo.Store.Forum.ForumThread;
import com.example.demo.Store.StorePurchase.Discounts.Discount;
import com.example.demo.Store.StorePurchase.Policies.Policy;
import com.example.demo.StorePermission.OriginalStoreOwnerRole;
import com.example.demo.StorePermission.Permission;
import com.example.demo.StorePermission.StoreRoleType;
import com.example.demo.StorePermission.StoreRoles;

import javax.naming.NoPermissionException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Store implements getStoreInfo {
    private String storeName;
    private final String storeId;
    private List<StoreRoles> StoreRoles;
    private InventoryManager inventoryManager;
    private Forum forum;
    private StoreState storeState;

    private Float storeRating; //rating between 1 - 5
    private List<Review> reviews;

    private DatabaseService databaseService;

    public Store(StoreDTO storeDto,DatabaseService databaseService) throws SQLException {//build from database
        this.databaseService = databaseService;
        this.storeName = storeDto.getStoreName();
        this.storeId = storeDto.getStoreId();
        inventoryManager = new InventoryManager();//todo need to load from database inventory manager
        forum =new Forum();
        storeState = StoreState.valueOf(storeDto.getStoreState());
        storeRating = storeDto.getStoreRating();
        StoreRoles = databaseService.getRolesOfStore(storeDto.getStoreId());
    }
    public Store(String storeName, String storeId, List<String> storeOriginalManager, DatabaseService databaseService) throws SQLException {
        this.databaseService = databaseService;
        this.storeName = storeName;
        this.storeId = storeId;
        inventoryManager = new InventoryManager();
        forum =new Forum();
        storeState = StoreState.ACTIVE;
        storeRating = 0F;
        StoreRoles = new ArrayList<>();
        HashMap<StoreRoleDTO, List<Permission>> storeRoleDTOListHashMap = new HashMap<>();
        for (String userId : storeOriginalManager) {
            OriginalStoreOwnerRole originalStoreOwnerRole = new OriginalStoreOwnerRole(userId);
            StoreRoles.add(originalStoreOwnerRole);
            StoreRoleDTO storeRoleDTO = new StoreRoleDTO(userId,storeId,StoreRoleType.original_owner.toString());
            storeRoleDTOListHashMap.put(storeRoleDTO,originalStoreOwnerRole.getPermissions());
        }
        saveStore(storeRoleDTOListHashMap);
    }
    private void saveStore(HashMap<StoreRoleDTO, List<Permission>> storeRoleDTOListHashMap) throws SQLException {
        StoreDTO storeDTO = new StoreDTO(storeId,storeName,storeState.toString(),storeRating);
        databaseService.saveStore(storeDTO,storeRoleDTOListHashMap);
    }


    private boolean checkPermission(String userId, Permission action){
        synchronized (StoreRoles) {
        for (StoreRoles roleUser : StoreRoles) {
            if (roleUser.getUserId().equals(userId) &&
                    roleUser.getPermissions().contains(action) ) {
                return true;
            }
        }
        return false;
        }
    }

    public void createManager(String userIdGiving, String UserGettingPermission) throws NoPermissionException, NotifyException, UserException, SQLException {
        synchronized (StoreRoles) {
            for (StoreRoles roleUser : StoreRoles) {
                if (roleUser.getUserId().equals(UserGettingPermission)) {
                    throw new NoPermissionException("cant give permissions to user who is already manager");
                }
            }
            for (StoreRoles roleUser : StoreRoles) {
                if (roleUser.getUserId().equals(userIdGiving)) {
                    StoreRoles newRole = roleUser.createManager(UserGettingPermission,storeId,databaseService);
                    StoreRoles.add(newRole);
                    StoreNotification sn = new StoreNotification(this,NotificationSubject.StoreAppointment,"you got manager role",userIdGiving+" made you manager in store name:"+storeName+" store Id"+storeId);
                    NotificationManager.getNotificationManager().sendNotificationTo(UserGettingPermission,sn);
                    return;
                }
            }
            throw new NoPermissionException("the user is not manager/owner");
        }
    }
    public void createOwner(String userIdGiving, String UserGettingPermission, List<Permission> permissions) throws NoPermissionException, NotifyException, UserException, SQLException {
        synchronized (StoreRoles) {
            for (StoreRoles roleUser : StoreRoles) {
                if (roleUser.getUserId().equals(UserGettingPermission)) {
                    throw new NoPermissionException("cant give permissions to user who is already owner");
                }
            }
            for (StoreRoles roleUser : StoreRoles) {
                if (roleUser.getUserId().equals(userIdGiving)) {
                    StoreRoles newRole = roleUser.createOwner(UserGettingPermission, permissions,storeId,databaseService);
                    StoreRoles.add(newRole);
                    StoreNotification sn = new StoreNotification(this,NotificationSubject.StoreAppointment,"you got owner role",userIdGiving+" made you owner in store name:"+storeName+" store Id"+storeId);
                    NotificationManager.getNotificationManager().sendNotificationTo(UserGettingPermission,sn);
                    return;
                }
            }
            throw new NoPermissionException("the user is not manager/owner");
        }
    }
    private void removeRolesInStoreTo(List<String> RolesIdToRemove){
        synchronized (StoreRoles) {
            for (String id : RolesIdToRemove) {
                for (int i = 0; i < StoreRoles.size(); i++) {
                    if (IdGenerator.getInstance().isIdEqual(
                            StoreRoles.get(i).getUserId(),
                            id)
                    ) {
                        StoreRoles.remove(i);
                        break;
                    }
                }
            }
        }
    }
    public void removeRoleInHierarchy(String userIdRemoving, String UserAffectedId) throws NoPermissionException, NotifyException, UserException {
        synchronized (StoreRoles){
            for (StoreRoles roleUser : StoreRoles) {
                if (roleUser.getUserId().equals(userIdRemoving) ) {
                    removeRolesInStoreTo( roleUser.removeRole(UserAffectedId));
                    StoreNotification sn = new StoreNotification(this,NotificationSubject.StoreAppointment,"your role removed",userIdRemoving+" removed your role in store name:"+storeName+" store Id"+storeId);
                    NotificationManager.getNotificationManager().sendNotificationTo(UserAffectedId,sn);
                    return;
                }
            }
            throw new NoPermissionException("the user doesn't have a role in store ");
        }
    }
    public void removeRoleInHierarchy(String userId) throws NotifyException, UserException {
        synchronized (StoreRoles) {
            for (StoreRoles roleUser : StoreRoles) {
                removeRolesInStoreTo(roleUser.removeRole(userId));
                StoreNotification sn = new StoreNotification(this,NotificationSubject.StoreAppointment,"your role removed"," your role in store name:"+storeName+" store Id"+storeId);
                NotificationManager.getNotificationManager().sendNotificationTo(userId,sn);
            }
        }
    }

    public void removeSomePermissions(String userIdRemoving, String UserAffectedId, List<String> PerToRemove) throws NoPermissionException {
        List<Permission> permissions = new ArrayList<>();
        for (String perString: PerToRemove) {
            permissions.add(Permission.valueOf(perString));
        }
        synchronized (StoreRoles) {
            for (StoreRoles roleUser : StoreRoles) {
                if (roleUser.getUserId().equals(userIdRemoving) ) {
                    roleUser.removePerToRole(UserAffectedId, permissions);
                    return;
                }
            }
            throw new NoPermissionException("the user doesn't have a role in store ");
        }
    }
    public void editProduct(String userId, String productId, int newSupply, String newName, float newPrice , String category) throws NoPermissionException, SupplyManagementException, SupplyManagementException {
        if(!checkPermission(userId, Permission.EDIT_EXISTING_PRODUCT)){
            throw new NoPermissionException("the user don't have this permission");
        }
        inventoryManager.editProduct(productId, newSupply, newName, newPrice, category);
    }

    public String addNewProduct(String userId, String productName, float price, int howMuch, String category) throws NoPermissionException,  SupplyManagementException, SupplyManagementException {
        if(!checkPermission(userId, Permission.ADD_NEW_PRODUCT)){
            throw new NoPermissionException("the user don't have this permission");
        }
        return inventoryManager.addNewProduct(productName, price, howMuch, category);

    }

    public void addProductReview(String userId, String productId, String title, String body, float rating) throws  SupplyManagementException {

        inventoryManager.addProductReview(productId, userId, title, body, rating);

        //updateRating();
    }

    public void addReviewToStore(float rating, String userId, String title, String body){
        synchronized (reviews) {
            reviews.add(new Review(rating, userId, title, body));
        }

        float sum = 0;
        synchronized (reviews) {
            for (Review rev :
                    reviews) {
                sum = rev.getRating() + sum;
            }
            synchronized (storeRating) {
                this.storeRating = sum / (reviews.size());
            }
        }
    }

    public String addNewThreadToForum(String title, String userId){
        String output;
        synchronized (forum){
            output  = forum.addNewThread(title, userId);
        }
        return output;

    }
    public void RolePostMessageToForum(String threadId, String userId, String message) throws NoPermissionException, NotifyException, UserException {
        if(!checkPermission(userId, Permission.REPLY_TO_FORUM)){
            throw new NoPermissionException("the user don't have this permission");
        }
        synchronized (forum) {
            forum.postMessage(threadId, userId, message);
        }
        NotificationManager.getNotificationManager().sendNotificationTo(userId, new StoreNotification(this, NotificationSubject.StoreForum,"someone in store replied to your message","message: "+message));
    }

    public void  userPostMessageToForum(String threadId, String userId, String message) throws NoPermissionException,  NotifyException,  UserException {
        String threadUser;
        synchronized (forum) {
            threadUser = forum.getUserIdOfTread(threadId);
        }
        if(!IdGenerator.getInstance().isIdEqual(threadUser, userId)){
            throw new NoPermissionException("only user that created the forum thread can post messages");
        }
        synchronized (forum) {
            forum.postMessage(threadId, userId, message);
        }
        NotificationManager.getNotificationManager().sendNotificationTo(getRolesIds(), new StoreNotification(this, NotificationSubject.StoreForum,"user post message to forum","message: "+message));
    }


    public void deleteProduct(String userId, String productId) throws NoPermissionException, SupplyManagementException, NotifyException, UserException {
        if(!checkPermission(userId, Permission.ADD_NEW_PRODUCT)){
            throw new NoPermissionException("the user don't have this permission");
        }
        inventoryManager.deleteProduct(productId);
        StoreNotification sn = new StoreNotification(this,NotificationSubject.StoreDeleted,"your store deleted",userId+" deleted your store name:"+storeName+" store Id"+storeId);
        NotificationManager.getNotificationManager().sendNotificationTo(getRolesIds(),sn);

    }

    public void closeStore(String userId) throws NoPermissionException, NotifyException, UserException {
        if(!checkPermission(userId, Permission.CLOSE_STORE)){
            throw new NoPermissionException("the user don't have this permission");
        }
        synchronized (storeState) {
            storeState = StoreState.CLOSED;
        }
        StoreNotification sn = new StoreNotification(this,NotificationSubject.StoreState,"your store closed",userId+" closed your store name:"+storeName+" store Id"+storeId);
        NotificationManager.getNotificationManager().sendNotificationTo(getRolesIds(),sn);
    }

    public void openStore(String userId) throws NoPermissionException, NotifyException, UserException {
        if(!checkPermission(userId, Permission.OPEN_STORE)){
            throw new NoPermissionException("the user don't have this permission");
        }
        synchronized (storeState) {
            storeState = StoreState.ACTIVE;
        }
        StoreNotification sn = new StoreNotification(this,NotificationSubject.StoreState,"your store opened",userId+" opened your store name:"+storeName+" store Id"+storeId);
        NotificationManager.getNotificationManager().sendNotificationTo(getRolesIds(),sn);
    }

    public String addNewPolicy(String userId, Policy policy) throws NoPermissionException {
        if(!checkPermission(userId, Permission.EDIT_STORE_POLICY)){
            throw new NoPermissionException("the user don't have this permission");
        }
        return inventoryManager.addNewPolicy(policy);
    }
    public void deletePolicy(String userId, String policyId) throws NoPermissionException {
        if(!checkPermission(userId, Permission.EDIT_STORE_POLICY)){
            throw new NoPermissionException("the user don't have this permission");
        }
        inventoryManager.deletePolicy(policyId);
    }
    public List<Policy> getPolices() throws NoPermissionException {

        return inventoryManager.getPolicies();
    }
    public String addNewDiscount(String userId, Discount discount) throws NoPermissionException {
        if(!checkPermission(userId, Permission.EDIT_STORE_DISCOUNT)){
            throw new NoPermissionException("the user don't have this permission");
        }
        return inventoryManager.addNewDiscount(discount);
    }
    public void deleteDiscount(String userId, String discountId) throws NoPermissionException {
        if(!checkPermission(userId, Permission.EDIT_STORE_DISCOUNT)){
            throw new NoPermissionException("the user don't have this permission");
        }
        inventoryManager.deleteDiscount(discountId);
    }
    public List<Discount> getDiscounts(String userId) throws NoPermissionException {
        return inventoryManager.getDiscounts();
    }
    public Discount getDiscount(String userId, String discountId1) {
        return inventoryManager.getDiscount(discountId1);
    }

    public List<StoreRoles> getInfoOnManagersOwners(String userId) throws NoPermissionException {
        if(!checkPermission(userId, Permission.INFO_OF_MANAGERS)){
            throw new NoPermissionException("the user don't have this permission");
        }
        return  Collections.unmodifiableList(StoreRoles);
    }

    private List<String> getRolesIds(){
        List<String> rolesIds = new ArrayList<>();
        synchronized (StoreRoles) {
            for (StoreRoles role : StoreRoles) {
                rolesIds.add(role.getUserId());
            }
        }
        return rolesIds;
    }

    public List<Product> getAllProducts() {
        StoreState storeStateOp;
        synchronized (storeState){
            storeStateOp = this.storeState;
        }
        if(StoreState.ACTIVE == storeStateOp)
            return inventoryManager.getAllProducts((x)->true);
        return new ArrayList<>();
    }
    public List<Product> getProductsNameContains(String PartialName) {
        StoreState storeStateOp;
        synchronized (storeState){
            storeStateOp = this.storeState;
        }
        if(storeStateOp == StoreState.ACTIVE)
            return inventoryManager.getAllProducts(
                    (p)->p.getName().toUpperCase().contains(PartialName.toUpperCase())
            );
        return new ArrayList<Product>();
    }
    public List<Product> getProductsPriceContains(float lowerRange, float upperRange) {
        StoreState storeStateOp;
        synchronized (storeState){
            storeStateOp = this.storeState;
        }
        if(storeStateOp == StoreState.ACTIVE)
            return inventoryManager.getAllProducts(
                    (p)->(p.getPrice() >= lowerRange && p.getPrice() <= upperRange)
            );
        return new ArrayList<Product>();
    }
    public List<Product> getAllProductsCategory(List<String> category) {
        StoreState storeStateOp;
        synchronized (storeState){
            storeStateOp = this.storeState;
        }
        if(storeStateOp == StoreState.ACTIVE)
            return inventoryManager.getAllProducts(
                    (p)->category.stream().anyMatch(cat ->p.getCategory().toString().equals(cat))
            );
        return new ArrayList<Product>();
    }
    public List<Product> getAllProductsRating(float lower, float upper) {
        StoreState storeStateOp;
        synchronized (storeState){
            storeStateOp = this.storeState;
        }
        if(storeStateOp == StoreState.ACTIVE)
            return inventoryManager.getAllProducts(
                    (p)->p.getRating() >= lower && p.getRating() <=upper
            );
        return new ArrayList<Product>();
    }

    public Product getProduct(String productId) throws Exception {
        return inventoryManager.getProduct(productId);
    }
    public List<PurchaseHistory> getStoreHistory(String userId, boolean isAdmin) throws NoPermissionException {
        if (!checkPermission(userId, Permission.VIEW_STORE_HISTORY) && !isAdmin) {
            throw new NoPermissionException("the user don't have this permission and is not an admin");
        }
        return History.getInstance().getStoreHistory(storeId);
    }
    public List<PurchaseHistory> getStoreHistory(String userIdRequesting, String userId) throws NoPermissionException {
        if(!checkPermission(userIdRequesting, Permission.VIEW_STORE_HISTORY)){
            throw new NoPermissionException("the user don't have this permission");
        }
        return History.getInstance().getStoreUserHistory(userId,storeId);
    }

    public ForumThread getThread(String userId) {
        return forum.getThreadOfUserId(userId);
    }
    public String getStoreName() {
        return storeName;
    }
    public StoreState getStoreState() {
        return storeState;
    }
    public String getId() {
        return storeId;
    }
    public InventoryProtector getInventoryProtector(){
        return inventoryManager;
    }


    public String getTitle(String userIf) {
        synchronized (StoreRoles) {
            for (StoreRoles sr : StoreRoles) {
                if (sr.getUserId().equals(userIf)) {
                    return sr.getTitle();
                }
            }
        }
        return "no title";
    }
    public boolean checkIfUserHaveRoleInStore(String userIf) {
        synchronized (StoreRoles) {
            for (StoreRoles sr : StoreRoles) {
                if (sr.getUserId().equals(userIf)) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Permission> getUserPermission(String userId) {
        synchronized (StoreRoles) {
            for (StoreRoles sr : StoreRoles) {
                if (sr.getUserId().equals(userId)) {
                    return sr.getPermissions();
                }
            }
        }
        return new ArrayList<>();
    }

    public float getStoreRating() {
        synchronized (storeRating) {
            return storeRating;
        }
    }


    public boolean isUserOwner(String userID) {
        synchronized (StoreRoles) {
            return StoreRoles.stream().anyMatch(role -> (role.getTitle().equals("owner") ||
                    role.getTitle().equals("original owner")) &&
                    role.getUserId().equals(userID));
        }
    }

    public boolean isUserManager(String userID) {
        synchronized (StoreRoles) {
            return StoreRoles.stream().anyMatch(role ->role.getTitle().equals("manger") &&
                    role.getUserId().equals(userID) );
        }
    }


}
