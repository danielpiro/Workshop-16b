package main.java.com.example.demo.NotificationsManagement;

import CustomExceptions.UserException;

import java.util.List;

public interface NotificationReceiver {

    void sendNotificationTo(List<String> userIds, StoreNotification storeNotification) throws UserException;
    void sendComplaintTo(String senderId,List<String> userIds, ComplaintNotification complaintNotification) throws UserException;
}
