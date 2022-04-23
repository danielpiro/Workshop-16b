package StorePermissin;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class  StoreRoles {
    private String userId ;
    protected List<Permission> storePermissions;
    protected List<StoreRoles> createPermissionsTo; //all the users the object gave permissions

    public StoreRoles(String userId,List<Permission> permissions){
        this.userId = userId;
        this.storePermissions = permissions;
    }

    public String getUserId() {
        return userId;
    }

    public List<Permission> getPermissions(){
        return Collections.unmodifiableList(storePermissions);
    }
}
