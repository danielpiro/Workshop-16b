package com.example.demo.User;



public class Guest extends User {
    public Guest(String id) {
        super("");
        this.name=id;

    }
    public String getId() {
        return name;
    }
}
