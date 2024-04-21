package com.project.shorturl.controller;

import com.project.shorturl.controller.dto.ExceptionResponse;
import com.project.shorturl.exception.GeneratedUrlException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(GeneratedUrlException.class)
    public ResponseEntity<ExceptionResponse> handleGeneratedUrlException(HttpServletRequest request, Exception exception) {
        log.warn(exception.getClass().getName() + ": " + exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(request.getRequestURI(), exception.getMessage(), HttpStatus.BAD_REQUEST));
    }

}
