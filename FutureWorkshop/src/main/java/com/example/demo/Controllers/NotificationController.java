package com.example.demo.Controllers;

import com.example.demo.Controllers.model.realTimeNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;

@Controller
@EnableScheduling
public class NotificationController {

    private static NotificationController single_instance = null;
    private static SimpMessagingTemplate simpMessagingTemplate;

    public NotificationController(SimpMessagingTemplate simpMessagingTemplate) {
        NotificationController.simpMessagingTemplate = simpMessagingTemplate;
    }

    public static NotificationController getInstance() {
        if (single_instance == null)
            single_instance = new NotificationController(simpMessagingTemplate);
        return single_instance;
    }


    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public realTimeNotification receivePublicMessage(@Payload realTimeNotification message) {
        return message;
    }


    @MessageMapping("/private-message")
    public realTimeNotification sendNotification(@Payload realTimeNotification message) {
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message.getReceiverName().concat("\n"+"sender: "+message.getSenderName()+"\n"+"title: "+message.getTitle()+"\n"+"body: "+message.getBody()+"\n"+"date: "+message.getDate()));
        System.out.println(message.getReceiverName());
        return message;
    }

//    @Scheduled(fixedRate = 3000)
//    public void sendMessages() {
//        System.out.println("in function");
//        //simpMessagingTemplate.convertAndSend("/chatroom/public" , "hello");
//        simpMessagingTemplate.convertAndSendToUser("test" ,"/private", "hello from admin");
//
//    }


}
