package com.eventdate.msreservationservice.exception.handler;

import com.eventdate.msreservationservice.exception.ReservationCreationException;
import com.eventdate.msreservationservice.exception.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(ReservationCreationException.class)
    public ResponseEntity<ResponseMessage> userAlreadyExistsException(ReservationCreationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseMessage.builder().message(exception.getMessage()).build());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
