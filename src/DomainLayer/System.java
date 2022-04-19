package DomainLayer;

import DomainLayer.ExternalService.ProductFinanceService;
import DomainLayer.ExternalService.ProductSupplyService;
import DomainLayer.Roles.SystemManager;
import DomainLayer.Store.Store;
import DomainLayer.User.Guest;
import DomainLayer.User.Subscriber;
import Encryption.EncryptImp;

import java.util.ArrayList;
import java.util.List;

public class System {

    private static System SYSTEM_SINGLETON = null;

    private List<Subscriber> user_list;
    private List<Guest> guest_list;
    private int nextGuestId;
    private List<Store> store_list;
    public static boolean initialized = false;
    private EncryptImp encryptImp;
    private ProductSupplyService productSupplyService;
    private ProductFinanceService productFinanceService;
    private System(){
        user_list = new ArrayList<>();
        guest_list=new ArrayList<>();
        store_list = new ArrayList<>();

        initialized = true;
        Subscriber admin = new Subscriber("Admin","Password");
        SystemManager systemManger = new SystemManager(admin);
        user_list.add(admin);

        encryptImp = new EncryptImp();
        encryptImp.connect();
        nextGuestId=0;
    }


    public static System getSystem() {
        if(SYSTEM_SINGLETON == null) SYSTEM_SINGLETON = new System();
        return SYSTEM_SINGLETON;
    }

    public List<Subscriber> getUser_list() {
        return user_list;
    }


    public List<Store> getStore_list() { return store_list; }

    public void add_subscriber(Subscriber s){
        user_list.add(s);
    }

    public Subscriber get_subscriber(String user_name){
        Subscriber subscriber = null;
        for (Subscriber user : user_list){
            if(user.getName().equals(user_name)) subscriber = user;
        }
        return subscriber;
    }
    public Store get_store(String store_name){
        Store store = null;
        for (Store s : store_list){
            if(s.getName().equals(store_name)) store = s;
        }
        return store;
    }

    public ProductSupplyService getProductSupplyService() {
        return productSupplyService;
    }

    public void setProductSupplyService(ProductSupplyService productSupplyService) {
        this.productSupplyService = productSupplyService;
    }

    public ProductFinanceService getProductFinanceService() {
        return productFinanceService;
    }

    public void setProductFinanceService(ProductFinanceService productFinanceService) {
        this.productFinanceService = productFinanceService;
    }

    public EncryptImp getEncryptImp() {
        return encryptImp;
    }

    public List<Guest> getGuest_list() {
        return guest_list;
    }

    public int getNextGuestId() {
        return nextGuestId;
    }

    public void increaseGuestId(){
        this.nextGuestId++;
    }

    public void clearSystem(){
        SYSTEM_SINGLETON = null;
    }
}
