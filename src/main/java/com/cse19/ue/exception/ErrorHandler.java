package com.cse19.ue.exception;

import com.cse19.ue.dto.ErrorHandlerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ErrorHandler {

    private final String USER_NOT_FOUND = "User not found exception";
    private final String DATA_FAILED_TO_SAVE = "Failed to save data";

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorHandlerDto> handle(UserNotFoundException ex) {
        log.error(USER_NOT_FOUND, ex);
        ErrorHandlerDto errorHandlerDto = new ErrorHandlerDto(USER_NOT_FOUND, ex.getMessage());
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorHandlerDto);
    }

    @ExceptionHandler(DataSavingException.class)
    public ResponseEntity<ErrorHandlerDto> handle(DataSavingException ex) {
        log.error(DATA_FAILED_TO_SAVE, ex);
        ErrorHandlerDto errorHandlerDto = new ErrorHandlerDto(DATA_FAILED_TO_SAVE, ex.getMessage());
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorHandlerDto);
    }
}
