package com.project.shorturl.exception;

public class LinkNotFoundException extends BaseRuntimeException {
    public LinkNotFoundException(String message) {
        super(message);
    }
}
