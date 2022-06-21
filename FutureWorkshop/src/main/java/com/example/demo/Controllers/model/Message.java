package com.example.demo.Controllers.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Message {

    private String recieverName;
    private String body;

    public Message(String recieverName, String body) {
        this.recieverName = recieverName;
        this.body = body;
    }

    public String getReceiverName(){return  recieverName;}

}
