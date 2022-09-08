package com.override.service;

import com.override.feign.NotificatorFeign;
import com.override.mapper.PersonalDataMapper;
import com.override.model.PersonalData;
import com.override.repository.PersonalDataRepository;
import com.override.repository.PlatformUserRepository;
import dto.PersonalDataDTO;
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

    @Autowired
    PersonalDataRepository personalDataRepository;

    @Autowired
    PlatformUserRepository platformUserRepository;

    @Autowired
    RequestInNotificationService requestInNotificationService;

    @Autowired
    PersonalDataMapper personalDataMapper;

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

    public boolean getCodePhone(String code, String phone) {
        Cache data = cacheManager.getCache("codeCallSecurity");
        assert data != null;
        Cache.ValueWrapper cacheCode = data.get(phone);
        assert cacheCode != null;
        String userCode = (String) cacheCode.get();
        return code.equals(userCode);
    }

    public boolean getCodeEmail(String code, String email) {
        Cache data = cacheManager.getCache("codeEmailMessageSecurity");
        assert data != null;
        Cache.ValueWrapper cacheCode = data.get(email);
        assert cacheCode != null;
        String userCode = (String) cacheCode.get();
        return code.equals(userCode);
    }

    public void savePhoneNumberAndEmail(String login, String email, Long phoneNumber) {
        if (personalDataRepository.findById(platformUserRepository.findByLogin(login).getId()).isPresent()) {
            PersonalData personalData = personalDataRepository.findById(platformUserRepository.findByLogin(login).getId()).get();
            personalData.setEmail(email);
            personalData.setPhoneNumber(phoneNumber);
            personalDataRepository.save(personalData);
            PersonalDataDTO personalDataDTO = personalDataMapper.entityToDto(personalData);
            requestInNotificationService.saveRecipient(personalDataDTO, login);
        }
    }
}
