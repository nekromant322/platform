package com.override.util;

import com.override.model.Recipient;
import com.override.service.VkService;
import dto.MessageDTO;
import enums.Communication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VkCommunication implements CommunicationStrategy {

    @Autowired
    private VkService vkService;

    @Override
    public void sendMessage(Recipient recipient, String message) {
        vkService.sendMessage(MessageDTO.builder()
                .chatId(recipient.getVkChatId().orElseThrow(() -> new IllegalStateException("У пользователя " +
                        recipient.getLogin() + " не найден id Вк")))
                .message(message)
                .build());
    }

    @Override
    public Recipient setCommunication(Recipient recipient, String value) {
        return null;
    }

    @Override
    public Communication getType() {
        return Communication.VK;
    }
}
