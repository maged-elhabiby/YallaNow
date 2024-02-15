package org.yallanow.recombeeservice.exceptions;

public class RecombeeClientException extends Exception {
    public RecombeeClientException(String message) {
        super(message);
    }

    public RecombeeClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
