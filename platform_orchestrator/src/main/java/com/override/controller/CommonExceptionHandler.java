package com.override.controller;

import com.override.exception.BaseOrchestratorException;
import dtos.CommonErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;

import java.time.Instant;

/**
 * Единый интерфейс API обработки ошибок
 */
@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<CommonErrorDTO> handleException(Exception e, ServletWebRequest request) {
        log.error(e.getMessage(), e);
        if (e instanceof BaseOrchestratorException) {
            return ResponseEntity.status(((BaseOrchestratorException) e).status).body(createDto(e));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createDto(e));
    }

    /**
     * Создание дто ошибки
     */
    private CommonErrorDTO createDto(Exception ex) {
        return CommonErrorDTO
                .builder()
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .build();
    }

}

