package com.example.demo.Tests.Unit;


import com.example.demo.Controllers.NotificationController;
import com.example.demo.Controllers.model.Message;
import com.example.demo.Controllers.model.realTimeNotification;
import com.example.demo.User.Subscriber;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class SubscriberTest {
    String pattern = "MM/dd/yyyy HH:mm:ss";
    private static Subscriber subscriber;
    private static Subscriber subscriber2;
    private static Subscriber subscriber3;

    @BeforeEach
    void setUp() throws IOException {
        subscriber = new Subscriber("dan12", "pass1234");
        subscriber2 = new Subscriber("nir33", "pass12345");
        subscriber2.setLogged_in(true);
        subscriber2.AddQuery("what is the cheapest store for milk");
        subscriber3 = new Subscriber("idan44", "pass123456");
        subscriber3.AddQuery("what is the cheapest store for soda");
        subscriber3.AddQuery("how much does a hat cost");
    }

    @org.junit.jupiter.api.Test
    void getName() {
        assertEquals("dan12", subscriber.getName());
        assertEquals("nir33", subscriber2.getName());

    }


    @org.junit.jupiter.api.Test
    void getQueries() {
        assertEquals(0, subscriber.getQueries().size());
        assertEquals(1, subscriber2.getQueries().size());
        assertEquals(2, subscriber3.getQueries().size());


    }

    @org.junit.jupiter.api.Test
    void loggedin() {
        //assertEquals(false, subscriber.isLogged_in());
        //assertEquals(true, subscriber2.isLogged_in());
        //assertEquals(false, subscriber3.isLogged_in());
      //  NotificationController.getInstance().sendNotification(new realTimeNotification(subscriber.name,"abudi","love of my life","marriage","finaaaaaly",new SimpleDateFormat(pattern).format(Calendar.getInstance().getTime())));
      //NotificationController.getInstance().sendMessage(new Message(subscriber.getName(), "hi hi hi"));
    }
}
