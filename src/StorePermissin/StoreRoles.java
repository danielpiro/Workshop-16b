package StorePermissin;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class  StoreRoles {
    private User user ;
    protected List<Permission> storePermissions;
    protected List<StoreRoles> createPermissionsTo; //all the users the object gave permissions

    public StoreRoles(User user,List<Permission> permissions){
        this.user = user;
        this.storePermissions = permissions;
    }

    public int getUserId() {
        return user.getUserId();
    }

    public List<Permission> getPermissions(){
        return Collections.unmodifiableList(storePermissions);
    }
}
