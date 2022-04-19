package DomainLayer.Roles;

import DomainLayer.InternalService.SystemManagementFacade;
import DomainLayer.PurchaseProcess;
import DomainLayer.Store.Store;
import DomainLayer.User.Subscriber;

import java.util.ArrayList;
import java.util.List;


public class SystemManager extends Role {

    public SystemManager(Subscriber user){
        this.user = user;
    }
   public List<PurchaseProcess> allpurchases() {
        List<PurchaseProcess> all_purchases_processes = new ArrayList<>();
        List<Store> all_stores = SystemManagementFacade.getAllStores();
        for(Store s :all_stores){
            for(PurchaseProcess pp : s.getPurchase_process_list())
                if(pp.isfinished())
                    all_purchases_processes.add(pp);
        }
        return all_purchases_processes;
    }

}
