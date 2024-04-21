package com.project.shorturl.controller;

import com.project.shorturl.controller.dto.ExceptionResponse;
import com.project.shorturl.exception.GeneratedUrlException;
import com.project.shorturl.exception.LifeTimeExpiredException;
import com.project.shorturl.exception.RedirectException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(GeneratedUrlException.class)
    public ResponseEntity<ExceptionResponse> handleGeneratedUrlException(HttpServletRequest request,
                                                                         Exception exception) {
        return getResponse(request, exception, BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleProcessValidationError(HttpServletRequest request,
                                                                          Exception exception,
                                                                          BindingResult bindingResult) {
        return getResponseEntityValid(request, exception, bindingResult, BAD_REQUEST);
    }

    @ExceptionHandler(RedirectException.class)
    public ResponseEntity<ExceptionResponse> handleRedirectException(HttpServletRequest request,
                                                                     Exception exception) {
        return getResponse(request, exception, HttpStatus.GONE);
    }

    @ExceptionHandler(LifeTimeExpiredException.class)
    public ResponseEntity<ExceptionResponse> handleLifeTimeExpiredException(HttpServletRequest request,
                                                                            Exception exception) {
        return getResponse(request, exception, HttpStatus.NOT_FOUND);
    }


    private ResponseEntity<ExceptionResponse> getResponseEntityValid(HttpServletRequest request,
                                                                     Exception exception,
                                                                     BindingResult bindingResult,
                                                                     HttpStatus status) {
        log.warn(exception.getClass().getName() + ": " + exception.getMessage());
        String message = String.valueOf(bindingResult.getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .toList());
        return ResponseEntity.status(status)
                .body(new ExceptionResponse(request.getRequestURI(), message));
    }

    private ResponseEntity<ExceptionResponse> getResponse(HttpServletRequest request, Exception exception, HttpStatus status) {
        log.warn(exception.getClass().getName() + ": " + exception.getMessage());
        return ResponseEntity.status(status)
                .body(new ExceptionResponse(request.getRequestURI(), exception.getMessage()));
    }

}
