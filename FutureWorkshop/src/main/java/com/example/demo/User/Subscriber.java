package com.example.demo.User;


import com.example.demo.Controllers.NotificationController;
import com.example.demo.Controllers.model.Message;
import com.example.demo.Controllers.model.realTimeNotification;
import com.example.demo.GlobalSystemServices.IdGenerator;
import com.example.demo.NotificationsManagement.ComplaintNotification;
import com.example.demo.NotificationsManagement.StoreNotification;

import java.text.SimpleDateFormat;
import java.util.*;


public class Subscriber extends User {
    String pattern = "MM/dd/yyyy HH:mm:ss";

    private String password;
    private boolean logged_in = false;
    private List<String> Queries; //3.5
    private List<StoreNotification> realTimestoreNotifications;
    private List<StoreNotification> storeNotifications;

    private List<ComplaintNotification> complaintNotifications;
    private Object lock = new Object();


    public Subscriber(String user_name, String password) {
        super(user_name);
        this.password = password;
        this.name = user_name;
        Queries = new ArrayList<>();
        lock = new Object();
        realTimestoreNotifications = new ArrayList<>();
        storeNotifications = new ArrayList<>();
        complaintNotifications = new ArrayList<>();
    }

    public boolean validateWebSocket(String session_Id) {
        return session_Id.equals(getSessionId());
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLogged_in() {
        return logged_in;
    }

    public void setLogged_in(boolean logged_in) {
        this.logged_in = logged_in;
        if(logged_in)
            this.setSessionId(IdGenerator.getInstance().getSessionId());
//        if (this.logged_in && getStoreNotifications().size() > 0) {
//            for (int i = 0; i < getStoreNotifications().size(); i++) {
//                NotificationController.getInstance().sendNotification(new realTimeNotification(this.name, getStoreNotifications().get(i).getSentFrom().getStoreName(), getStoreNotifications().get(i).getSubject().toString(), getStoreNotifications().get(i).getTitle(), getStoreNotifications().get(i).getBody(), new SimpleDateFormat(pattern).format(Calendar.getInstance().getTime())));
//            }
//            storeNotifications = new ArrayList<>();
//        }

    }

    public void AddQuery(String s) {
        this.getQueries().add(s);
    }


    public List<String> getQueries() {
        return Queries;
    }


    public List<StoreNotification> getRealTimestoreNotifications() {
        return realTimestoreNotifications;
    }

    public List<StoreNotification> getStoreNotifications() {
        return storeNotifications;
    }

    public List<ComplaintNotification> getComplaintNotifications() {
        return complaintNotifications;
    }

    public void addComplaint(ComplaintNotification complaintNotification) {
        getComplaintNotifications().add(complaintNotification);
    }

    public void sendMessage(){
        NotificationController.getInstance().sendMessage(new Message(this.name,"sending a real time message to "+this.name));

    }
    public void addNotification(StoreNotification storeNotification) {
        if (isLogged_in())
            NotificationController.getInstance().sendNotification(new realTimeNotification(this.name, storeNotification.getSentFrom().getStoreName(), storeNotification.getSubject().toString(), storeNotification.getTitle(), storeNotification.getBody(), new SimpleDateFormat(pattern).format(Calendar.getInstance().getTime())));
        else
            getRealTimestoreNotifications().add(storeNotification);
        getStoreNotifications().add(storeNotification);
    }

    public Object getLock() {
        return lock;
    }

    public void resetNotification(){this.realTimestoreNotifications = new ArrayList<>();}

}
