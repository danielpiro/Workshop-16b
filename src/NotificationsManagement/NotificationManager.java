package NotificationsManagement;

import CustomExceptions.NotifyException;
import GlobalSystemServices.IdGenerator;
import org.junit.runners.model.InitializationError;

import java.util.ArrayList;
import java.util.List;

public class NotificationManager {//todo add to store send notification
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

    public void sendNotificationTo(List<String> userIds,Notification notification){
        receiver.sendNotificationTo( userIds, notification);
    }
    public void sendNotificationTo(String userId,Notification notification){
        List<String> userIds = new ArrayList<>();
        userIds.add(userId);
        receiver.sendNotificationTo( userIds, notification);
    }


}
