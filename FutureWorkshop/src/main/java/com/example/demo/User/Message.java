package com.example.demo.User;

import java.time.LocalDate;

public class Message {
    private String sender_id;
    private String message;
    private String store_name;
    private LocalDate date;
    private boolean is_read;

    public Message(String sender_id, String message, LocalDate date,String storeName) {
        this.sender_id = sender_id;
        this.message = message;
        this.date = date;
        this.is_read = false;
        store_name = storeName;
    }

    public String getSender_id() {
        return sender_id;
    }
    public String getStore_name() {
        return store_name;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isIs_read() {
        return is_read;
    }

    public void setIs_read(boolean is_read) {
        this.is_read = is_read;
    }
}
