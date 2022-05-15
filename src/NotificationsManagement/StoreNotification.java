package NotificationsManagement;

public class StoreNotification {
    private final getStoreInfo sentFrom;
    private final NotificationSubject subject;
    private final String Title;
    private final String Body;
    private boolean read;

    public StoreNotification(getStoreInfo sentFrom, NotificationSubject subject, String title, String body) {
        this.sentFrom = sentFrom;
        this.subject = subject;
        Title = title;
        Body = body;
        read = false;
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
}
