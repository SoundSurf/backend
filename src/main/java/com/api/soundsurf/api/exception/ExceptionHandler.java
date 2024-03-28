package com.api.soundsurf.api.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class ExceptionHandler {

    private void errorLog(final ResponseEntity<ExceptionDto> e) {
        final var a = e.getBody();
        log.error(Objects.requireNonNull(Objects.requireNonNull(e.getBody()).getMessage()));
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> exception(MethodArgumentNotValidException e) {
        final var exceptionBody =  ResponseEntity.badRequest().body(new ExceptionDto(e));
        errorLog(exceptionBody);

        return exceptionBody;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionDto> exception(ConstraintViolationException e) {
        final var exceptionBody =  ResponseEntity.badRequest().body(new ExceptionDto(e));
        errorLog(exceptionBody);

        return exceptionBody;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ApiException.class)
    public ResponseEntity<ExceptionDto> exception(ApiException e) {
        final var exceptionBody =  ResponseEntity.badRequest().body(new ExceptionDto(e));
        errorLog(exceptionBody);

        return exceptionBody;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ExceptionDto> exception(WebExchangeBindException e) {
        final var exceptionBody =  ResponseEntity.badRequest().body(new ExceptionDto(e));
        errorLog(exceptionBody);

        return exceptionBody;
    }
}
