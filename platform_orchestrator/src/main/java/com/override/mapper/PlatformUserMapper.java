package com.override.mapper;

import com.override.model.PlatformUser;
import dto.PlatformUserDTO;
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
