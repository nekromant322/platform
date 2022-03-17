package com.override.mappers;

import com.override.models.JoinRequest;
import dtos.RegisterUserRequestDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JoinRequestMapperTest {

    @InjectMocks
    private JoinRequestMapper requestMapper;

    @Test
    public void testDtoToEntity() {
        RegisterUserRequestDTO expectedDto = RegisterUserRequestDTO.builder()
                .chatId("1234")
                .telegramUserName("name")
                .build();

        JoinRequest actualEntity = requestMapper.dtoToEntity(expectedDto);

        Assertions.assertEquals(expectedDto.getChatId(), actualEntity.getChatId());
        Assertions.assertEquals(expectedDto.getTelegramUserName(), actualEntity.getNickName());
    }
}