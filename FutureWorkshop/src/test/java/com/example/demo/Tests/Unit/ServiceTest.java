//package com.example.demo.Tests.Unit;
//
//
//import com.example.demo.Controllers.BigController;
//import com.example.demo.CustomExceptions.Exception.StorePolicyViolatedException;
//import com.example.demo.CustomExceptions.Exception.SupplyManagementException;
//import com.example.demo.CustomExceptions.Exception.UserException;
//import com.example.demo.CustomExceptions.ExceptionHandler.ReturnValue;
//import com.example.demo.ExternalConnections.Delivery.DeliveryNames;
//import com.example.demo.ExternalConnections.Payment.PaymentNames;
//import com.example.demo.Mock.MockFullProduct;
//import com.example.demo.Mock.MockSmallProduct;
//import com.example.demo.Store.Product;
//import com.example.demo.StorePermission.Permission;
//import lombok.SneakyThrows;
//import org.junit.jupiter.api.Test;
//
//import javax.naming.NoPermissionException;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//import java.util.logging.Level;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class ServiceTest {
//    BigController service;
//    private ExecutorService executorService;
//
//    public ServiceTest()
//    {
//        try {
//            service = new BigController();
//            this.executorService = Executors.newFixedThreadPool(10);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (SupplyManagementException e) {
//            throw new RuntimeException(e);
//        } catch (NoPermissionException e) {
//            throw new RuntimeException(e);
//        } catch (UserException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void lastProduct(){
//        try {
//            String userId1 = "user1";
//            String userId2 = "user2";
//            String userId3 = "user3";
//            Runnable runnable1 = () -> {
//                try {
//                    service.signup(userId1, "123");
//                } catch (UserException e) {
//                    throw new RuntimeException(e);
//                }
//            };
//            Runnable runnable2 = () -> {
//                try {
//                    service.signup(userId2, "123");
//                } catch (UserException e) {
//                    throw new RuntimeException(e);
//                }
//            };
//            Runnable runnable3 = () -> {
//                try {
//                    service.signup(userId3, "123");
//                } catch (UserException e) {
//                    throw new RuntimeException(e);
//                }
//            };
//            executorService.submit(runnable1);
//            executorService.submit(runnable2);
//            executorService.submit(runnable3);
//            runnable1 = () -> {
//                try {
//                    service.login(userId1, "123");
//                } catch (UserException e) {
//                    throw new RuntimeException(e);
//                }
//            };
//            runnable2 = () -> {
//                try {
//                    service.login(userId2, "123");
//                } catch (UserException e) {
//                    throw new RuntimeException(e);
//                }
//            };
//            runnable3 = () -> {
//                try {
//                    service.login(userId3, "123");
//                } catch (UserException e) {
//                    throw new RuntimeException(e);
//                }
//            };
//            executorService.submit(runnable1);
//            executorService.submit(runnable2);
//            executorService.submit(runnable3);
//
//
//
//            Future<String> storeId = executorService.submit(() -> {
//                try {
//                    ReturnValue store1 = service.openNewStore(userId1, "store1");
//                    return (String)store1.getValue();
//                } catch (UserException | NoPermissionException e) {
//                    throw new RuntimeException(e);
//                }
//            });
//
//
//            executorService.submit(() -> {
//                try {
//                    service.addNewProductToStore(new MockFullProduct(storeId.get(), userId1, "fggt", 1F, 1, "Other"));
//                } catch (UserException | NoPermissionException | InterruptedException | ExecutionException |
//                         SupplyManagementException e) {
//                    throw new RuntimeException(e);
//                }
//            }).get();
//
//            HashMap<String, List<Product>> storesAndProducts = service.getAllProductsAndStores();
//            String pID1 = storesAndProducts.get(storeId.get()).get(0).getId();
//
//            service.addProductFromCart(new MockSmallProduct(userId2,pID1,storeId.get(),1),false);
//            service.addProductFromCart(new MockSmallProduct(userId3,pID1,storeId.get(),1),false);
//
//
//
//            runnable1 = () -> {
//                try {
//                    service.purchaseCart(userId2, PaymentNames.Visa, DeliveryNames.FedEx);
//                } catch (UserException | SupplyManagementException | StorePolicyViolatedException e) {
//                    throw new RuntimeException(e);
//                }
//            };
//            runnable2 = () -> {
//                try {
//                    service.purchaseCart(userId3, PaymentNames.Visa, DeliveryNames.FedEx);
//                } catch (UserException | SupplyManagementException | StorePolicyViolatedException e) {
//                    throw new RuntimeException(e);
//                }
//            };
//            Future per1 = executorService.submit(runnable1);
//            Future per2 = executorService.submit(runnable2);
//            float x= (float)((ReturnValue)per1.get()).getValue();
//            float x2= (float)((ReturnValue)per2.get()).getValue();
//            assertTrue( (float)per1.get()== -1 ^  (float)per2.get()== -1 );
//
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail();
//        }
//    }
//    @Test
//    void twoOwnersGivePermissionAtTheSameTime() {
//        try {
//            String userId1 = "user1";
//            String userId2 = "user2";
//            String userId3 = "user3";
//
//            service.sign_up(userId1, "123").get();
//            service.sign_up(userId2, "123").get();
//            service.sign_up(userId3, "123").get();
//
//            service.login(userId1, "123").get();
//            service.login(userId2, "123").get();
//
//            String storeId = (String) service.openNewStore(userId1, "store1").get();
//            List<Permission> per = new ArrayList<>();
//            per.add(Permission.VIEW_STORE_HISTORY);
//            service.createOwner(storeId,userId1,userId2,per).get();
//
//            boolean b1  = (boolean)service.createOwner(storeId,userId1,userId3,per).get();
//            boolean b2  =  (boolean)service.createOwner(storeId,userId1,userId3,per).get();
//            assertTrue(b1^b2);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail();
//        }
//
//    }
//
//    @Test
//    void ownerDeleteProductWhileCustomerBuyIt() {
//        try {
//            String userId1 = "user1";
//            String userId2 = "user2";
//
//
//            service.sign_up(userId1, "123").get();
//            service.sign_up(userId2, "123").get();
//
//
//            service.login(userId1, "123").get();
//            service.login(userId2, "123").get();
//
//            String storeId = (String) service.openNewStore(userId1, "store1").get();
//            service.addNewProductToStore(storeId, userId1, "fuck", 1F, 1, "Other").get();
//            Future<HashMap<String, List<Product>>> storesAndProducts = service.getAllProductsAndStores(userId1);
//            String pID1 = storesAndProducts.get().get(storeId).get(0).getId();
//            System.out.println(pID1);
//            service.addProductFromCart(userId2,pID1,storeId,1,false).get();
//
//
//            Future p1= service.purchaseCart(userId2, PaymentNames.Visa,DeliveryNames.FedEx);
//            Future success = service.deleteProduct(storeId,userId1, pID1);
//
//            assertTrue(((float)p1.get()==-1 && (boolean)success.get()) ^ ((float)p1.get()!=-1 && !(boolean)success.get())  );
//
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail();
//        }
//
//    }
//
//    @Test
//    void onlyOneCanLogin() {
//
//        try {
//            String userId1 = "user1";
//            String userId2 = "user2";
//
//
//
//            service.sign_up(userId1, "123").get();
//            service.sign_up(userId2, "123").get();
//
//
//
//            service.login(userId1, "123").get();
//
//            Future<Boolean>  future1= service.login(userId2, "123");
//            Future<Boolean>  future2= service.login(userId2, "123");
//
//            assertTrue( future1.get() ^  future2.get() );
//
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//            fail();
//        }
//    }


//}