package com.yallanow.analyticsservice.exceptions;

/**
 * This exception is thrown when an error occurs in the UserService.
 */
public class UserServiceException extends Exception {

    /**
     * Constructs a new UserServiceException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     * @param cause the cause (which is saved for later retrieval by the getCause() method)
     */
    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
