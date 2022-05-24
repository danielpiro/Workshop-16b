package com.example.demo.CustomExceptions.ExceptionHandler;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReturnValue<T> {

    private boolean success;
    private String reason;
    private T value;


}
