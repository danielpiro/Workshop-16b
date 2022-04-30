package Tests.StorePartUnitTests;

import Store.Store;
import StorePermission.Permission;
import StorePermission.StoreManager;
import StorePermission.StoreRoles;
import Store.Product;
import User.Subscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.NoPermissionException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StoreTest {
    String userId1 = "userId1";
    String userId2 = "userId2";
    String userId3 = "userId3";
    String userId4 = "userId4";
    String userId5 = "userId5";
    String userId6 = "userId6";
    Store store1 ;
    //Store store2 ;
    String productId1;
    String productId2;
    String productId3;


    @BeforeEach
    void setUp() {
        List<String> managers1 = new LinkedList<>();
        managers1.add(userId1);
        store1 =new Store("store1", "s1Id", managers1);
        //managers1.add(userId2);
        //store2 =new Store("store2", "s2Id", managers1);


        try {
            productId1 = store1.addNewProduct(userId1,"p1", 5.5F, 4, "Appliances");
            productId2 = store1.addNewProduct(userId1,"p2", 15.5F, 1, "Other");
            productId3 = store1.addNewProduct(userId1,"p3", 100F, 50, "Baby");
            assertEquals(store1.getAllProducts().size(), 3);
        } catch (NoPermissionException e) {
            e.printStackTrace();
        }

    }

    @Test
    void createManager() {
        try {
            store1.createManager(userId1, userId2);
            List<StoreRoles>  allRoles =store1.getInfoOnManagersOwners(userId1);
            for (StoreRoles sr: allRoles){
                if(sr.getUserId().equals(userId2)){
                    assertTrue(sr instanceof StoreManager);
                }
            }
            store1.getStoreHistory(userId2);
            try{
                store1.createManager(userId2, userId3);
                fail();
            }
            catch (Exception e){
                e.printStackTrace();
            }

        } catch (NoPermissionException e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    void createOwnerBad() {
        try {
            store1.createOwner(userId2, userId3, new ArrayList<>());
            fail();
        } catch (NoPermissionException e) {
            e.printStackTrace();
        }
    }
    @Test
    void createOwnerBadCircle() {
        try {
            store1.createOwner(userId1, userId1, new ArrayList<>());
            fail();
        } catch (NoPermissionException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createOwnerGood() {
        try {
            List<Permission> per = new ArrayList<>();
            per.add(Permission.ADD_NEW_PRODUCT);
            per.add(Permission.EDIT_PRODUCT);
            per.add(Permission.REMOVE_PRODUCT);

            store1.createOwner(userId1, userId2, per);

            store1.deleteProduct(userId2,productId1);
            assertEquals(store1.getAllProducts().size(), 2);

            productId1 = store1.addNewProduct(userId2,"p1", 5.5F, 4, "Appliances");
            assertEquals(store1.getAllProducts().size(), 3);

            List<Permission> per2 = new ArrayList<>();
            per.add(Permission.ADD_NEW_PRODUCT);
            per.add(Permission.EDIT_PRODUCT);

            store1.createOwner(userId2, userId3, per2);
            assertEquals(store1.getInfoOnManagersOwners(userId1).size(), 3);

            try{
                store1.createManager(userId3, userId1);
                fail();
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                store1.createOwner(userId3, userId1,per2);
                fail();
            }catch (Exception e){
                e.printStackTrace();
            }


        } catch (NoPermissionException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void removePermissionTo() {
        try {
            List<Permission> per = new ArrayList<>();
            per.add(Permission.ADD_NEW_PRODUCT);
            per.add(Permission.EDIT_PRODUCT);
            per.add(Permission.REMOVE_PRODUCT);
            per.add(Permission.EDIT_STORE_POLICY);
            per.add(Permission.VIEW_FORUM);

            store1.createOwner(userId1, userId2, per);
            store1.createOwner(userId2, userId3, per);
            store1.createOwner(userId2, userId4, per);
            store1.createOwner(userId3, userId5, per);
            store1.createManager(userId4, userId6);

            store1.removePermissionTo(userId1,userId2);
            assertEquals(store1.getInfoOnManagersOwners(userId1).size(),1);

        } catch (NoPermissionException e) {
            e.printStackTrace();
        }

    }
    @Test
    void removePermissionTo2() {
        try {
            List<Permission> per = new ArrayList<>();
            per.add(Permission.ADD_NEW_PRODUCT);
            per.add(Permission.EDIT_PRODUCT);
            per.add(Permission.REMOVE_PRODUCT);
            per.add(Permission.EDIT_STORE_POLICY);
            per.add(Permission.VIEW_FORUM);

            store1.createOwner(userId1, userId2, per);
            store1.createOwner(userId2, userId3, per);
            store1.createOwner(userId2, userId4, per);
            store1.createOwner(userId3, userId5, per);
            store1.createManager(userId4, userId6);

            store1.removePermissionTo(userId2,userId4);
            assertEquals(store1.getInfoOnManagersOwners(userId1).size(),4);

        } catch (NoPermissionException e) {
            e.printStackTrace();
        }

    }






    @Test
    void addNewThreadToForum() {
        store1.addNewThreadToForum("test", userId3);
        assertTrue(store1.getThread(userId3).getUserId().equals(userId3));
    }

    @Test
    void postMessageToForum() {
        String threadId=store1.addNewThreadToForum("test", userId3);
        try {
            store1.userPostMessageToForum(threadId,userId3,"good products");
            assertEquals(store1.getThread(userId3).getTitle(),"test");

        } catch (NoPermissionException e) {
            e.printStackTrace();
            fail();
        }
    }


}