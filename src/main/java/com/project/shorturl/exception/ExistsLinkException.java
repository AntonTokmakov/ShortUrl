package com.project.shorturl.exception;

public class ExistsLinkException extends ServiceUrlException {
    public ExistsLinkException(String message) {
        super(message);
    }
}
