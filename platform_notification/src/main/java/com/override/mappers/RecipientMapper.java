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
        return new Recipient(
                null,
                recipientDTO.getLogin(),
                recipientDTO.getEmail(),
                recipientDTO.getPhoneNumber(),
                recipientDTO.getTelegramId(),
                recipientDTO.getVkId()
        );
    }
}
