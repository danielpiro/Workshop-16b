package StorePermissin;

import java.util.Arrays;
import java.util.List;

public class OriginalStoreManagerRole extends StoreRoles{
    public OriginalStoreManagerRole(User user) {
        super(user, Arrays.asList(Permission.values()));
    }

    public StoreManagerRole createAnotherManager(User user,List<Permission> givePerm){
        StoreManagerRole newManager = new StoreManagerRole(user,givePerm);
        createPermissionsTo.add(newManager);
        return newManager;
    }
}
