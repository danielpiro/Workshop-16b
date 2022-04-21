package StorePermissin;

import java.util.List;

public class StoreManagerRole extends StoreRoles{

    public StoreManagerRole(User user, List<Permission> permissions) {
        super(user, permissions);
    }


    public StoreManagerRole createAnotherManager(User user,List<Permission> givePerm){
        for (Permission Perm: givePerm) {
            if(!storePermissions.contains(Perm)){
                throw new IllegalArgumentException("this manager don't have the permission he is giving");
            }
        }
        return new StoreManagerRole(user, givePerm);
    }
}
