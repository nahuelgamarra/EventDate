package com.eventdate.msgateway.handler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ResponseMessage> handleException(Exception exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseMessage.builder().message(exception.getMessage()).build());

    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ResponseMessage> expiredJwtException(Exception exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseMessage.builder().message("JWT token has expired").build());

    }


}
