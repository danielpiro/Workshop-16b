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
                throw new IllegalArgumentException("this manager don't have the permission he is giving");
            }
        }
        return new StoreOwnerRole(userId, givePerm);
    }
    @Override
    public StoreManager createManager(String userId) throws NoPermissionException {
        StoreManager newStoreManager = new StoreManager(userId);
        createPermissionsTo.add(newStoreManager);
        return newStoreManager;
    }
}
