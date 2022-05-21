package com.example.demo.NotificationsManagement;

import com.example.demo.CustomExceptions.Exception.UserException;

import java.util.List;

public interface NotificationReceiver {

    void sendNotificationTo(List<String> userIds,  StoreNotification storeNotification) throws UserException, UserException;
    void sendComplaintTo(String senderId,List<String> userIds, ComplaintNotification complaintNotification) throws UserException;
}
