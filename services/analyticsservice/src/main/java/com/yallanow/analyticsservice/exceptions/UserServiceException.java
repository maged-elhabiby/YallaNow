package com.yallanow.analyticsservice.exceptions;

public class UserServiceException extends Exception{

    public UserServiceException(String message) {
        super(message);
    }

    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
