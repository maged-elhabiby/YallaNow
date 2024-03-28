package com.yallanow.analyticsservice.exceptions;


/**
 * This exception is thrown when an error occurs in the ItemService class.
 */
public class ItemServiceException extends Exception {

    /**
     * Constructs a new ItemServiceException with the specified detail message.
     *
     * @param message the detail message
     */
    public ItemServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new ItemServiceException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public ItemServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
