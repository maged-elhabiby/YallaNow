package com.yallanow.analyticsservice.services;

import com.yallanow.analyticsservice.exceptions.RecombeeClientException;
import com.yallanow.analyticsservice.exceptions.ValidationException;

public class ExceptionHelper {
    public static void handleRecombeeClientException(String msg, RecombeeClientException e) {
        // Log the error
        // You can also rethrow the exception if you want to handle it at a higher level (e.g., in a controller)
    }

    public static void handleValidationException(String msg, ValidationException e) {
        // Log the error
        // You can also rethrow the exception if you want to handle it at a higher level (e.g., in a controller)
    }
}
