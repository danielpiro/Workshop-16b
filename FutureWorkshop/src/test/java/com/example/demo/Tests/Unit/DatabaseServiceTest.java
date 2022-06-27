package com.example.demo.Tests.Unit;

import com.example.demo.CustomExceptions.Exception.NotifyException;
import com.example.demo.CustomExceptions.Exception.ResourceNotFoundException;
import com.example.demo.CustomExceptions.Exception.SupplyManagementException;
import com.example.demo.CustomExceptions.Exception.UserException;
import com.example.demo.Database.DTOobjects.Cart.ShoppingBasketDTO;
import com.example.demo.Database.DTOobjects.GlobalServices.IdGeneratorDTO;
import com.example.demo.Database.DTOobjects.History.HistoryDTO;
import com.example.demo.Database.DTOobjects.Store.Permissions.StoreRoleDTO;
import com.example.demo.Database.DTOobjects.Store.StoreDTO;
import com.example.demo.Database.DTOobjects.User.ComplaintDTO;
import com.example.demo.Database.DTOobjects.User.UserDTO;
import com.example.demo.Database.Service.DatabaseService;
import com.example.demo.GlobalSystemServices.IdGenerator;
import com.example.demo.NotificationsManagement.*;
import com.example.demo.ShoppingCart.ShoppingBasket;
import com.example.demo.ShoppingCart.ShoppingCart;
import com.example.demo.Store.Review;
import com.example.demo.Store.Store;
import com.example.demo.StorePermission.Permission;
import com.example.demo.StorePermission.StoreRoleType;
import com.example.demo.User.Subscriber;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.testng.annotations.AfterTest;

import javax.naming.NoPermissionException;
import javax.validation.constraints.AssertTrue;
import java.sql.Array;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DatabaseServiceTest {

    @Autowired
    DatabaseService databaseService;

    @AfterEach
    @BeforeEach
    public void deleteDeleteDatabase(){
        databaseService.clearDatabase();
    }



    @Test
    void getShoppingCart() {
        ShoppingCart sc = new ShoppingCart("NotAValidUsername");

        sc.addProduct("product1","store1",5);
        sc.addProduct("product2","store1",8);
        sc.addProduct("product3","store1",1);
        sc.addProduct("product1","store2",5);
        sc.addProduct("product2","store2",2);

        databaseService.saveShoppingCart(sc);

//        ShoppingCart scGuy = new ShoppingCart("guy");
//
//        scGuy.addProduct("product1","store1",5);
//        scGuy.addProduct("product2","store1",8);
//        scGuy.addProduct("product3","store1",1);
//        scGuy.addProduct("product1","store2",5);
//        scGuy.addProduct("product2","store2",2);
//
//        databaseService.saveShoppingCart(scGuy);



        ShoppingCart sc2 =databaseService.getShoppingCart("NotAValidUsername");

        assertTrue(sc2.equals(sc));

    }






    @Test
    void findUser() {
        Subscriber sb = new Subscriber("NotAValidUsername","rotman");
        UserDTO user =databaseService.saveUser(sb);
        List<UserDTO> ls = databaseService.findUserbyName("NotAValidUsername");
        UserDTO user2= ls.get(0);

        databaseService.deleteUserByName("NotAValidUsername");
        assertTrue(user.hashCode()==user.hashCode());

    }

    @Test
    void notifications() {


        ComplaintDTO complaint1 = new ComplaintDTO(99999,"Ron Weasley", NotificationSubject.StoreForum,"urgent","what what in the butt",false,"NotAValidUsername");
        databaseService.saveComplaint(complaint1);
        List<ComplaintDTO> ls = databaseService.findComplaintByUserId("NotAValidUsername");
        ComplaintDTO complaint2= ls.get(0);

        databaseService.deleteComplaint(complaint1);
        assertTrue(complaint2.hashCode()==complaint1.hashCode());

    }


    @Test
    void saveAndFindHistory() {
        HistoryDTO his = new HistoryDTO("NotAValidUsername","Store_1","Garbage Can",8.3f,5, LocalDateTime.now());

        databaseService.savePurchaseHistory(his);
        List<HistoryDTO> ls = databaseService.findHistoryByUserId("NotAValidUsername");
        HistoryDTO his2= ls.get(0);


        int firstHash = his.hashCode();
        int secondHash = his2.hashCode();

        databaseService.deleteHistoryDTO(his);
        assertTrue(firstHash==secondHash);
    }

    @Test
    void saveIdGenerator(){

        //save
        IdGenerator idGenerator = IdGenerator.getInstance(1L,2L,3L,4L, 5L ,6L, 7L,8L, 9L);
        IdGenerator.addDatabase(databaseService);
        idGenerator.getProductId();
        //override

        IdGenerator.getForTestingOnly(0L,0L,0L,0L, 0L ,0L, 0L,0L, 0L);
        //load
        IdGenerator.getInstance().loadData();
        assertTrue(IdGenerator.getInstance().equals(idGenerator));

    }

    @Test
    void saveStore() throws SQLException, NoPermissionException, UserException, NotifyException, SupplyManagementException, ResourceNotFoundException {
        NotificationManager.ForTestsOnlyBuildNotificationManager(new NotificationReceiver() {
            @Override
            public void sendNotificationTo(List<String> userIds, StoreNotification storeNotification) throws UserException, UserException {}
            @Override
            public void sendComplaintToAdmins(String senderId, ComplaintNotification complaintNotification) throws UserException {}
        });
        //create store
        List<String> originalOwner = new ArrayList<>();
        originalOwner.add("testOriginalOwner");
        Store store= new Store("testName","testID", originalOwner,databaseService);
        List<Permission> permissions = new ArrayList<>();
        permissions.add(Permission.ADD_NEW_PRODUCT);
        permissions.add(Permission.EDIT_PRODUCT);
        store.createOwner("testOriginalOwner","testOwner1",permissions);
        StoreDTO storeDTO = new StoreDTO(store.getId(), store.getStoreName(), store.getStoreState().toString(),store.getStoreRating());


        HashMap<StoreRoleDTO, List<Permission>> storeRoleDTOListHashMap = new HashMap<>();
        storeRoleDTOListHashMap.put(new StoreRoleDTO("testOriginalOwner",store.getId(), StoreRoleType.original_owner.toString()), Arrays.asList(Permission.values()));
        storeRoleDTOListHashMap.put(new StoreRoleDTO("testOwner1",store.getId(), StoreRoleType.owner.toString()),permissions);


        databaseService.saveStore(storeDTO,storeRoleDTOListHashMap);

        //store load
        List<Store> stores = new ArrayList<>();
        List<StoreDTO> storeDTOList = databaseService.getAllStores();
        for(StoreDTO LoadedStoreDTO: storeDTOList){
           stores.add( new Store(LoadedStoreDTO,databaseService));
        }

        if(stores.size() != 1){
            fail();
        }
        Store loadStore = stores.get(0);
        assertTrue(loadStore.equals(store));

    }

}