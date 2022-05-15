package com.example.demo.CustomExceptions.ExceptionHandler;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException {

    private final String message;
    //private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;

    public ApiException(String message, ZonedDateTime timestamp) {
        this.message = message;

        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }




    public ZonedDateTime getTimestamp() {
        return timestamp;
    }


}
