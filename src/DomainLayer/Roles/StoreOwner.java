package DomainLayer.Roles;


import DomainLayer.Store.Store;
import DomainLayer.User.Subscriber;

public class StoreOwner extends StoreRole {

    public StoreOwner(Subscriber user, Store store){
        this.user = user;
        this.store = store;
    }


}
