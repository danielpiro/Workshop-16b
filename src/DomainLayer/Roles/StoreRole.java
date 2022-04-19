package DomainLayer.Roles;

import DomainLayer.Store.Store;

import java.util.ArrayList;
import java.util.List;


public abstract class StoreRole extends Role {
    public Store store;
    private List<Role> assigned_users = new ArrayList<>();
    private Role assigned_by = null;

    public List<Role> getAssigned_users() {
        return assigned_users;
    }

    public void setAssigned_users(List<Role> assigned_users) {
        this.assigned_users = assigned_users;
    }

    public Role getAssigned_by() {
        return assigned_by;
    }

    public void setAssigned_by(Role assigned_by) {
        this.assigned_by = assigned_by;
    }





}
