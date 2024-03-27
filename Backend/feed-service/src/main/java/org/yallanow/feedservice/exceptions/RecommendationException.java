package org.yallanow.feedservice.exceptions;

public class RecommendationException extends Exception {
    private int statusCode;

    public RecommendationException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public RecommendationException(String message, Throwable cause, int statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}

