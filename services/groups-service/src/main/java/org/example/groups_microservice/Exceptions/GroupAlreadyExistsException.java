package org.example.groups_microservice.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) // 409
public class GroupAlreadyExistsException extends Exception {
    public GroupAlreadyExistsException(String message) {
        super(message);
    }
    
    public GroupAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
