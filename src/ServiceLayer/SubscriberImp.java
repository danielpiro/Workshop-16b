package ServiceLayer;

import DomainLayer.*;
import DomainLayer.InternalService.SubscribersManagementFacade;
import DomainLayer.InternalService.SystemManagementFacade;
import DomainLayer.Store.Store;
import DomainLayer.User.Subscriber;
import DomainLayer.Product;
import DomainLayer.PurchaseProcess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SubscriberImp implements ISubscriber {

    Log my_log = Log.getLogger();

    public SubscriberImp() throws IOException {
    }

    @Override
    public List<Product> view_products_information_store(String store_name) {
        my_log.logger.info("view_products_information_store");
        List<Product> products = null;
        if(!SystemManagementFacade.is_initialized()) return null;
        products = SystemManagementFacade.get_products_of_store(store_name);
        return products;
    }

    @Override
    public List<Product> search_products(String product_name) {
        my_log.logger.info("search_products");
        List<Product> products = new ArrayList<>();
        if(!SystemManagementFacade.is_initialized()) return null;
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
    public boolean save_products(String userName,String product_name, String store_name) {
        my_log.logger.info("save_products");
        if(!SystemManagementFacade.is_initialized())
            return false;
        Subscriber s= SystemManagementFacade.get_subscriber(userName);
        boolean processExist =false;
        Product product=null;
        Store store = SystemManagementFacade.get_store(store_name);
        if(store == null) return false;
        for(Product prod : store.getProduct_list()) {
            if (prod.getName().equals(product_name))
                product = prod;
        }
        for(PurchaseProcess p:s.getPurchaseProcesslist()){
            if(p.getStore().getName().equals(store_name)){
                p.getShoppingBag().getProducts_names().add(product_name);
                p.getShoppingBag().getProducts().add(product);
                processExist=true;
            }
        }
        if(!processExist){
            PurchaseProcess p=new PurchaseProcess(s, SystemManagementFacade.get_store(store_name),new ShoppingBag(new ArrayList<>()));
            s.getShoppingCart().getShopping_bag_list().add(p.getShoppingBag());
            p.getShoppingBag().getProducts_names().add(product_name);
            p.getShoppingBag().getProducts().add(product);
            s.getPurchaseProcesslist().add(p);
        }
        return true;
    }

    @Override
    public List<String> watch_products_in_cart(String userName) {
        my_log.logger.info("watch_products_in_cart");
        if(!SystemManagementFacade.is_initialized())
            return null;
        List<String> res= new ArrayList<>();
        if(!SystemManagementFacade.is_initialized()) return null;
        Subscriber s= SystemManagementFacade.get_subscriber(userName);

        ShoppingCart sc=s.getShoppingCart();
        for(ShoppingBag sb : sc.getShopping_bag_list()){
            res.addAll(sb.getProducts_names());
        }
        return res;
    }

    @Override
    public boolean buy_products_in_cart(String id, String buyerName, String creditCardNumber, String expireDate, int cvv, double discount) {
        my_log.logger.info("buy_products_in_cart");
        if(!SystemManagementFacade.is_initialized())
            return false;
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

        Subscriber g= SystemManagementFacade.get_subscriber(id);
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
        if (SystemManagementFacade.buy(dd)) for(PurchaseProcess pp: g.getPurchaseProcesslist()) pp.setDone(true);
        return true;
    }


    @Override
    public boolean sign_out(String user_name) {
        my_log.logger.info("sign_out");
        if(!SystemManagementFacade.is_initialized()) return false;

        if(SystemManagementFacade.find_subscriber(user_name) && SubscribersManagementFacade.check_if_logged_in(user_name)){
            SubscribersManagementFacade.subscriber_login_state(user_name,false);
            return true;
        }
        return false;
    }

    @Override
    public boolean open_store(String user_name, String store_name) {
        my_log.logger.info("open_store");
        if(!SystemManagementFacade.is_initialized()) return false;

        if(SystemManagementFacade.find_subscriber(user_name) && SubscribersManagementFacade.check_if_logged_in(user_name)){
            SubscribersManagementFacade.create_store(user_name,store_name);
            return true;
        }
        return false;
    }

    @Override
    public boolean write_review(String user_name, String product_name, String store_name, String review_data, int rank) {
        return false;
    }


    @Override
    public boolean rank_product() {
        if(!SystemManagementFacade.is_initialized()) return false;
        return false;
    }

    @Override
    public boolean rank_store() {
        if(!SystemManagementFacade.is_initialized()) return false;
        return false;
    }

    @Override
    public boolean send_query_to_store(String user_name,String Query) {//add test
        my_log.logger.info("send_query_to_store");
        if(!SystemManagementFacade.is_initialized())
            return false;
        if(SystemManagementFacade.find_subscriber(user_name) && SubscribersManagementFacade.check_if_logged_in(user_name)) {
            SystemManagementFacade.Add_Query(user_name, Query);
        }
        return true;
    }

    @Override
    public boolean fill_complaint() {
        if(!SystemManagementFacade.is_initialized()) return false;
        return false;
    }

    @Override
    public List<PurchaseProcess> view_purchase_history(String user_name) {
        my_log.logger.info("view_purchase_history");
        if(!SystemManagementFacade.is_initialized())
            return null;
        if(SystemManagementFacade.find_subscriber(user_name) && SubscribersManagementFacade.check_if_logged_in(user_name)){
            return SystemManagementFacade.View_purchase(user_name);
        }
        return null;
    }

    @Override
    public boolean edit_account() {
        if(!SystemManagementFacade.is_initialized()) return false;
        return false;
    }
}
