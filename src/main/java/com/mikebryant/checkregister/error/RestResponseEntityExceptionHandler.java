package com.mikebryant.checkregister.error;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    public RestResponseEntityExceptionHandler() {
        super();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        log.warn("MethodArgumentNotValid: " + e.getMessage(), e);
        return handleExceptionInternal(e, message(status, e, e.getBindingResult()), headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        log.warn("HttpMessageNotReadable: " + e.getMessage(), e);
        return handleExceptionInternal(e, message(status, e), headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException e,
                                                                        WebRequest request) {
        log.warn("DataIntegrityViolationException: " + e.getMessage(), e);
        return handleExceptionInternal(e, message(HttpStatus.BAD_REQUEST, e), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e,
                                                                WebRequest request) {
        log.warn("EntityNotFoundException: " + e.getMessage(), e);
        return handleExceptionInternal(e, message(HttpStatus.NOT_FOUND, e), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    private ApiError message(HttpStatus status, Exception e) {
        return new ApiError(status.value(),
                ExceptionUtils.getRootCauseMessage(e),
                e.getMessage());
    }

    private ApiError message(HttpStatus status, Exception e, BindingResult result) {
        return new ApiError(status.value(),
                ExceptionUtils.getRootCauseMessage(e),
                e.getMessage(),
                ValidationUtil.fromBindingErrors(result));
    }

}
