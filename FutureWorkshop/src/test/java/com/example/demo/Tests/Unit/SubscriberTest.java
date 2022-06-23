package com.example.demo.Tests.Unit;


import com.example.demo.Controllers.NotificationController;
import com.example.demo.Controllers.model.Message;
import com.example.demo.Controllers.model.realTimeNotification;
import com.example.demo.User.Subscriber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.AbstractMessageChannel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class SubscriberTest {
    String pattern = "MM/dd/yyyy HH:mm:ss";
    private static Subscriber subscriber;
    private static Subscriber subscriber2;
    private static Subscriber subscriber3;
    private final SimpMessagingTemplate simpMessagingTemplate = new SimpMessagingTemplate(new AbstractMessageChannel() {
        @Override
        protected boolean sendInternal(org.springframework.messaging.Message<?> message, long timeout) {
            return false;
        }
    });

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
        String hi_hi_hi = new NotificationController(simpMessagingTemplate).sendMessage(new Message(subscriber.name, "hi"));
        Assertions.assertEquals(hi_hi_hi , "was success");

    }
}
