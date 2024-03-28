package com.yallanow.analyticsservice.exceptions;

/**
 * This exception is thrown when there is an error related to the Recombee client in the analytics service.
 */
public class RecombeeClientException extends Exception {
    /**
     * Constructs a new RecombeeClientException with the specified detail message.
     *
     * @param message the detail message
     */
    public RecombeeClientException(String message) {
        super(message);
    }

    /**
     * Constructs a new RecombeeClientException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public RecombeeClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
