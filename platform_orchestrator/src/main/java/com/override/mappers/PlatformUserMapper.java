package com.override.mappers;

import com.override.models.PlatformUser;
import dtos.PlatformUserDTO;
import org.springframework.stereotype.Component;

@Component
public class PlatformUserMapper {

    public PlatformUserDTO entityToDto(PlatformUser account) {
        return PlatformUserDTO.builder()
                .login(account.getLogin())
                .password(account.getPassword())
                .build();
    }
}
