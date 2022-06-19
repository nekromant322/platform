package com.override.mapper;

import com.override.model.JoinRequest;
import dto.RegisterUserRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class JoinRequestMapper {

    public JoinRequest dtoToEntity(RegisterUserRequestDTO requestDTO) {
        return new JoinRequest(null, requestDTO.getTelegramUserName(), requestDTO.getChatId());
    }
}
