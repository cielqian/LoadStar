package com.ciel.pocket.auth.infrastructure.exceptions;

public class ObjectNotExistingException extends RuntimeException {
    public ObjectNotExistingException(String message) {
        super(message);
    }
}
