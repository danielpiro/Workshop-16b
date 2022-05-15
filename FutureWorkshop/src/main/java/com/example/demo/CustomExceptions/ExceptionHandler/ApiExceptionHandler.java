package com.example.demo.CustomExceptions.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = { MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleNotValidException (MethodArgumentNotValidException e){
        ApiException apiException = new ApiException(
                "one of the variables you entered wasn't correct",
                e.getMessage(),

                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException,HttpStatus.BAD_REQUEST);


    }

    @ExceptionHandler(value = { MissingServletRequestParameterException.class})
    public ResponseEntity<Object> handleNotValidException (MissingServletRequestParameterException e){
        ApiException apiException = new ApiException(
                "one of the variables you entered wasn't correct",
                e.getMessage(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException,HttpStatus.BAD_REQUEST);


    }


}

