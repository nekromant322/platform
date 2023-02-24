package com.override.service.communication;

import com.override.model.Recipient;
import com.override.service.TelegramService;
import dto.MessageDTO;
import enums.CommunicationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TelegramCommunication implements CommunicationStrategy {

    @Autowired
    private TelegramService telegramService;

    @Override
    public void sendMessage(Recipient recipient, String message) {
        telegramService.sendMessage(MessageDTO.builder()
                .chatId(recipient.getTelegramId().orElseThrow(() -> new IllegalStateException("У пользователя " +
                        recipient.getLogin() + " не найден id Телеграма")))
                .message(message)
                .build());
    }

    @Override
    public Recipient updateRecipient(Recipient recipient, String value) {
        recipient.setTelegramId(value);
        return recipient;
    }

    @Override
    public CommunicationType getType() {
        return CommunicationType.TELEGRAM;
    }
}
