package com.override.service;

import com.override.feign.NotificatorFeign;
import com.override.model.PlatformUser;
import com.override.repository.PlatformUserRepository;
import dto.ChangePasswordDTO;
import enums.Communication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RestoreService {

    private final String MESSAGE_WITH_CODE = "Ваш разовый код: %s.\nНикому не сообщайте этот код.";

    @Autowired
    private NotificatorFeign notificatorFeign;

    @Autowired
    @Qualifier("getRestoreCacheManager")
    private CacheManager cacheManager;

    @Autowired
    private PlatformUserRepository platformUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void getCodeTelegramSecurity(String username, String code) {
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
        changePasswordDTO.setUsername(username);
        changePasswordDTO.setCode(code);
        Cache data = cacheManager.getCache("codeTelegramSecurity");
        data.put(username, changePasswordDTO);
    }

    public void sendSecurityCode(String username) {
        Random random = new Random();
        String code = Integer.toString(random.nextInt(9999));
        notificatorFeign.sendMessage(username, String.format(MESSAGE_WITH_CODE, code), Communication.TELEGRAM);
        getCodeTelegramSecurity(username, code);
    }

    public boolean isEqualCodeTelegram(String code, String username) {
        Cache data = cacheManager.getCache("codeTelegramSecurity");
        Cache.ValueWrapper cacheCode = data.get(username);
        ChangePasswordDTO changePasswordDTO = (ChangePasswordDTO) cacheCode.get();
        return code.equals(changePasswordDTO.getCode());
    }

    public void changePassword(ChangePasswordDTO changePasswordDTO) {
        PlatformUser platformUser = platformUserRepository.findFirstByLogin(changePasswordDTO.getUsername());
        platformUser.setPassword(passwordEncoder.encode(changePasswordDTO.getPassword()));
        platformUserRepository.save(platformUser);
    }
}
