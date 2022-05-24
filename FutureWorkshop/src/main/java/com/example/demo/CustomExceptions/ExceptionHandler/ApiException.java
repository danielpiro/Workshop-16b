package com.example.demo.CustomExceptions.ExceptionHandler;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException {

    private  String message;
    private  String errorMessage;
    //private final HttpStatus httpStatus;
    private  ZonedDateTime timestamp;


    public ApiException(String message ,String errorMessage, ZonedDateTime timestamp) {
        this.message = message;
        this.errorMessage=errorMessage;
        this.timestamp = timestamp;
    }

    public ApiException(String errorMessage, ZonedDateTime timestamp) {
        this.errorMessage = errorMessage;

        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }
    public String getErrorMessage (){
        return errorMessage;
    }



    public ZonedDateTime getTimestamp() {
        return timestamp;
    }


}
