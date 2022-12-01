package com.override.mapper;

import com.override.model.Recipient;
import dto.RecipientDTO;
import org.springframework.stereotype.Component;

@Component
public class RecipientMapper {

    public RecipientDTO entityToDto(Recipient recipient) {
        return RecipientDTO.builder()
                .login(recipient.getLogin())
                .email(recipient.getEmail().get())
                .telegramId(recipient.getTelegramId().get())
                .phoneNumber(recipient.getPhoneNumber().get())
                .vkChatId(recipient.getVkChatId().orElse("None"))
                .build();
    }

    public Recipient dtoToEntity(RecipientDTO recipientDTO) {
        return Recipient.builder()
                .login(recipientDTO.getLogin())
                .email(recipientDTO.getEmail())
                .telegramId(recipientDTO.getTelegramId())
                .phoneNumber(recipientDTO.getPhoneNumber())
                .vkChatId(recipientDTO.getVkChatId())
                .build();
    }
}
