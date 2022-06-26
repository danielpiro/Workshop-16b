package com.example.demo.Tests.Unit;

import com.example.demo.Database.DTOobjects.Cart.ShoppingBasketDTO;
import com.example.demo.Database.DTOobjects.History.HistoryDTO;
import com.example.demo.Database.DTOobjects.User.ComplaintDTO;
import com.example.demo.Database.DTOobjects.User.UserDTO;
import com.example.demo.Database.Service.DatabaseService;
import com.example.demo.NotificationsManagement.ComplaintNotification;
import com.example.demo.NotificationsManagement.NotificationSubject;
import com.example.demo.ShoppingCart.ShoppingBasket;
import com.example.demo.ShoppingCart.ShoppingCart;
import com.example.demo.Store.Review;
import com.example.demo.User.Subscriber;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import javax.validation.constraints.AssertTrue;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DatabaseServiceTest {

    @Autowired
    DatabaseService databaseService;




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

}