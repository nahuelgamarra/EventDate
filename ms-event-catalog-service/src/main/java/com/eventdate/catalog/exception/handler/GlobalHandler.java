package com.eventdate.catalog.exception.handler;

import com.eventdate.catalog.exception.CategoryNotFoundException;
import com.eventdate.catalog.exception.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandler {
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ResponseMessage> categoryNotFoundException(CategoryNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseMessage.builder().message(exception.getMessage()).build());
    }

}
