package com.override.service.communication;

import com.override.model.Recipient;
import enums.CommunicationType;

public interface CommunicationStrategy {
    void sendMessage(Recipient recipient, String message);

    Recipient updateRecipient(Recipient recipient, String value);

    CommunicationType getType();
}
