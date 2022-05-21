package com.example.demo.User;


import CustomExceptions.UserException;
import NotificationsManagement.ComplaintNotification;
import NotificationsManagement.StoreNotification;

import java.util.ArrayList;
import java.util.List;


public class Subscriber extends User {
    private String password;
    private boolean logged_in = false;
    private List<String> Queries; //3.5
    private List<StoreNotification> storeNotifications;
    private List<ComplaintNotification> complaintNotifications;
    private Object lock = new Object();

    public Subscriber(String user_name, String password){
        super(user_name);
        this.password = password;
        this.name = user_name;
        Queries= new ArrayList<>();
        lock = new Object();
        storeNotifications = new ArrayList<>();
        complaintNotifications = new ArrayList<>();
    }

    public String getName()
    {
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

    }
    public void AddQuery(String s){this.getQueries().add(s);}

    public List<String> getQueries() {
        return Queries;
    }


    public List<StoreNotification> getStoreNotifications() {
        return storeNotifications;
    }

    public List<ComplaintNotification> getComplaintNotifications() {
        return complaintNotifications;
    }

    public void addComplaint(ComplaintNotification complaintNotification){ getComplaintNotifications().add(complaintNotification);}
    public void addNotification(StoreNotification storeNotification){ getStoreNotifications().add(storeNotification);}

    public Object getLock (){
        return lock;
    }
}
