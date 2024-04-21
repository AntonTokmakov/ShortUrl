package com.project.shorturl.exception;

public class LifeTimeExpiredException extends BaseRuntimeException {
    public LifeTimeExpiredException(String message) {
        super(message);
    }
}
