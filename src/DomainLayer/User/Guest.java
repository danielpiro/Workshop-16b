package DomainLayer.User;

import DomainLayer.PurchaseProcess;
import DomainLayer.User.User;

import java.util.ArrayList;
import java.util.List;

public class Guest extends User {


    private int id;
    private List<PurchaseProcess> purchaseProcesslist;

    public Guest(int id) {
        super();
        this.id=id;
        purchaseProcesslist = new ArrayList<>();

    }


    public int getId() {
        return id;
    }

    public List<PurchaseProcess> getPurchaseProcesslist() {
        return purchaseProcesslist;
    }


}
