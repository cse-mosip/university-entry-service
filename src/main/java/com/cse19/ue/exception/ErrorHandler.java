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

    private final String CUSTOM_EXCEPTION = "custom exception";

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorHandlerDto> handle(CustomException ex) {
        log.error(CUSTOM_EXCEPTION, ex);
        ErrorHandlerDto errorHandlerDto = new ErrorHandlerDto(CUSTOM_EXCEPTION, ex.getMessage());
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorHandlerDto);
    }

//    @ExceptionHandler(DataSavingException.class)
//    public ResponseEntity<String> handle(DataSavingException ex) {
//        log.error(CUSTOM_EXCEPTION, ex);
//        ErrorHandlerDto errorHandlerDTO = new ErrorHandlerDTO(DATA_SAVING_EXCEPTION, ex.getMessage());
//        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(gson.toJson(errorHandlerDTO, ErrorHandlerDTO.class));
//    }

}
