package com.override.mapper;

import com.override.model.PlatformUser;
import dto.PlatformUserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.override.utils.TestFieldsUtil.generateTestUser;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class PlatformUserMapperTest {

    @InjectMocks
    private PlatformUserMapper platformUserMapper;

    @Test
    public void testEntityToDto() {
        PlatformUser user = generateTestUser();

        PlatformUserDTO platformUserDTO = platformUserMapper.entityToDto(user);

        assertEquals(platformUserDTO.getLogin(), user.getLogin());
        assertEquals(platformUserDTO.getPassword(), user.getPassword());
    }
}