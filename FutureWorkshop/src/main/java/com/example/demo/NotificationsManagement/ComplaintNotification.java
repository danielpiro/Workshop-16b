package com.example.demo.NotificationsManagement;


import com.example.demo.GlobalSystemServices.IdGenerator;

public class ComplaintNotification  {
    private final String sentFrom;
    private final NotificationSubject subject;
    private final String Title;
    private final String Body;
    private boolean read;
    private final int id;

    public ComplaintNotification(String sentFrom, NotificationSubject subject, String title, String body) {
        this.sentFrom = sentFrom;
        this.subject = subject;
        Title = title;
        Body = body;
        read = false;
        id = IdGenerator.getInstance().getComplaintNotificationId();
    }

    public String getSentFrom() {
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
    public void setReadTrue(){this.read = true;}
    public ComplaintNotification getDeepCopy(){
        return new ComplaintNotification(sentFrom,subject,Title,Body);
    }
}
