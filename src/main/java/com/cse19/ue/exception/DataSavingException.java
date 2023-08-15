package com.cse19.ue.exception;

public class DataSavingException extends Exception {
    public DataSavingException(String message, Throwable cause) {
        super(message, cause);
    }
    public DataSavingException(String message) {
        super(message);
    }
}
