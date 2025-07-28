package com.example.expensetracker.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

public class ConflictException extends RuntimeException{

    public ConflictException(String message){
        super(message);
    }
}
