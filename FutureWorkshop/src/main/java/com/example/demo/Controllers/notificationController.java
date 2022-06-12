package com.example.demo.Controllers;

import com.example.demo.Controllers.model.realTimeNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class notificationController {

    private static notificationController single_instance = null;

   public static notificationController getInstance(){
       if(single_instance==null)
           single_instance = new notificationController();
       return single_instance;
   }
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public realTimeNotification receivePublicMessage(@Payload realTimeNotification message){
        return message;
    }

    @MessageMapping("/private-message")
    public realTimeNotification recievePrivateMessage(@Payload realTimeNotification message){
        if(simpMessagingTemplate != null){
            simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
        }

        System.out.println(message.toString());
        return message;
    }

}
