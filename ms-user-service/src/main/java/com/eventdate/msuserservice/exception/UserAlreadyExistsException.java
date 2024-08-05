package com.eventdate.msuserservice.exception;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String emailAlreadyRegistered) {
        super(emailAlreadyRegistered);
    }
}
