package com.example.demo.CustomExceptions.ExceptionHandler;

import com.example.demo.CustomExceptions.Exception.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.NoPermissionException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ApiExceptionHandler {


    @ExceptionHandler(value = { MissingServletRequestParameterException.class,
                                MethodArgumentNotValidException.class})

    public ReturnValue handleWrongArgument (){
        ReturnValue rv = new ReturnValue(
                false,
                "one of the variables you entered wasn't correct",
                null
        );

        return rv;

    }

    @ExceptionHandler(value = { NoSuchElementException.class , CantPurchaseException.class,IllegalArgumentException.class,
            NoPermissionException.class, UserException.class, UserDeleted.class, SupplyManagementException.class, NotifyException.class, ExternalServiceDoesNotExist.class})
    public ReturnValue handleElementDoesntExist (Exception e){
        ReturnValue rv = new ReturnValue(
                false,
                e.getMessage(),
                null
        );

        return rv;

    }

    //when this happens, the policy id is in the value field.
    @ExceptionHandler(value = { StorePolicyViolatedException.class })
    public ReturnValue handlePolicyException (Exception e){
        ReturnValue rv = new ReturnValue(
                false,
                "the store policy was violated" ,
                e.getMessage()
        );

        return rv;

    }
    @ExceptionHandler(value = { JsonProcessingException.class })
    public ReturnValue handleInternalProblem (Exception e){
        ReturnValue rv = new ReturnValue(
                false,
                "internal problem" ,
                e.getMessage()
        );
        return rv;

    }






}

