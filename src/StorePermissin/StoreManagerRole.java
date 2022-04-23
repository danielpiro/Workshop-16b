package StorePermissin;

import java.util.List;

public class StoreManagerRole extends StoreRoles{

    public StoreManagerRole(String userId, List<Permission> permissions) {
        super(userId, permissions);
    }


    public StoreManagerRole createAnotherManager(String userId,List<Permission> givePerm){
        for (Permission Perm: givePerm) {
            if(!storePermissions.contains(Perm)){
                throw new IllegalArgumentException("this manager don't have the permission he is giving");
            }
        }
        return new StoreManagerRole(userId, givePerm);
    }
}
