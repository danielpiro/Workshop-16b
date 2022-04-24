package StorePermission;

import java.util.Arrays;
import java.util.List;

public class OriginalStoreManagerRole extends StoreRoles{
    public OriginalStoreManagerRole(String userId) {
        super(userId, Arrays.asList(Permission.values()));
    }

    public StoreManagerRole createAnotherManager(String userId,List<Permission> givePerm){
        for (Permission p :
                givePerm) {
            if (p == Permission.CLOSE_STORE  ||  p == Permission.OPEN_STORE) {
                throw new RuntimeException("only store manager can have permission to close store");
            }
        }
        StoreManagerRole newManager = new StoreManagerRole(userId,givePerm);
        createPermissionsTo.add(newManager);
        return newManager;
    }
}
