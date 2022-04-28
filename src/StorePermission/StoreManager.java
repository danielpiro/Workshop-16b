package StorePermission;

import javax.naming.NoPermissionException;
import java.util.ArrayList;
import java.util.List;

public class StoreManager extends StoreRoles {
    public StoreManager(String userId) {
        super(userId, new ArrayList<>());
        storePermissions.add(Permission.VIEW_STORE_HISTORY);
        storePermissions.add(Permission.REPLY_TO_FORUM);
        storePermissions.add(Permission.VIEW_FORUM);
    }

    @Override
    public StoreOwnerRole createOwner(String userId, List<Permission> givePerm) throws NoPermissionException {
        throw new NoPermissionException("manager cant give permissions");
    }

    @Override
    public StoreManager createManager(String userId) throws NoPermissionException {
        throw new NoPermissionException("manager cant give permissions");
    }
}
