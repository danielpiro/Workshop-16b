package DomainLayer.User;

import DomainLayer.PurchaseProcess;
import DomainLayer.Roles.Role;
import DomainLayer.Roles.StoreRole;
import DomainLayer.User.User;

import java.util.ArrayList;
import java.util.List;

public class Subscriber extends User {

    private String name;
    private String password;
    private List<Role> role_list;
    private List<PurchaseProcess> purchaseProcesslist;
    private boolean logged_in = false;
    private List<String> Quries; //3.5

    public Subscriber(String user_name, String password) {
        super();
        this.password = password;
        this.name = user_name;
        role_list = new ArrayList<>();
        purchaseProcesslist = new ArrayList<>();
        Quries= new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public List<Role> getRole_list() {
        return role_list;
    }

    public List<PurchaseProcess> getPurchaseProcesslist() {
        return purchaseProcesslist;
    }

    public boolean isLogged_in() {
        return logged_in;
    }

    public void setLogged_in(boolean logged_in) {
        this.logged_in = logged_in;
    }


    public StoreRole get_role_at_store(String store_name){
        StoreRole role_to_return = null;
        for(Role role : role_list){
            if(role instanceof StoreRole && ((StoreRole) role).store.getName().equals(store_name)){
                role_to_return = (StoreRole) role;
            }
        }
        return role_to_return;
    }

    public List<String> getQuries() {
        return Quries;
    }
}
