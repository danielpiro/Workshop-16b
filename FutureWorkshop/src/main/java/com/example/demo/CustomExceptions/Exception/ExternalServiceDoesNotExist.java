package com.example.demo.CustomExceptions.Exception;

public class ExternalServiceDoesNotExist extends Exception {
    public ExternalServiceDoesNotExist (String message){
        super(message);
    }
}
