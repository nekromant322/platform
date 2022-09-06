package com.override.service;

import com.override.feign.NotificatorFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class VerificationService {

    @Autowired
    NotificatorFeign notificatorFeign;

    @Autowired
    CacheManager cacheManager;

    @Autowired
    DefaultEmailService emailService;

    @CachePut(value="codeCallSecurity")
    public String getCodeCallSecurity(String phone) {
        return notificatorFeign.callToClient(phone);
    }

    @CachePut(value="codeEmailMessageSecurity")
    public String getCodeEmailSecurity(String email) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 4; i++) {
            int numberForCode = (int)(Math.random() * 10);
            sb.append(numberForCode);
        }
        String code = sb.toString();
        emailService.sendSimpleEmail(email, "Код подтверждения", "Ваш код подтверждения - " + code);
        return code;
    }

    public boolean getCodePhone(String code) {
        String phone = "89996247571";
        Cache data = cacheManager.getCache("codeCallSecurity");
        assert data != null;
        Cache.ValueWrapper cacheCode = data.get(phone);
        assert cacheCode != null;
        String userCode = (String) cacheCode.get();
        if (code.equals(userCode)) {
            return true;
        }
        return false;
    }

    public boolean getCodeEmail(String code) {
        String email = "evlantjev.artur2012@yandex.ru";
        Cache data = cacheManager.getCache("codeEmailMessageSecurity");
        assert data != null;
        Cache.ValueWrapper cacheCode = data.get(email);
        assert cacheCode != null;
        String userCode = (String) cacheCode.get();
        if (code.equals(userCode)) {
            return true;
        }
        return false;
    }
}
