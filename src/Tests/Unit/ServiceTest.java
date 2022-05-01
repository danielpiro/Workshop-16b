package Tests.Unit;

import Controllers.Service;
import ExternalConnections.Delivery.DeliveryNames;
import ExternalConnections.Payment.PaymentNames;
import GlobalSystemServices.Log;
import Store.Product;
import StorePermission.Permission;
import org.junit.jupiter.api.Test;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;

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
            service.sign_up(userId1, "123").get();
            service.sign_up(userId2, "123").get();
            service.sign_up(userId3, "123").get();

            service.login(userId1, "123").get();
            service.login(userId2, "123").get();
            service.login(userId3, "123").get();

            Future<String> storeId = service.openNewStore(userId1, "store1");

            service.addNewProductToStore(storeId.get(), userId1, "fuck", 1F, 1, "Other").get();
            Future<HashMap<String, List<Product>>> storesAndProducts = service.getAllProductsAndStores(userId1);
            String pID1 = storesAndProducts.get().get(storeId.get()).get(0).getId();

            service.addProductFromCart(userId2,pID1,storeId.get(),1,false).get();
            service.addProductFromCart(userId3,pID1,storeId.get(),1,false).get();

            Future per1 = service.purchaseCart(userId2, PaymentNames.Visa, DeliveryNames.FedEx);
            Future per2 = service.purchaseCart(userId3, PaymentNames.Visa, DeliveryNames.FedEx);


            float x= (float)per1.get();
            float x2= (float)per2.get();
            assertTrue( (float)per1.get()== -1 ^  (float)per2.get()== -1 );



        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    @Test
    void twoOwnersGivePermissionAtTheSameTime() {
        try {
            String userId1 = "user1";
            String userId2 = "user2";
            String userId3 = "user3";

            service.sign_up(userId1, "123").get();
            service.sign_up(userId2, "123").get();
            service.sign_up(userId3, "123").get();

            service.login(userId1, "123").get();
            service.login(userId2, "123").get();

            String storeId = (String) service.openNewStore(userId1, "store1").get();
            List<Permission> per = new ArrayList<>();
            per.add(Permission.VIEW_STORE_HISTORY);
            service.createOwner(storeId,userId1,userId2,per).get();

            boolean b1  = (boolean)service.createOwner(storeId,userId1,userId3,per).get();
            boolean b2  =  (boolean)service.createOwner(storeId,userId1,userId3,per).get();
            assertTrue(b1^b2);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    void ownerDeleteProductWhileCustomerBuyIt() {
        try {
            String userId1 = "user1";
            String userId2 = "user2";


            service.sign_up(userId1, "123").get();
            service.sign_up(userId2, "123").get();


            service.login(userId1, "123").get();
            service.login(userId2, "123").get();

            String storeId = (String) service.openNewStore(userId1, "store1").get();
            service.addNewProductToStore(storeId, userId1, "fuck", 1F, 1, "Other").get();
            Future<HashMap<String, List<Product>>> storesAndProducts = service.getAllProductsAndStores(userId1);
            String pID1 = storesAndProducts.get().get(storeId).get(0).getId();
            System.out.println(pID1);
            service.addProductFromCart(userId2,pID1,storeId,1,false).get();


            Future p1= service.purchaseCart(userId2, PaymentNames.Visa,DeliveryNames.FedEx);
            Future success = service.deleteProduct(storeId,userId1, pID1);

            assertTrue(((float)p1.get()==-1 && (boolean)success.get()) ^ ((float)p1.get()!=-1 && !(boolean)success.get())  );



        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    void onlyOneCanLogin() {

        try {
            String userId1 = "user1";
            String userId2 = "user2";



            service.sign_up(userId1, "123").get();
            service.sign_up(userId2, "123").get();



            service.login(userId1, "123").get();

            Future<Boolean>  future1= service.login(userId2, "123");
            Future<Boolean>  future2= service.login(userId2, "123");

            assertTrue( future1.get() ^  future2.get() );



        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }


}