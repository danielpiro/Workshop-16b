package StorePermission;

import javax.naming.NoPermissionException;
import java.util.List;

public class StoreOwnerRole extends StoreRoles{

    public StoreOwnerRole(String userId, List<Permission> permissions) {
        super(userId, permissions);
    }


    public StoreOwnerRole createOwner(String userId, List<Permission> givePerm){
        for (Permission Perm: givePerm) {
            if(!storePermissions.contains(Perm)){
                throw new IllegalArgumentException("this owner"+ userId +" don't have the permission he is giving");
            }
        }
        StoreOwnerRole newOwner = new StoreOwnerRole(userId, givePerm);
        createPermissionsTo.add(newOwner);
        return newOwner;
    }
    @Override
    public StoreManager createManager(String userId) throws NoPermissionException {
        StoreManager newStoreManager = new StoreManager(userId);
        createPermissionsTo.add(newStoreManager);
        return newStoreManager;
    }

    @Override
    public String getTitle() {
        return "owner";
    }
}
