package com.project.shorturl.exception;

public class ServiceUnavailableException extends BaseRuntimeException{
    public ServiceUnavailableException(String message) {
        super(message);
    }
}
