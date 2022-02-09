package com.override.exception;

import org.springframework.http.HttpStatus;

public class BaseOrchestratorException extends RuntimeException {
    public HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public BaseOrchestratorException() {
    }

    public BaseOrchestratorException(HttpStatus status) {
        this.status = status;
    }

    public BaseOrchestratorException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public BaseOrchestratorException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public BaseOrchestratorException(Throwable cause, HttpStatus status) {
        super(cause);
        this.status = status;
    }

    public BaseOrchestratorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
                                     HttpStatus status) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.status = status;
    }
}
