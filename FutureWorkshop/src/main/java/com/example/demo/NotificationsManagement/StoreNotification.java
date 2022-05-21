package com.example.demo.NotificationsManagement;


import com.example.demo.GlobalSystemServices.IdGenerator;

public class StoreNotification {
    private final getStoreInfo sentFrom;
    private final NotificationSubject subject;
    private final String Title;
    private final String Body;
    private boolean read;
    private final int id;

    public StoreNotification(getStoreInfo sentFrom, NotificationSubject subject, String title, String body) {
        this.sentFrom = sentFrom;
        this.subject = subject;
        Title = title;
        Body = body;
        read = false;
        id = IdGenerator.getInstance().getStoreNotificationId();
    }

    public getStoreInfo getSentFrom() {
        return sentFrom;
    }

    public String getTitle() {
        return Title;
    }

    public String getBody() {
        return Body;
    }
    public NotificationSubject getSubject() {
        return subject;
    }
    public boolean isRead() {
        return read;
    }

    public void setReadTrue() {
        this.read = true;
    }
    public StoreNotification getDeepCopy(){
       return new StoreNotification(sentFrom,subject,Title,Body);
    }
    public int getId(){return id;}


}
