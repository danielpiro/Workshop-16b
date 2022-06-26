package com.example.demo.Database.DTOobjects.User;


import com.example.demo.NotificationsManagement.ComplaintNotification;
import com.example.demo.NotificationsManagement.NotificationSubject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "complaint")
public class ComplaintDTO {

    @Id
    private Integer id;
    private String sentFrom;
    private NotificationSubject subject;
    private String title;
    private String body;
    private Boolean readInt;
    private String userID ;

    public ComplaintNotification convertToComplaint(){
        return new ComplaintNotification(sentFrom,subject,title,body,readInt,id);

    }




    @Override
    public int hashCode() {
        return Objects.hash(id, sentFrom, subject, title, body, readInt, userID);
    }
}
