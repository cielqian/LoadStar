package com.ciel.pocket.user.infrastructure.exceptions;

public class ObjectNotExistingException extends RuntimeException {
    public ObjectNotExistingException(String message) {
        super(message);
    }
}