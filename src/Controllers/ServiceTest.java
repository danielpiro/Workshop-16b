package Controllers;

import ExternalConnections.Delivery.DeliveryNames;
import ExternalConnections.Payment.PaymentNames;
import Store.Product;
import org.junit.jupiter.api.Test;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Future;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServiceTest {
    Service service;


    public ServiceTest()
    {
        try {
            service = new Service();

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
            service.login(userId3, "123");

            Future<String> storeId = service.openNewStore(userId1, "store1");

            service.addNewProductToStore(storeId.get(), userId1, "fuck", 1F, 1, "Other").get();
            Future<HashMap<String, List<Product>>> storesAndProducts = service.getAllProductsAndStores(userId1);

            service.addProductFromCart(userId2,storesAndProducts.get().get(storeId).get(0).getId(),storeId.get(),1,false).get();
            service.addProductFromCart(userId3,storesAndProducts.get().get(storeId).get(0).getId(),storeId.get(),1,false).get();

            Future per1 = service.purchaseCart(userId1, PaymentNames.Visa, DeliveryNames.FedEx);
            Future per2 = service.purchaseCart(userId2, PaymentNames.Visa, DeliveryNames.FedEx);

            assertTrue((float)per1.get()== -1 ||  (float)per2.get()== -1 );



        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }


    }




}