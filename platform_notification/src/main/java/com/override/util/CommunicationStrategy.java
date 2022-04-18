package com.override.util;

import com.override.models.Recipient;
import enums.Communication;
import org.springframework.http.ResponseEntity;

public interface CommunicationStrategy {
    ResponseEntity<String> sendMessage(Recipient recipient, String message);
    Recipient setCommunication(Recipient recipient, String value);
    Communication getType();
}
