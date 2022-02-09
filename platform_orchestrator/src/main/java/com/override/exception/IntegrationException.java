package com.override.exception;

import org.springframework.http.HttpStatus;

public class IntegrationException extends BaseOrchestratorException {
    public IntegrationException(String message, Throwable cause) {
        super(message, cause, HttpStatus.SERVICE_UNAVAILABLE);
    }

    public IntegrationException(String message) {
        super(message, HttpStatus.SERVICE_UNAVAILABLE);
    }

    public IntegrationException() {
        super("Сервис не доступен", HttpStatus.SERVICE_UNAVAILABLE);
    }
}
