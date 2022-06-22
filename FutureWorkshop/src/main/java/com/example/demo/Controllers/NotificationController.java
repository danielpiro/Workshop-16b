package com.example.demo.Controllers;

import com.example.demo.Controllers.model.Message;
import com.example.demo.Controllers.model.realTimeNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    private static NotificationController single_instance = null;
    private static SimpMessagingTemplate simpMessagingTemplate;

    private NotificationController(SimpMessagingTemplate simpMessagingTemplate) {
        NotificationController.simpMessagingTemplate = simpMessagingTemplate;
    }

    public static NotificationController getInstance() {
        if (single_instance == null)
            single_instance = new NotificationController(simpMessagingTemplate);
        return single_instance;
    }


    @MessageMapping("/private-test")
    public void sendMessage(@Payload Message message){
        if (simpMessagingTemplate == null){
            throw new NullPointerException();
        }
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message.getReceiverName().concat("\n" + message.getBody()));
        System.out.println(message.getReceiverName());
        System.out.println(message.getBody());
    }

    @MessageMapping("/private-message")
    public void sendNotification(@Payload realTimeNotification message) {
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message.getReceiverName().concat("\n" + "sender: " + message.getSenderName() + "\n" + "title: " + message.getTitle() + "\n" + "body: " + message.getBody() + "\n" + "date: " + message.getDate()));
        System.out.println(message.getReceiverName());
    }

}