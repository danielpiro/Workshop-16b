package ServiceLayer;

import DomainLayer.*;
import DomainLayer.InternalService.SubscribersManagementFacade;
import DomainLayer.InternalService.SystemManagementFacade;
import DomainLayer.Store.Store;
import DomainLayer.User.Guest;
import Encryption.EncryptImp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GuestImp implements IGuest {

    Log my_log = Log.getLogger();

    public GuestImp() throws IOException {
    }

    @Override
    public boolean sign_up(String user_name, String password) {
        my_log.logger.info("Sign Up");
        if(!SystemManagementFacade.is_initialized()) {
            my_log.logger.warning("System not initialized");
            return false;
        }
        EncryptImp encryption = new EncryptImp();
        if(!encryption.connect()) {
            my_log.logger.warning("System not initialized");
            return false;
        }
        password = encryption.encrypt(password); // for security
        if(!SystemManagementFacade.find_subscriber(user_name)) {
            SystemManagementFacade.add_subscriber(user_name, password);
            return true;
        }
        my_log.logger.severe("Action Failed");
        return false;
    }

    @Override
    public boolean login(String user_name, String password) {
        my_log.logger.info("Login");

        if(user_name.equals("Admin") && password.equals("Password")) SystemManagementFacade.init_system();
        if(!SystemManagementFacade.is_initialized()) {
            my_log.logger.warning("System not initialized");
            return false;

        }
        if(SystemManagementFacade.find_subscriber(user_name) && SystemManagementFacade.check_password(user_name,password)){
            SubscribersManagementFacade.subscriber_login_state(user_name,true);
            return true;
        }
        my_log.logger.severe("Action Failed");
        return false;
    }

    @Override
    public List<Product> view_products_information_store(String store_name) {
        my_log.logger.info("view_products_information_store");
        if(!SystemManagementFacade.is_initialized()) {
            my_log.logger.warning("System not initialized");
            return null;
        }

        List<Product> products = null;
        if(!SystemManagementFacade.is_initialized()) return null;
        products = SystemManagementFacade.get_products_of_store(store_name);
        return products;
    }

    @Override
    public List<Product> search_products(String product_name) {
        my_log.logger.info("search_products");
        if(!SystemManagementFacade.is_initialized()) {
            my_log.logger.warning("System not initialized");
            return null;
        }

        List<Product> products = new ArrayList<>();
        List<Store> stores= SystemManagementFacade.get_stores();
        for(Store store : stores){
            for(Product p : store.getProduct_list()){
                if(p.getName().equals(product_name))
                    products.add(p);
            }
        }
        return products;

    }

    @Override
    public boolean save_products(int id,String product_name, String store_name) {
        my_log.logger.info("save_products");
        if(!SystemManagementFacade.is_initialized()) {
            my_log.logger.warning("System not initialized");
            return false;
        }

        Guest g= SystemManagementFacade.getGuest(id);
        if(g==null)
            g= SystemManagementFacade.addGuest();
        boolean processExist=false;
        Product product=null;
        Store s = SystemManagementFacade.get_store(store_name);

        for(Product prod: s.getProduct_list()){
            if(prod.getName().equals(product_name)){
                product=prod;
            }
        }
        for(PurchaseProcess p:g.getPurchaseProcesslist()){
            if(p.getStore().getName().equals(store_name)){
                p.getShoppingBag().getProducts_names().add(product_name);
                p.getShoppingBag().getProducts().add(product);
                processExist=true;
            }
        }
        if(!processExist){
            PurchaseProcess p=new PurchaseProcess(g, SystemManagementFacade.get_store(store_name),new ShoppingBag(new ArrayList<>()));
            g.getShoppingCart().getShopping_bag_list().add(p.getShoppingBag());
            p.getShoppingBag().getProducts_names().add(product_name);
            p.getShoppingBag().getProducts().add(product);

        }
        return true;
    }

    @Override
    public List<String> watch_products_in_cart(int id) {
        my_log.logger.info("watch_products_in_cart");
        if(!SystemManagementFacade.is_initialized()) {
            my_log.logger.warning("System not initialized");
            return null;
        }

        List<String> res= new ArrayList<>();
        Guest g= SystemManagementFacade.getGuest(id);
        assert g != null;
        ShoppingCart sc=g.getShoppingCart();
        for(ShoppingBag sb : sc.getShopping_bag_list()){
            res.addAll(sb.getProducts_names());
        }
        return res;
    }

    @Override
    public boolean buy_products_in_cart(int id,String buyerName,String creditCardNumber,String expireDate,int cvv,double discount){
        my_log.logger.info("buy_products_in_cart");
        if(!SystemManagementFacade.is_initialized()) {
            my_log.logger.warning("System not initialized");
            return false;
        }
        if(discount > 1 || discount < 0){
            return false;
        }
        if(expireDate.length() != 5){
            return false;
        }
        if(creditCardNumber.length()!=16)
            return false;
        if(cvv>=1000)
            return false;
        Guest g= SystemManagementFacade.getGuest(id);
        if(g==null)
            return false;
        double price=0;
        for(PurchaseProcess pp : g.getPurchaseProcesslist()){
            for(Product prod : pp.getShoppingBag().getProducts()){
                price+=prod.getPrice();
            }
        }
        price= price*discount;

        DealDetails dd =new DealDetails(price,buyerName,creditCardNumber,expireDate,cvv);
        return SystemManagementFacade.buy(dd);
    }
}
