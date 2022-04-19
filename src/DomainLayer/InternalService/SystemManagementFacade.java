package DomainLayer.InternalService;

import DomainLayer.*;
import DomainLayer.Roles.Permission;
import DomainLayer.Store.Store;
import DomainLayer.System;
import DomainLayer.User.Guest;
import DomainLayer.User.ProductReview;
import DomainLayer.User.Subscriber;
import Encryption.EncryptImp;

import java.util.ArrayList;
import java.util.List;

public class SystemManagementFacade implements InternalService {

    public static System system;



    public static void init_system() {
        system = System.getSystem();
    }

    public static boolean is_initialized() {
        return System.initialized;
    }

    /////////////// store methods///////////////////////////

    public static boolean find_store(String store_name) {
        Store store = system.get_store(store_name);
        return store != null;
    }

    public static Store get_store(String store_name) {

        return system.get_store(store_name);
    }

    public static List<Store> get_stores() {

        return system.getStore_list();
    }

    public static boolean buy(DealDetails dd){
        return system.getProductFinanceService().tryToBuy(dd);
    }

   /* public static boolean buy(DealDetails dd){
        return system.getProductFinanceService().tryToBuy(dd);
    }*/



    /////////////////guest methods/////////////////////

    public static Guest getGuest(int id){
        for(Guest g : system.getGuest_list()){
            if(g.getId()==id)
                return g;
        }
        return null;
    }

    public static Guest addGuest(){
        Guest guest=new Guest(system.getNextGuestId());
        system.getGuest_list().add(guest);
        system.increaseGuestId();
        return guest;
    }

    /////////////// subscriber methods//////////////////////

    public static boolean find_subscriber(String user_name) {
        Subscriber subscriber = system.get_subscriber(user_name);
        return subscriber != null;
    }

    public static Subscriber get_subscriber(String user_name) {
        if(user_name==null||user_name.isEmpty()){
            throw new IllegalArgumentException("invalid user name");
        }
        return system.get_subscriber(user_name);
    }

    public static void add_subscriber(String user_name, String password) {
        if(user_name==null||user_name.isEmpty()){
            throw new IllegalArgumentException("invalid user name");
        }
        if(password==null||password.isEmpty()){
            throw new IllegalArgumentException("invalid password");
        }
        for (Subscriber s : system.getUser_list()){
            if(s.getName().equals(user_name)){
                throw new IllegalArgumentException("subscriber name must be unique");
            }
        }
        Subscriber subscriber = new Subscriber(user_name, password);
        system.getUser_list().add(subscriber);
    }

    public static  List<PurchaseProcess> View_purchase(String user_name) { //3.7
        if(user_name==null||user_name.isEmpty()){
            throw new IllegalArgumentException("invalid user name");
        }
        Subscriber subscriber = system.get_subscriber(user_name);
        return subscriber.getPurchaseProcesslist();
    }

    public static void Add_Query(String user_name,String query) { //3.5  -- #TODO add test that the query inserted
        if(user_name==null||user_name.isEmpty()){
            throw new IllegalArgumentException("invalid user name");
        }
        Subscriber subscriber = system.get_subscriber(user_name);
        subscriber.getQuries().add(query);
    }
    /*
    public static boolean addProductReview(String user_name, String product_name, String store_name, String review_data, int rank) {
        Subscriber subscriber = system.get_subscriber(user_name);
        Product reviewedProduct = system.get_store(store_name).getProduct(product_name);
        List<PurchaseProcess> purchedlist = new ArrayList<>();
        ProductReview product_review;
        boolean isPurchased = false;
        ShoppingBag currentShoppingBag;
        if (subscriber != null && subscriber.isLogged_in() && reviewedProduct != null) {
            purchedlist = subscriber.getPurchaseProcesslist();
            for (PurchaseProcess pp : purchedlist) {
                currentShoppingBag = pp.getShoppingBag();
                for (String p : currentShoppingBag.getProducts_names())
                    if (p.equals(product_name) && pp.getStore().getName().equals(store_name)&&pp.isfinished()) {
                        isPurchased = true;
                        product_review = new ProductReview(subscriber,rank,review_data);
                        reviewedProduct.addReview(product_review);

                    }

            }

        }
        return isPurchased;
    }
*/

    ////////////////////////////////////////////////////////
    public static boolean check_password(String user_name, String password) {
        EncryptImp encryptImp = system.getEncryptImp();
        Subscriber subscriber = system.get_subscriber(user_name);
        String password_dyc = encryptImp.decrypt(subscriber.getPassword());
        return password_dyc.equals(encryptImp.decrypt(password));
    }

    public static Permission string_to_permission(String s) {
        Permission permission[] = Permission.values();
        for (Permission p : permission)
            if (s.equalsIgnoreCase(String.valueOf(p)))
                return p;


        return null;
    }

    public static List<Permission> strings_to_permissions(List<String> strings) {
        List<Permission> permissions = new ArrayList<>();
        ;
        for (String s : strings) {
            permissions.add(string_to_permission(s));
        }
        return permissions;


    }

    public static List<Product> get_products_of_store(String store_name) {
        List<Store> storeList = system.getStore_list();
        List<Product> productList = null;
        for (Store s : storeList) {
            if (s.getName().equals(store_name))
                productList = s.getProduct_list();
        }
        return productList;
    }
    public static List<Store> getAllStores(){
        return system.getStore_list();
    }

}
