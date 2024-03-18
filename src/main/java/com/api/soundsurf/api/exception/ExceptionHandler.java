package com.api.soundsurf.api.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> exception(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(new ExceptionDto(e));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionDto> exception(ConstraintViolationException e) {
        return ResponseEntity.badRequest().body(new ExceptionDto(e));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ApiException.class)
    public ResponseEntity<ExceptionDto> exception(ApiException e) {
        return ResponseEntity.badRequest().body(new ExceptionDto(e));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ExceptionDto> exception(WebExchangeBindException e) {
        return ResponseEntity.badRequest().body(new ExceptionDto(e));
    }
}
