package com.example.demo.NotificationsManagement;


import com.example.demo.Controllers.model.realTimeNotification;
import com.example.demo.Controllers.notificationController;
import com.example.demo.CustomExceptions.Exception.NotifyException;
import com.example.demo.CustomExceptions.Exception.SupplyManagementException;
import com.example.demo.CustomExceptions.Exception.UserException;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class NotificationManager {//todo add tests
    private static NotificationManager systemNotifyManager = null;
    private NotificationReceiver receiver;

    String pattern = "MM/dd/yyyy HH:mm:ss";

    // Create an instance of SimpleDateFormat used for formatting
// the string representation of date according to the chosen pattern
    DateFormat df = new SimpleDateFormat(pattern);

    // Get the today date using Calendar object.
    Date today = Calendar.getInstance().getTime();
    // Using DateFormat format method we can create a string
// representation of a date with the defined format.
    String todayAsString = df.format(today);


    private NotificationManager( NotificationReceiver receiver){
        this.receiver = receiver;
    }
    public static void buildNotificationManager( NotificationReceiver receiver){
        if (systemNotifyManager == null)
            systemNotifyManager = new NotificationManager(receiver);
    }
    public static void ForTestsOnlyBuildNotificationManager( NotificationReceiver receiver){
            systemNotifyManager = new NotificationManager(receiver);
    }
    public static NotificationManager getNotificationManager() throws NotifyException {
        if (systemNotifyManager != null)
            return systemNotifyManager;
        throw new NotifyException("no one built NotificationManager");
    }



    public void sendNotificationTo(List<String> userIds,  StoreNotification storeNotification) throws UserException, SupplyManagementException, NoPermissionException, IOException {
        receiver.sendNotificationTo(userIds, storeNotification);
    }
    public void sendNotificationTo(String userId,  StoreNotification storeNotification) throws UserException {
        List<String> userIds = new ArrayList<>();
        userIds.add(userId);
        receiver.sendNotificationTo( userIds, storeNotification);
        notificationController.getInstance().sendNotification(new realTimeNotification(userId,storeNotification.getSentFrom().getStoreName(), storeNotification.getSubject().toString(), storeNotification.getTitle(), storeNotification.getBody(), new SimpleDateFormat(pattern).format(Calendar.getInstance().getTime())));

    }
    public void sendComplaintTo(String senderId, ComplaintNotification ComplaintNotification) throws UserException {
        receiver.sendComplaintToAdmins( senderId,ComplaintNotification);
    }


}
