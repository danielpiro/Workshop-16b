package com.example.demo.Tests.Unit.StorePartUnitTests;


import com.example.demo.Store.Store;
import com.example.demo.StorePermission.Permission;
import com.example.demo.StorePermission.StoreManager;
import com.example.demo.StorePermission.StoreRoles;
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
        } catch (NoPermissionException | SupplyManagementException e) {
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


        } catch (NoPermissionException | SupplyManagementException e) {
            e.printStackTrace();
            fail();
        }
    }
    private void setUpBeforePermissionTests(){
        try{
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
        }
        catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }
    @Test
    void removeRoleTo1() {
        try {

            setUpBeforePermissionTests();
            store1.removeRoleInHierarchy(userId1,userId2);
            assertEquals(store1.getInfoOnManagersOwners(userId1).size(),1);

        } catch (NoPermissionException e) {
            e.printStackTrace();
            fail();
        }

    }
    @Test
    void removeRoleTo2() {
        try {
            setUpBeforePermissionTests();

            store1.removeRoleInHierarchy(userId2,userId4);
            assertEquals(store1.getInfoOnManagersOwners(userId1).size(),4);

        } catch (NoPermissionException e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    void removeRoleTo3() {
        try {
            setUpBeforePermissionTests();

            store1.removeRoleInHierarchy(userId1,userId4);
            List<StoreRoles> storeRoles = store1.getInfoOnManagersOwners(userId1);
            assertEquals(storeRoles.size(),4);

        } catch (NoPermissionException e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    void removeRoleTo4() {
        try {
            setUpBeforePermissionTests();

            store1.removeRoleInHierarchy(userId2,userId6);
            List<StoreRoles> storeRoles = store1.getInfoOnManagersOwners(userId1);
            assertEquals(storeRoles.size(),5);

        } catch (NoPermissionException e) {
            e.printStackTrace();
            fail();
        }

    }
    @Test
    void removeRoleToBad() {
        try {
            setUpBeforePermissionTests();

            store1.removeRoleInHierarchy(userId6,userId2);
            fail();

        } catch (IllegalArgumentException | NoPermissionException e) {
            e.printStackTrace();
        }
    }
    @Test
    void removePermissin() {
        try {
            setUpBeforePermissionTests();
            List<String> per = new ArrayList<>();
            per.add("ADD_NEW_PRODUCT");
            per.add("EDIT_PRODUCT");
            per.add("REMOVE_PRODUCT");
            per.add("EDIT_STORE_POLICY");
            per.add("VIEW_FORUM");


            store1.removeSomePermissions(userId1,userId2,per);
            List<StoreRoles> storeRoles = store1.getInfoOnManagersOwners(userId1);
            assertEquals(storeRoles.size(),6);
            for (StoreRoles role : storeRoles) {
                if (!role.getUserId().equals("userId1")) {
                    assertFalse(role.getPermissions().stream().anyMatch(
                            ProductPerm -> per.stream().anyMatch(
                                    deletedPerm -> deletedPerm.equals(ProductPerm.toString())
                            )));

                }
            }

        } catch (IllegalArgumentException | NoPermissionException e) {
            e.printStackTrace();
            fail();
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

        } catch (NoPermissionException | NotifyException | UserException e) {
            e.printStackTrace();
            fail();
        }
    }


}
