package Store;

import CustomExceptions.NotifyException;
import CustomExceptions.SupplyManagementException;
import CustomExceptions.UserException;
import GlobalSystemServices.IdGenerator;
import History.History;
import History.PurchaseHistory;
import NotificationsManagement.StoreNotification;
import NotificationsManagement.NotificationManager;
import NotificationsManagement.NotificationSubject;
import NotificationsManagement.getStoreInfo;
import ShoppingCart.InventoryProtector;
import Store.Forum.Forum;
import Store.Forum.ForumThread;
import Store.StorePurchase.Policies.Policy;
import StorePermission.OriginalStoreOwnerRole;
import StorePermission.Permission;
import StorePermission.StoreRoles;


import javax.naming.NoPermissionException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Store implements getStoreInfo {


    private String storeName;
    private final String storeId;
    private List<StoreRoles> StoreRoles;
    private InventoryManager inventoryManager;
    private Forum forum;
    private StoreState storeState;
    private float storeRating; //rating between 1 - 5
    private List<Review> reviews;



    public Store(String storeName, String storeId, List<String> storeOriginalManager) {
        this.storeName = storeName;
        this.storeId = storeId;
        StoreRoles = storeOriginalManager.stream().map(OriginalStoreOwnerRole::new).collect(Collectors.toList());
        inventoryManager = new InventoryManager();
        forum =new Forum();
        storeState = StoreState.ACTIVE;
        storeRating = 0;

    }

    private boolean checkPermission(String userId, Permission action){
        List<StoreRoles> copyStoreRoles;
        synchronized (StoreRoles) {
            copyStoreRoles = List.copyOf(StoreRoles);

        for (StoreRoles roleUser : copyStoreRoles) {
            if (roleUser.getUserId().equals(userId) &&
                    roleUser.getPermissions().contains(action) ) {
                return true;
            }
        }
        return false;
        }
    }

    public void createManager(String userIdGiving, String UserGettingPermission) throws NoPermissionException {
        synchronized (StoreRoles) {
            for (StoreRoles roleUser : StoreRoles) {
                if (roleUser.getUserId().equals(UserGettingPermission)) {
                    throw new NoPermissionException("cant give permissions to user who is already manager");
                }
            }
            for (StoreRoles roleUser : StoreRoles) {
                if (roleUser.getUserId().equals(userIdGiving)) {
                    StoreRoles newRole = roleUser.createManager(UserGettingPermission);
                    StoreRoles.add(newRole);
                    return;
                }
            }
            throw new NoPermissionException("the user is not manager/owner");
        }
    }
    public void createOwner(String userIdGiving, String UserGettingPermission, List<Permission> permissions) throws NoPermissionException {
        synchronized (StoreRoles) {
            for (StoreRoles roleUser : StoreRoles) {
                if (roleUser.getUserId().equals(UserGettingPermission)) {
                    throw new NoPermissionException("cant give permissions to user who is already owner");
                }
            }
            for (StoreRoles roleUser : StoreRoles) {
                if (roleUser.getUserId().equals(userIdGiving)) {
                    StoreRoles newRole = roleUser.createOwner(UserGettingPermission, permissions);
                    StoreRoles.add(newRole);
                    return;
                }
            }
            throw new NoPermissionException("the user is not manager/owner");
        }
    }
    private void removeRolesInStoreTo(List<String> RolesToRemove){
        synchronized (StoreRoles) {
            for (String id : RolesToRemove) {
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
    public void removeRoleInHierarchy(String userIdRemoving, String UserAffectedId) throws NoPermissionException {
        synchronized (StoreRoles){
            for (StoreRoles roleUser : StoreRoles) {
                if (roleUser.getUserId().equals(userIdRemoving) ) {
                    removeRolesInStoreTo( roleUser.removeRole(UserAffectedId));
                    return;
                }
            }
            throw new NoPermissionException("the user doesn't have a role in store ");
        }
    }
    public void removeRoleInHierarchy(String userId) {
        synchronized (StoreRoles) {
            for (StoreRoles roleUser : StoreRoles) {
                removeRolesInStoreTo(roleUser.removeRole(userId));
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
    public void editProduct(String userId, String productId, int newSupply, String newName, float newPrice , String category) throws NoPermissionException, SupplyManagementException {
        if(!checkPermission(userId, Permission.EDIT_EXISTING_PRODUCT)){
            throw new NoPermissionException("the user don't have this permission");
        }
        inventoryManager.editProduct(productId, newSupply, newName, newPrice, category);
    }

    public String addNewProduct(String userId, String productName, float price, int howMuch, String category) throws NoPermissionException, SupplyManagementException {
        if(!checkPermission(userId, Permission.ADD_NEW_PRODUCT)){
            throw new NoPermissionException("the user don't have this permission");
        }
        return inventoryManager.addNewProduct(productName, price, howMuch, category);

    }

    public void addProductReview(String userId, String productId, String title, String body, float rating) throws SupplyManagementException {
        //TODO: check in history user Bought this product
        inventoryManager.addProductReview(productId, userId, title, body, rating);

        //updateRating();
    }

    public void addReviewToStore(float rating, String userId, String title, String body){
        reviews.add(new Review(rating, userId, title, body));
        float sum = 0;
        for (Review rev :
                reviews) {
            sum = rev.getRating()+sum;
        }
        this.storeRating = sum / (reviews.size());
    }

    public String addNewThreadToForum(String title, String userId){
        return forum.addNewThread(title, userId);
    }
    public void RolePostMessageToForum(String threadId, String userId, String message) throws NoPermissionException, NotifyException, UserException {
        if(!checkPermission(userId, Permission.REPLY_TO_FORUM)){
            throw new NoPermissionException("the user don't have this permission");
        }
        forum.postMessage(threadId, userId, message);
        NotificationManager.getNotificationManager().sendNotificationTo(userId, new StoreNotification(this, NotificationSubject.StoreForum,"someone in store replied to your message","message: "+message));
    }

    public void  userPostMessageToForum(String threadId, String userId, String message) throws NoPermissionException, NotifyException, UserException {
        String threadUser =  forum.getUserIdOfTread(threadId);
        if(!IdGenerator.getInstance().isIdEqual(threadUser, userId)){
            throw new NoPermissionException("only user that created the forum thread can post messages");
        }
        forum.postMessage(threadId, userId, message);

        NotificationManager.getNotificationManager().sendNotificationTo(getRolesIds(), new StoreNotification(this, NotificationSubject.StoreForum,"user post message to forum","message: "+message));
    }


    public void deleteProduct(String userId, String productId) throws NoPermissionException, SupplyManagementException {
        if(!checkPermission(userId, Permission.ADD_NEW_PRODUCT)){
            throw new NoPermissionException("the user don't have this permission");
        }
        inventoryManager.deleteProduct(productId);

    }

    public void closeStore(String userId) throws NoPermissionException {
        if(!checkPermission(userId, Permission.CLOSE_STORE)){
            throw new NoPermissionException("the user don't have this permission");
        }
        storeState = StoreState.CLOSED;
    }

    public void openStore(String userId) throws NoPermissionException {
        if(!checkPermission(userId, Permission.OPEN_STORE)){
            throw new NoPermissionException("the user don't have this permission");
        }
        storeState = StoreState.ACTIVE;
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
    public List<Policy> getPolices(String userId) throws NoPermissionException {
        if(!checkPermission(userId, Permission.EDIT_STORE_POLICY)){
            throw new NoPermissionException("the user don't have this permission");
        }
        return inventoryManager.getPolicies();
    }


    public List<StoreRoles> getInfoOnManagersOwners(String userId) throws NoPermissionException {
        if(!checkPermission(userId, Permission.INFO_OF_MANAGERS)){
            throw new NoPermissionException("the user don't have this permission");
        }
        return  Collections.unmodifiableList(StoreRoles);
    }

    private List<String> getRolesIds(){
        List<String> rolesIds = new ArrayList<>();
        for (StorePermission.StoreRoles role : StoreRoles){
            rolesIds.add(role.getUserId());
        }
        return rolesIds;
    }

    public List<Product> getAllProducts() {
        if(storeState == StoreState.ACTIVE)
            return inventoryManager.getAllProducts((x)->true);
        return new ArrayList<Product>();
    }
    public List<Product> getProductsNameContains(String PartialName) {
        if(storeState == StoreState.ACTIVE)
            return inventoryManager.getAllProducts(
                    (p)->p.getName().toUpperCase().contains(PartialName.toUpperCase())
            );
        return new ArrayList<Product>();
    }
    public List<Product> getProductsPriceContains(float lowerRange, float upperRange) {
        if(storeState == StoreState.ACTIVE)
            return inventoryManager.getAllProducts(
                    (p)->(p.getPrice() >= lowerRange && p.getPrice() <= upperRange)
            );
        return new ArrayList<Product>();
    }
    public List<Product> getAllProductsCategory(List<String> category) {
        if(storeState == StoreState.ACTIVE)
            return inventoryManager.getAllProducts(
                    (p)->category.stream().anyMatch(cat ->p.getCategory().toString().equals(cat))
            );
        return new ArrayList<Product>();
    }
    public List<Product> getAllProductsRating(float lower, float upper) {
        if(storeState == StoreState.ACTIVE)
            return inventoryManager.getAllProducts(
                    (p)->p.getRating() >= lower && p.getRating() <=upper
            );
        return new ArrayList<Product>();
    }

    public Product getProduct(String productId) throws Exception {
        return inventoryManager.getProduct(productId);
    }
    public List<PurchaseHistory> getStoreHistory(String userId) throws NoPermissionException {
         if(!checkPermission(userId, Permission.VIEW_STORE_HISTORY)){
             throw new NoPermissionException("the user don't have this permission");
         }
        return History.getInstance().getStoreHistory(storeId);
    }
    public List<PurchaseHistory> getStoreHistory(String userIdRequesting, String userId) throws NoPermissionException {
        if(!checkPermission(userIdRequesting, Permission.VIEW_STORE_HISTORY)){
            throw new NoPermissionException("the user don't have this permission");
        }
        return History.getInstance().getStoreHistory(userId,storeId);
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
        InventoryProtector InProtected = inventoryManager;
        return InProtected;
    }


    public String getTitle(String userIf) {
        for(StoreRoles sr:StoreRoles){
            if(sr.getUserId().equals(userIf)){
                return sr.getTitle();
            }
        }
        return "no title";
    }

    public List<Permission> getUserPermission(String userId) {
        for(StoreRoles sr:StoreRoles){
            if(sr.getUserId().equals(userId)){
                return sr.getPermissions();
            }
        }
        return new ArrayList<>();
    }


}
