package com.override.util;

import com.override.models.Recipient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface MessageStrategy {
    ResponseEntity<HttpStatus> sendMessage(Recipient recipient, String message);
}
