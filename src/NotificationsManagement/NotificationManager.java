package NotificationsManagement;

import CustomExceptions.NotifyException;
import CustomExceptions.UserException;

import java.util.ArrayList;
import java.util.List;

public class NotificationManager {
    private static NotificationManager systemNotifyManager = null;
    private NotificationReceiver receiver;

    private NotificationManager(NotificationReceiver receiver){
        this.receiver = receiver;
    }
    public static void buildNotificationManager(NotificationReceiver receiver){
        if (systemNotifyManager == null)
            systemNotifyManager = new NotificationManager(receiver);
    }
    public static NotificationManager getNotificationManager() throws  NotifyException {
        if (systemNotifyManager != null)
            return systemNotifyManager;
        throw new NotifyException("no one built NotificationManager");
    }

    public void sendNotificationTo(List<String> userIds, StoreNotification storeNotification) throws UserException {
        receiver.sendNotificationTo( userIds, storeNotification);
    }
    public void sendNotificationTo(String userId, StoreNotification storeNotification) throws UserException {
        List<String> userIds = new ArrayList<>();
        userIds.add(userId);
        receiver.sendNotificationTo( userIds, storeNotification);
    }
    public void sendComplaintTo(String senderId,List<String> userIds, ComplaintNotification ComplaintNotification) throws UserException {
        receiver.sendComplaintTo( senderId,userIds, ComplaintNotification);
    }


}
