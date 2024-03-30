package org.example.groups_microservice.Exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.CONFLICT) // 409
public class MemberAlreadyInGroupException extends Exception{
    public MemberAlreadyInGroupException(String message) {
        super(message);
    }


}
