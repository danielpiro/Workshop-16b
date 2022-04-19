package ServiceLayer;
import DomainLayer.InternalService.SubscribersManagementFacade;
import DomainLayer.InternalService.SystemManagementFacade;

import java.io.IOException;
import java.util.ArrayList;


public class StoreRoles implements IStoreRole {

//    private String user_name, store_name;
//    private String product_name;
//    private String product_price;
//    private String product_amount;

    Log my_log = Log.getLogger();

    public StoreRoles() throws IOException {
    }


    @Override
    public boolean add_store_product(String user_name, String store_name, String product_name, int product_price, int product_amount) {
        my_log.logger.info("add product to store");
        if (!SystemManagementFacade.is_initialized() || product_amount == 0) return false;
        if (SystemManagementFacade.find_subscriber(user_name) && SubscribersManagementFacade.check_if_logged_in(user_name)) {
            return SubscribersManagementFacade.add_product_to_store(user_name, store_name, product_name, product_price, product_amount);
        }
        return false;
    }

    @Override
    public boolean edit_store_product(String user_name, String store_name, String product_name,String new_product_name, int product_price, int product_amount) {
        my_log.logger.info("edit_store_product");
        if (!SystemManagementFacade.is_initialized() || product_amount == 0) return false;
        if (SystemManagementFacade.find_subscriber(user_name) && SubscribersManagementFacade.check_if_logged_in(user_name)) {
            return SubscribersManagementFacade.change_product_in_store(user_name, store_name, product_name,new_product_name, product_price, product_amount);
        }
        return false;
    }

    @Override
    public boolean remove_store_product(String user_name, String store_name, String product_name) {
        my_log.logger.info("remove_store_product");
        if (!SystemManagementFacade.is_initialized())  return false;
        if (SystemManagementFacade.find_subscriber(user_name) && SubscribersManagementFacade.check_if_logged_in(user_name)) {
            return SubscribersManagementFacade.remove_product_in_store(user_name, store_name, product_name);
        }
        else
            return false;
    }

    @Override
    public boolean edit_store_policy(String user_name, String store_name) {
        //if (!SystemManage_Facade.is_initialized()) return false;
        return false;
    }

    @Override
    public boolean assign_store_owner(String user_name, String store_name, String user_assign) {
        my_log.logger.info("assign_store_owner");
        if (!SystemManagementFacade.is_initialized()) return false;
        if (SystemManagementFacade.find_subscriber(user_name) && SubscribersManagementFacade.check_if_logged_in(user_name)) {
            return SubscribersManagementFacade.add_owner_to_store(user_name, store_name, user_assign);
        }
        return false;
    }

    @Override
    public boolean remove_store_owner(String user_name, String store_name, String user_assign) {
        my_log.logger.info("remove_store_owner");
        if (!SystemManagementFacade.is_initialized()) return false;
        if (SystemManagementFacade.find_subscriber(user_name) && SubscribersManagementFacade.check_if_logged_in(user_name)) {
            return SubscribersManagementFacade.remove_owner_from_store(user_name, store_name, user_assign);
        }
        return false;
    }

    @Override
    public boolean assign_store_manager(String user_name, String store_name, String user_assign) {
        my_log.logger.info("assign_store_manager");
        if (!SystemManagementFacade.is_initialized()) return false;
        if (SystemManagementFacade.find_subscriber(user_name) && SubscribersManagementFacade.check_if_logged_in(user_name)) {
            return SubscribersManagementFacade.add_manager_to_store(user_name, store_name, user_assign);
        }
        return false;
    }



    @Override
    public boolean edit_manager_permissions(String user_name, String store_name,String user_assign , ArrayList<String> permissions) {
        my_log.logger.info("edit_manager_permissions");
        if (!SystemManagementFacade.is_initialized()) return false;
        if (SystemManagementFacade.find_subscriber(user_name) && SystemManagementFacade.find_store(store_name) && SubscribersManagementFacade.check_if_logged_in(user_name)) {
            return SubscribersManagementFacade.change_permissions_of_manager(user_name, store_name,user_assign , permissions);
        }
        return false;
    }


    @Override
    public boolean remove_store_manager(String user_name, String store_name, String user_assign) {
        my_log.logger.info("remove_store_manager");
        if (!SystemManagementFacade.is_initialized()) return false;
        if (SystemManagementFacade.find_subscriber(user_name) && SubscribersManagementFacade.check_if_logged_in(user_name)) {
            return SubscribersManagementFacade.remove_manager_from_store(user_name, store_name, user_assign);
        }
        return false;
    }

    @Override
    public boolean close_store(String user_name, String store_name) {
        my_log.logger.info("close_store");
        if (!SystemManagementFacade.is_initialized()) return false;
        return false;
    }

    @Override
    public boolean view_and_respond_to_questions() {
        if (!SystemManagementFacade.is_initialized()) return false;
        return false;
    }


    @Override
    public String watch_store_history(String user_name, String store_name) {
        my_log.logger.info("watch_store_history");
        if (!SystemManagementFacade.is_initialized()) return null;
        if (SystemManagementFacade.find_subscriber(user_name) && SystemManagementFacade.find_store(store_name) && SubscribersManagementFacade.check_if_logged_in(user_name) ) {
            return SubscribersManagementFacade.store_purchase_history(user_name, store_name);
        }
        return null;
    }

}
