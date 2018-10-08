package com.ciel.pocket.auth.infrastructure.exceptions;

public class ObjectExistingException extends RuntimeException{
    public ObjectExistingException(String message) {
        super(message);
    }
}
