package com.ciel.pocket.link.infrastructure.exceptions;

public class ObjectNotExistingException extends RuntimeException {
    public ObjectNotExistingException(String message) {
        super(message);
    }
}