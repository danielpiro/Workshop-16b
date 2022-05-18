package com.example.demo.CustomExceptions.ExceptionHandler;

import com.example.demo.CustomExceptions.Exception.CantPurchaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.NoPermissionException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ApiExceptionHandler {


    @ExceptionHandler(value = { MissingServletRequestParameterException.class,
                                MethodArgumentNotValidException.class})

    public ResponseEntity handleWrongArgument (){
        ReturnValue rv = new ReturnValue(
                false,
                "one of the variables you entered wasn't correct",
                null
        );

        return new ResponseEntity<>(rv,HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = { NoSuchElementException.class , CantPurchaseException.class,IllegalArgumentException.class, NoPermissionException.class})
    public ResponseEntity handleElementDoesntExist (Exception e){
        ReturnValue rv = new ReturnValue(
                false,
                e.getMessage(),
                null
        );

        return new ResponseEntity<>(rv,HttpStatus.BAD_REQUEST);

    }




}

