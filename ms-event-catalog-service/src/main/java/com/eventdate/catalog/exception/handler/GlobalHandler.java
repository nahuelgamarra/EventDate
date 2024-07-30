package com.eventdate.catalog.exception.handler;

import com.eventdate.catalog.exception.CategoryNotFoundException;
import com.eventdate.catalog.exception.EventCreationException;
import com.eventdate.catalog.exception.EventNotFoundException;
import com.eventdate.catalog.exception.ResponseMessage;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ResponseMessage> categoryNotFoundException(CategoryNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseMessage.builder().message(exception.getMessage()).build());
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ResponseMessage> eventNotFoundException(EventNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseMessage.builder().message(exception.getMessage()).build());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseMessage> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        String message = "Invalid argument type: " + exception.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseMessage.builder().message(message).build());
    }

    @ExceptionHandler(EventCreationException.class)
    public ResponseEntity<ResponseMessage> eventCreationException(EventCreationException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseMessage.builder().message(exception.getMessage()).build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseMessage> handleValidationExceptions(MethodArgumentNotValidException exception) {
        StringBuilder message = new StringBuilder("Validation failed: ");
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            message.append(error.getField()).append(" ").append(error.getDefaultMessage()).append("; ");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseMessage.builder().message(message.toString()).build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseMessage> handleConstraintViolationException(ConstraintViolationException exception) {
        StringBuilder message = new StringBuilder("Constraint violation: ");
        exception.getConstraintViolations().forEach(violation ->
                message.append(violation.getPropertyPath()).append(" ").append(violation.getMessage()).append("; "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseMessage.builder().message(message.toString()).build());
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ResponseMessage> handleDateTimeParseException(DateTimeParseException exception) {
        String message = "Invalid date format: " + exception.getParsedString();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseMessage.builder().message(message).build());
    }


}
