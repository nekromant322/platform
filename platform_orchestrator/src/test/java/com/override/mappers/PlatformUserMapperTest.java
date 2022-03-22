package com.override.mappers;

import com.override.models.PlatformUser;
import dtos.PlatformUserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.override.utils.TestFieldsUtil.generateTestUser;


@ExtendWith(MockitoExtension.class)
class PlatformUserMapperTest {

    @InjectMocks
    private PlatformUserMapper platformUserMapper;

    @Test
    public void testEntityToDto() {
        PlatformUser user = generateTestUser();

        PlatformUserDTO platformUserDTO = platformUserMapper.entityToDto(user);

        Assertions.assertEquals(platformUserDTO.getLogin(), user.getLogin());
        Assertions.assertEquals(platformUserDTO.getPassword(), user.getPassword());
        Assertions.assertEquals(platformUserDTO.getTelegramChatId(), user.getTelegramChatId());
    }
}