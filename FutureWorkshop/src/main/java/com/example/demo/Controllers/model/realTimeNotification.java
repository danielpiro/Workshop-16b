package com.example.demo.Controllers.model;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class realTimeNotification {
    private String senderName;
    private String receiverName;
    private String subject;
    private String title;
    private String body;
    private String date;


    public realTimeNotification(String receiverName,String sender, String subject, String title, String body, String date){
        this.receiverName = receiverName;
        this.senderName=sender;
        this.subject=subject;
        this.title=title;
        this.body=body;
        this.date=date;
    }

}

   // NotificationSubject subject, String title, String body