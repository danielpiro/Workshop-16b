package com.example.demo.Controllers.model;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class realTimeNotification {
    private String recieverName;
    private String senderName;
    private String subject;
    private String title;
    private String body;
    private String date;


    public realTimeNotification(String recieverName,String sender, String subject, String title, String body, String date){
        this.recieverName=recieverName;
        this.senderName=sender;
        this.subject=subject;
        this.title=title;
        this.body=body;
        this.date=date;
    }

    public String getReceiverName(){return  recieverName;}

}

   // NotificationSubject subject, String title, String body