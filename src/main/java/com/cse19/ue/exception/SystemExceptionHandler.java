package com.cse19.ue.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SystemExceptionHandler {
    @ExceptionHandler(Exceptions.class)
    public ResponseEntity<?> handleException(Exceptions exception) {
        return ResponseEntity.status(exception.getResponseCodeValue()).body(null);
    }
}
