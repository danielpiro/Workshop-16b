package StorePermission;

import javax.naming.NoPermissionException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OriginalStoreOwnerRole extends StoreRoles{
    public OriginalStoreOwnerRole(String userId) {
        super(userId, new ArrayList<>());
        List<Permission> allPerm = Arrays.asList(Permission.values());
        storePermissions.addAll(allPerm);
    }

    public StoreOwnerRole createOwner(String userId, List<Permission> givePerm){
        for (Permission p :
                givePerm) {
            if (p == Permission.CLOSE_STORE  ||  p == Permission.OPEN_STORE) {
                throw new RuntimeException("only store manager can have permission to close store");
            }
        }
        StoreOwnerRole newManager = new StoreOwnerRole(userId,givePerm);
        createPermissionsTo.add(newManager);
        return newManager;
    }

    @Override
    public StoreManager createManager(String userId) throws NoPermissionException {
        StoreManager newStoreManager = new StoreManager(userId);
        createPermissionsTo.add(newStoreManager);
        return newStoreManager;
    }
}
