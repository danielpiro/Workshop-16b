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

    private static SimpMessagingTemplate simpMessagingTemplate;

    public NotificationController(SimpMessagingTemplate simpMessagingTemplate) {
        NotificationController.simpMessagingTemplate = simpMessagingTemplate;
    }


    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public static realTimeNotification receivePublicMessage(@Payload realTimeNotification message) {
        return message;
    }

    @MessageMapping("/private-test")
    public static void sendMessage(@Payload Message message) {
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message.getReceiverName().concat("\n" + message.getBody()));
        System.out.println(message.getReceiverName());
        System.out.println(message.getBody());
    }

    @MessageMapping("/private-message")
    public static void sendNotification(@Payload realTimeNotification message) {
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message.getReceiverName().concat("\n" + "sender: " + message.getSenderName() + "\n" + "title: " + message.getTitle() + "\n" + "body: " + message.getBody() + "\n" + "date: " + message.getDate()));
        System.out.println(message.getReceiverName());
    }

}
