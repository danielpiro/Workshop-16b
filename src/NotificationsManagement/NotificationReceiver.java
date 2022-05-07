package NotificationsManagement;

import java.util.List;

public interface NotificationReceiver {

    void sendNotificationTo(List<String> userIds, Notification notification);
}
