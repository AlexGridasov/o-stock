package com.gri.alex.license.service;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ CallNotPermittedException.class })
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {

        return new ResponseEntity<>(
                ex.getMessage(), new HttpHeaders(), HttpStatus.GATEWAY_TIMEOUT);
    }
}
