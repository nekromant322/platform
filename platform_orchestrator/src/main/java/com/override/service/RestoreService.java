package com.override.service;

import com.override.feign.NotificatorFeign;
import com.override.model.PlatformUser;
import com.override.repository.PlatformUserRepository;
import dto.ChangePasswordDTO;
import enums.Communication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RestoreService {
    @Autowired
    private NotificatorFeign notificatorFeign;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private PlatformUserRepository platformUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @CachePut(value = "codeTelegramSecurity")
    public ChangePasswordDTO getCodeTelegramSecurity(String username) {
        return getTelegramCode(username);
    }

    public ChangePasswordDTO getTelegramCode(String username) {
        Random random = new Random();
        String code = Integer.toString(random.nextInt(9999));
        notificatorFeign.sendMessage(username, code, Communication.TELEGRAM);
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
        changePasswordDTO.setUsername(username);
        changePasswordDTO.setCode(code);
        return changePasswordDTO;
    }

    public boolean getCodeTelegram(String code, String username) {
        Cache data = cacheManager.getCache("codeTelegramSecurity");
        assert data != null;
        Cache.ValueWrapper cacheCode = data.get(username);
        assert cacheCode != null;
        ChangePasswordDTO changePasswordDTO = (ChangePasswordDTO) cacheCode.get();
        return code.equals(changePasswordDTO.getCode());
    }

    public void changePassword(ChangePasswordDTO changePasswordDTO) {
        PlatformUser platformUser = platformUserRepository.findFirstByLogin(changePasswordDTO.getUsername());
        platformUser.setPassword(passwordEncoder.encode(changePasswordDTO.getPassword()));
        platformUserRepository.save(platformUser);
    }

}
