package com.override.mappers;

import com.override.models.JoinRequest;
import dtos.RegisterUserRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class JoinRequestMapper {

    public JoinRequest dtoToEntity(RegisterUserRequestDTO requestDTO) {
        return new JoinRequest(null, requestDTO.getTelegramUserName(), requestDTO.getChatId());
    }
}
