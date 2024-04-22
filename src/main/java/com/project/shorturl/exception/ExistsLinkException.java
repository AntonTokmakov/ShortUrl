package com.project.shorturl.exception;

public class ExistsLinkException extends BaseRuntimeException {
    public ExistsLinkException(String message) {
        super(message);
    }
}
