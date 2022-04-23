package Store;

import GlobalSystemServices.IdGenerator;
import Store.Forum.Forum;
import StorePermissin.OriginalStoreManagerRole;
import StorePermissin.Permission;
import StorePermissin.StoreRoles;
import Views.ProductView;

import javax.naming.NoPermissionException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Store {
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
        StoreRoles = storeOriginalManager.stream().map(OriginalStoreManagerRole::new).collect(Collectors.toList());
        inventoryManager = new InventoryManager();
        forum =new Forum();
        storeState = StoreState.ACTIVE;
        storeRating = 0;
    }

    private boolean checkPermission(String userId, Permission action){
        for (StoreRoles roleUser :
                StoreRoles) {
            if (roleUser.getUserId().equals(userId) && roleUser.getPermissions().contains(action)) {
                return true;
            }
        }
        return false;
    }

    public void givePermissionTo(String userIdGiving,String UserGettingPermission,List<Permission> permissions) throws NoPermissionException {
        for (StoreRoles roleUser :
                StoreRoles) {
            if (roleUser.getUserId().equals(userIdGiving) ) {
                roleUser.createAnotherManager(UserGettingPermission,permissions);
                return;
            }
        }
        throw new NoPermissionException("the user is not manager");
    }
    private void removePermissionTo(List<String> RolesToRemove){
        for (String id :
                RolesToRemove) {
            for (int i = 0; i < StoreRoles.size(); i++) {
                if(IdGenerator.getInstance().isIdEqual(
                        StoreRoles.get(i).getUserId(),
                        id)
                ){
                    StoreRoles.remove(i);
                    break;
                }
            }


        }
    }
    public void removePermissionTo(String userIdRemoving,String UserAffectedId) throws NoPermissionException {
        for (StoreRoles roleUser : StoreRoles) {
            if (roleUser.getUserId().equals(userIdRemoving) ) {
                removePermissionTo( roleUser.removeManager(UserAffectedId));
                return;
            }
        }
        throw new NoPermissionException("the user is not manager");
    }

    public void editProductSupply( String userId, String productId, int newSupply, String newName, float newPrice ) throws NoPermissionException {
        if(!checkPermission(userId, Permission.EDIT_EXISTING_PRODUCT)){
            throw new NoPermissionException("the user don't have this permission");
        }
        inventoryManager.editProductSupply(productId, newSupply, newName, newPrice);
    }

    public String addNewProduct(String userId, String productName, float price, int howMuch) throws NoPermissionException {
        if(!checkPermission(userId, Permission.ADD_NEW_PRODUCT)){
            throw new NoPermissionException("the user don't have this permission");
        }
        return inventoryManager.addNewProduct(productName, price, howMuch);

    }


    public List<ProductView> getAllProducts() {
        return inventoryManager.getAllProducts();
    }



    public String getId() {
        return storeId;
    }

    public void addProductReview(String userId, String productId, String title, String body, float rating) {
        //TODO: check if user Bought this product
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
    public void postMessageToForum(String threadId, String userId, String message) throws NoPermissionException {
        if(!checkPermission(userId, Permission.REPLY_TO_FORUM)){
            throw new NoPermissionException("the user don't have this permission");
        }
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
}
