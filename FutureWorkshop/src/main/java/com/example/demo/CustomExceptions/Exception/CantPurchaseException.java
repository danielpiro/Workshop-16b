package com.example.demo.CustomExceptions.Exception;

public class CantPurchaseException  extends RuntimeException {
    public CantPurchaseException(String message) {
        super(message);
    }

}
