package com.example.demo.CustomExceptions.ExceptionHandler;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter

public class ReturnValue<T> {

    private boolean success;
    private String reason;
    private T value;

    public ReturnValue(boolean success, String reason, T value) {
        this.success = success;
        this.reason = reason;
        this.value = value;
    }
}
