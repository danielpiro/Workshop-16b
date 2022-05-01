package Store;

import GlobalSystemServices.IdGenerator;
import History.History;
import History.PurchaseHistory;
import ShoppingCart.InventoryProtector;
import Store.Forum.Forum;
import Store.Forum.ForumThread;
import StorePermission.OriginalStoreOwnerRole;
import StorePermission.Permission;
import StorePermission.StoreRoles;
//import org.junit.platform.engine.support.hierarchical.ThrowableCollector;

import javax.naming.NoPermissionException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Store {
    private String storeName;
    private final String storeId;
    private List<StoreRoles> StoreRoles;
    private InventoryManager inventoryManager;
    private Forum forum;

    public StoreState getStoreState() {
        return storeState;
    }

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
            throw new NoPermissionException("the user is not manager");
        }
    }
    public void removeRoleInHierarchy(String userId) {
        synchronized (StoreRoles) {
            for (StoreRoles roleUser : StoreRoles) {
                removeRolesInStoreTo(roleUser.removeRole(userId));
            }
        }
    }

    public void removeSomePermissions(String userIdRemoving, String UserAffectedId, List<String> PerToRemove){
        synchronized (StoreRoles) {

        }
    }
    public void editProduct(String userId, String productId, int newSupply, String newName, float newPrice , String category) throws NoPermissionException {
        if(!checkPermission(userId, Permission.EDIT_EXISTING_PRODUCT)){
            throw new NoPermissionException("the user don't have this permission");
        }
        inventoryManager.editProduct(productId, newSupply, newName, newPrice, category);
    }

    public String addNewProduct(String userId, String productName, float price, int howMuch, String category) throws NoPermissionException {
        if(!checkPermission(userId, Permission.ADD_NEW_PRODUCT)){
            throw new NoPermissionException("the user don't have this permission");
        }
        return inventoryManager.addNewProduct(productName, price, howMuch, category);

    }


    public List<Product> getAllProducts() {
        return inventoryManager.getAllProducts((x)->true);
    }
    public List<Product> getProductsNameContains(String PartialName) {
        return inventoryManager.getAllProducts(
                (p)->p.getName().toUpperCase().contains(PartialName.toUpperCase())
        );
    }
    public List<Product> getProductsPriceContains(float lowerRange, float upperRange) {
        return inventoryManager.getAllProducts(
                (p)->(p.getPrice() >= lowerRange && p.getPrice() <= upperRange)
        );
    }
    public List<Product> getAllProductsCategory(List<String> category) {
        return inventoryManager.getAllProducts(
                (p)->category.stream().anyMatch(cat ->p.getCategory().toString().equals(cat))
        );
    }
    public List<Product> getAllProductsRating(float lower, float upper) {
        return inventoryManager.getAllProducts(
                (p)->p.getRating() >= lower && p.getRating() <=upper
        );
    }


    public InventoryProtector getInventoryProtector(){
        InventoryProtector InProtected = inventoryManager;
        return InProtected;
    }

    public String getId() {
        return storeId;
    }

    public void addProductReview(String userId, String productId, String title, String body, float rating) {
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
    public void RolePostMessageToForum(String threadId, String userId, String message) throws NoPermissionException {
        if(!checkPermission(userId, Permission.REPLY_TO_FORUM)){
            throw new NoPermissionException("the user don't have this permission");
        }
        String threadUser =  forum.getUserIdOfTread(threadId);
        forum.postMessage(threadId, userId, message);
    }

    public void  userPostMessageToForum(String threadId, String userId, String message) throws NoPermissionException {
        String threadUser =  forum.getUserIdOfTread(threadId);
        if(!IdGenerator.getInstance().isIdEqual(threadUser, userId)){
            throw new NoPermissionException("only user that created the forum thread can post messages");
        }
        forum.postMessage(threadId, userId, message);
    }


    public void deleteProduct(String userId, String productId) throws NoPermissionException {
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

    public List<StoreRoles> getInfoOnManagersOwners(String userId) throws NoPermissionException {
        if(!checkPermission(userId, Permission.INFO_OF_MANAGERS)){
            throw new NoPermissionException("the user don't have this permission");
        }
        return  Collections.unmodifiableList(StoreRoles);
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

    public ForumThread getThread(String userId) {
        return forum.getThreadOfUserId(userId);
    }
}
