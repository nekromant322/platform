package com.override.util;

import com.override.models.Recipient;
import enums.Communication;

public interface CommunicationStrategy {
    void sendMessage(Recipient recipient, String message);
    Recipient setCommunication(Recipient recipient, String value);
    Communication getType();
}
