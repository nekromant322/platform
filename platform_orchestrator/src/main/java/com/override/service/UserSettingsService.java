package com.override.service;

import com.override.feign.NotificatorFeign;
import com.override.model.PlatformUser;
import com.override.model.UserSettings;
import com.override.repository.UserSettingsRepository;
import dto.RecipientDTO;
import enums.Communication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSettingsService {

    @Autowired
    private UserSettingsRepository userSettingsRepository;

    @Autowired
    private PlatformUserService platformUserService;

    @Autowired
    private NotificatorFeign notificatorFeign;

    public void save(UserSettings userSettings, String login) {
        RecipientDTO recipientDTO = notificatorFeign.getRecipient(login);
        PlatformUser user = platformUserService.findPlatformUserByLogin(login);
        UserSettings settings = user.getUserSettings();
        userSettings.setId(settings.getId());
        if (recipientDTO.getVkChatId().equals("None") && userSettings.getVkNotification().equals(true)) {
            Integer res = notificatorFeign.getVkChatID(login);
            notificatorFeign.setCommunications(login, String.valueOf(res), Communication.VK);
        }
        userSettingsRepository.save(userSettings);
    }
}
