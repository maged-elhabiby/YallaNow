package org.example.groups_microservice.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN) // 403
public class NotAuthorizationException extends Throwable {
    public NotAuthorizationException(String message) {
        super(message);
    }

}
