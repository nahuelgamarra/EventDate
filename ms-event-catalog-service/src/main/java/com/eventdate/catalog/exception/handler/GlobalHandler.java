package com.eventdate.catalog.exception.handler;

import com.eventdate.catalog.exception.CategoryNotFoundException;
import com.eventdate.catalog.exception.EventNotFoundException;
import com.eventdate.catalog.exception.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalHandler {
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ResponseMessage> categoryNotFoundException(CategoryNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseMessage.builder().message(exception.getMessage()).build());
    }
    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ResponseMessage> categoryNotFoundException(EventNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseMessage.builder().message(exception.getMessage()).build());
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseMessage> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        String message = "Invalid date format. Expected format is yyyy-MM-dd.";
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ResponseMessage.builder().message(message).build());
    }

}
