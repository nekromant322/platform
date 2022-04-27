package com.override.mappers;

import com.override.models.JoinRequest;
import dtos.RegisterUserRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class JoinRequestMapperTest {

    @InjectMocks
    private JoinRequestMapper requestMapper;

    @Test
    public void testDtoToEntity() {
        RegisterUserRequestDTO expectedDto = RegisterUserRequestDTO.builder()
                .telegramUserName("name")
                .build();

        JoinRequest actualEntity = requestMapper.dtoToEntity(expectedDto);

        assertEquals(expectedDto.getTelegramUserName(), actualEntity.getNickName());
    }
}