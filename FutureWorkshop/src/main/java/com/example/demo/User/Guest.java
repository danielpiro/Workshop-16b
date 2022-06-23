package com.example.demo.User;


import com.example.demo.GlobalSystemServices.IdGenerator;

public class Guest extends User {
    public Guest(String id) {
        super("");
        this.name=id;
        this.setSessionId(IdGenerator.getInstance().getSessionId());

    }
    public String getId() {
        return name;
    }
}
