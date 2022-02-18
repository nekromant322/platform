package com.override.mappers;

import com.override.models.JoinRequest;
import dtos.RegisterStudentRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class JoinRequestMapper {

    public JoinRequest dtoToEntity(RegisterStudentRequestDTO requestDTO) {
        return new JoinRequest(null, requestDTO.getTelegramUserName(), requestDTO.getChatId());
    }

    public RegisterStudentRequestDTO entityToDto(JoinRequest request) {
        return RegisterStudentRequestDTO.builder().telegramUserName(request.getNickName()).chatId(request.getChatId()).build();
    }
}
