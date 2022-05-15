package com.example.demo.CustomExceptions.Exception;

public class CantPurchaseException  extends Exception {
    public CantPurchaseException(String message) {
        super(message);
    }

}
