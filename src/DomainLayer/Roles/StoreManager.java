package DomainLayer.Roles;

import DomainLayer.Store.Store;
import DomainLayer.User.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class StoreManager extends StoreRole {

    private List<Permission> permissions;

    public StoreManager(Subscriber user, Store store){
        this.store = store;
        this.user = user;
        permissions = new ArrayList<>();
        permissions.add(Permission.VIEW_AND_RESPOND_TO_USERS);
        permissions.add(Permission.VIEW_STORE_HISTORY);
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public boolean havePermission (String permission) {
        for(Permission p: permissions){
            if(p.toString().equals(permission)) return true;
        }
        return false;
    }

}
