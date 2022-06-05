package com.example.demo.Controllers;

import com.example.demo.Controllers.model.realTimeNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    private static NotificationController single_instance = null;

    public static NotificationController getInstance() {
        if (single_instance == null)
            single_instance = new NotificationController();
        return single_instance;
    }

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public realTimeNotification receivePublicMessage(@Payload realTimeNotification message) {
        System.out.println(message);
        return message;
    }

    @MessageMapping("/private-message")
    public realTimeNotification recievePrivateMessage(@Payload realTimeNotification message) {
        System.out.println(message);
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message);
        return message;
    }
}
