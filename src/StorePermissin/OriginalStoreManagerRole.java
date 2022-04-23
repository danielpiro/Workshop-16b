package StorePermissin;

import java.util.Arrays;
import java.util.List;

public class OriginalStoreManagerRole extends StoreRoles{
    public OriginalStoreManagerRole(String userId) {
        super(userId, Arrays.asList(Permission.values()));
    }

    public StoreManagerRole createAnotherManager(String userId,List<Permission> givePerm){
        StoreManagerRole newManager = new StoreManagerRole(userId,givePerm);
        createPermissionsTo.add(newManager);
        return newManager;
    }
}
