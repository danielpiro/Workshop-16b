package Controllers;

import Store.Product;
import org.junit.jupiter.api.Test;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {
    Service service;
    BigController bg;

    public ServiceTest()
    {
        try {
            service = new Service();
            bg = new BigController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void lastProduct(){
        try {
            String userId1 = "user1";
            String userId2 = "user2";
            String userId3 = "user3";
            Future user1 = service.sign_up(userId1, "123");
            Future user2 = service.sign_up(userId2, "123");
            Future user3 = service.sign_up(userId3, "123");

            service.login(userId1, "123");
            service.login(userId2, "123");

            String storeId = bg.openNewStore(userId1, "store1");

            bg.addNewProductToStore(storeId, userId1, "fuck", 1F, 1, "Other");
            HashMap<String, List<Product>> storesAndProducts = bg.getAllProductsAndStores(userId1);

            bg.addProductFromCart(userId2,storesAndProducts.get(storeId).get(0).getId(),storeId,1,false);
            bg.addProductFromCart(userId3,storesAndProducts.get(storeId).get(0).getId(),storeId,1,false);

            


        } catch (NoPermissionException e) {
            e.printStackTrace();
        }


    }




}