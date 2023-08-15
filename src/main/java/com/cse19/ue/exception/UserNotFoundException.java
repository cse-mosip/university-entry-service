package com.cse19.ue.exception;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public UserNotFoundException(String message) {
        super(message);
    }
}
