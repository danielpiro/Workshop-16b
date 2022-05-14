package User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Message {
    /*private String sender_id;
    private String message;
    private String store_name;
    private LocalDate date;
    private boolean is_read;*/
    private String Title;
    private String Body;
    private boolean read;

    public Message(String title , String body) {
        this.Title = title;
        this.Body = body;
    }

    public String getTitle() {
        return Title;
    }

    public String getBody() {
        return Body;
    }

    public boolean isRead() {
        return read;
    }
    public void setRead(){read = true;}
}
