package com.example.demo.NotificationsManagement;


import com.example.demo.Database.DTOobjects.User.ComplaintDTO;
import com.example.demo.GlobalSystemServices.IdGenerator;


public class  ComplaintNotification  {
    private final String sentFrom;
    private final NotificationSubject subject;
    private final String title;
    private final String body;
    private boolean read;
    private final int id;

    public ComplaintNotification(String sentFrom, NotificationSubject subject, String title, String body) {
        this.sentFrom = sentFrom;
        this.subject = subject;
        this.title = title;
        this.body = body;
        read = false;
        id = IdGenerator.getInstance().getComplaintNotificationId();
    }

    public ComplaintNotification(String sentFrom, NotificationSubject subject, String title, String body, boolean read, int id) {
        this.sentFrom = sentFrom;
        this.subject = subject;
        this.title = title;
        this.body = body;
        this.read = read;
        this.id = id;
    }

    public String getSentFrom() {
        return sentFrom;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
    public NotificationSubject getSubject() {
        return subject;
    }
    public boolean isRead() {
        return read;
    }
    public void setReadTrue(){this.read = true;}
    public ComplaintNotification getDeepCopy(){
        return new ComplaintNotification(sentFrom,subject, title, body);
    }
    public int getId(){return id;}

    public ComplaintDTO getDTO(String userId){
        return new ComplaintDTO(id,sentFrom,subject,title,body,read,userId);


    }
}
