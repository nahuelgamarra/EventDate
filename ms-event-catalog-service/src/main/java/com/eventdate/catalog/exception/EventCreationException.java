package com.eventdate.catalog.exception;

public class EventCreationException extends RuntimeException {
    public EventCreationException(String errorCreatingEvent) {
        super(errorCreatingEvent);
    }
}
