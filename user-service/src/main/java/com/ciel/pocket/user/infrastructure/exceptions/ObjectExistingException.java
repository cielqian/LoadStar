package com.ciel.pocket.user.infrastructure.exceptions;

public class ObjectExistingException extends RuntimeException{
    public ObjectExistingException(String message) {
        super(message);
    }
}
