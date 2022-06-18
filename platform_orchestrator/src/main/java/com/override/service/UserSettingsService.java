package com.override.service;

import com.override.model.PlatformUser;
import com.override.model.UserSettings;
import com.override.repository.UserSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSettingsService {

    @Autowired
    private UserSettingsRepository userSettingsRepository;

    @Autowired
    private PlatformUserService platformUserService;

    public void save(UserSettings userSettings, String login) {
        PlatformUser user = platformUserService.findPlatformUserByLogin(login);
        UserSettings settings = user.getUserSettings();
        userSettings.setId(settings.getId());
        userSettingsRepository.save(userSettings);
    }
}
