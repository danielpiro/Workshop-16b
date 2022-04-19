package DomainLayer;

import DomainLayer.Store.Store;
import DomainLayer.User.User;

public class PurchaseProcess {

    private User user;
    private Store store;
    private ShoppingBag shoppingBag;

    public DealDetails getDetails() {
        return details;
    }

    public void setDetails(DealDetails details) {
        this.details = details;
    }

    private DealDetails details;

    public void setDone(boolean done) {
        isDone = done;
    }

    boolean isDone;

    public PurchaseProcess(User user, Store store, ShoppingBag shoppingBag) {
        this.user = user;
        this.store = store;
        this.shoppingBag = shoppingBag;
        this.isDone = false;

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public ShoppingBag getShoppingBag() {
        return shoppingBag;
    }

    public void setShoppingBag(ShoppingBag shoppingBag) {
        this.shoppingBag = shoppingBag;
    }

    public boolean isfinished(){
        return isDone;
    }
}
