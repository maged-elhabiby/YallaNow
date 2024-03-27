package com.yallanow.analyticsservice.exceptions;


public class ItemServiceException extends Exception {

    public ItemServiceException(String message) {
        super(message);
    }

    public ItemServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
