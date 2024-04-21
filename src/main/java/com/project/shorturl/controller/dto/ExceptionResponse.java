package com.project.shorturl.controller.dto;

import org.springframework.http.HttpStatus;

public record ExceptionResponse(String uri, String message, HttpStatus status) {
}
