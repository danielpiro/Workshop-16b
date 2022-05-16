package com.example.demo.CustomExceptions.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ApiExceptionHandler {


    @ExceptionHandler(value = { MissingServletRequestParameterException.class,
                                MethodArgumentNotValidException.class})

    public ResponseEntity<Object> handleWrongArgument (){
        ReturnValue rv = new ReturnValue(
                false,
                "one of the variables you entered wasn't correct",
                null
        );

        return new ResponseEntity<>(rv,HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = { NoSuchElementException.class})

    public ResponseEntity<Object> handleElementDoesntExist (){
        ReturnValue rv = new ReturnValue(
                false,
                "one of the variables you entered doesn't exist",
                null
        );

        return new ResponseEntity<>(rv,HttpStatus.BAD_REQUEST);

    }


}

