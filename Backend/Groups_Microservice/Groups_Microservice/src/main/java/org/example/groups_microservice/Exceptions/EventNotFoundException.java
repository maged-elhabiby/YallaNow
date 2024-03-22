package org.example.groups_microservice.Exceptions;

public class EventNotFoundException extends Exception {
    public EventNotFoundException(String message) {
        super(message);
    }

    public EventNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
