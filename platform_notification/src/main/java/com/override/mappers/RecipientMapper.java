package com.override.mappers;

import com.override.models.Recipient;
import dtos.RecipientDTO;
import org.springframework.stereotype.Component;

@Component
public class RecipientMapper {

    public RecipientDTO entityToDto(Recipient recipient) {
        return RecipientDTO.builder()
                .login(recipient.getLogin())
                .email(recipient.getEmail())
                .phoneNumber(recipient.getPhoneNumber())
                .telegramId(recipient.getTelegramId())
                .vkId(recipient.getVkId())
                .build();
    }

    public Recipient dtoToEntity(RecipientDTO recipientDTO) {
        return Recipient.builder()
                .login(recipientDTO.getLogin())
                .email(recipientDTO.getEmail())
                .phoneNumber(recipientDTO.getPhoneNumber())
                .telegramId(recipientDTO.getTelegramId())
                .vkId(recipientDTO.getVkId())
                .build();
    }
}
