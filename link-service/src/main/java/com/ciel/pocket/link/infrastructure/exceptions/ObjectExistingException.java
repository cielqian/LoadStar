package com.ciel.pocket.link.infrastructure.exceptions;

public class ObjectExistingException extends RuntimeException{
    public ObjectExistingException(String message) {
        super(message);
    }
}
