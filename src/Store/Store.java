package Store;

import StorePermissin.OriginalStoreManagerRole;
import StorePermissin.Permission;
import StorePermissin.StoreRoles;
import StorePermissin.User;
import Views.ProductView;
import Views.StoreView;

import javax.naming.NoPermissionException;
import java.util.List;
import java.util.stream.Collectors;

public class Store {
    private String storeName;
    private final String storeId;
    private List<StoreRoles> StoreRoles;

    private InventoryManager inventoryManager;
    private Forum forum;
    private StoreState storeState;


    public Store(String storeName, String storeId, List<User> storeOriginalManager) {
        this.storeName = storeName;
        this.storeId = storeId;
        StoreRoles = storeOriginalManager.stream().map(OriginalStoreManagerRole::new).collect(Collectors.toList());
        inventoryManager = new InventoryManager();
        forum =new Forum();
        storeState = StoreState.ACTIVE;
    }

    private boolean checkPermission(User user, Permission action){
        for (StoreRoles roleUser :
                StoreRoles) {
            if (roleUser.getUserId() == user.getUserId() && roleUser.getPermissions().contains(action)) {
                return true;
            }
        }
        return false;
    }

    public void addToExistingProduct(User user,String productId, int howMach) throws NoPermissionException {
        if(!checkPermission(user, Permission.ADD_EXISTING_PRODUCT)){
            throw new NoPermissionException("the user don't have this permission");
        }
        inventoryManager.addToExistingProduct(productId, howMach);
    }

    public void addNewProduct(User user, String productName, float price, int howMuch) throws NoPermissionException {
        if(!checkPermission(user, Permission.ADD_NEW_PRODUCT)){
            throw new NoPermissionException("the user don't have this permission");
        }
        inventoryManager.addNewProduct(productName, price, howMuch);

    }


    public List<ProductView> getAllProducts() {
        return inventoryManager.getAllProducts();
    }



    public String getId() {
        return storeId;
    }
}
