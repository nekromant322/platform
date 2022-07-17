package com.override.mapper;

import com.override.model.JoinRequest;
import dto.RegisterUserRequestDTO;
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
    public void testWhenDtoToEntity() {
        RegisterUserRequestDTO expectedDto = RegisterUserRequestDTO.builder()
                .telegramUserName("name")
                .build();

        JoinRequest actualEntity = requestMapper.dtoToEntity(expectedDto);

        assertEquals(expectedDto.getTelegramUserName(), actualEntity.getNickName());
    }
}