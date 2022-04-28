package com.override.service;

import com.override.models.PlatformUser;
import com.override.models.UserSettings;
import com.override.repositories.UserSettingsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.override.utils.TestFieldsUtil.generateTestUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserSettingsServiceTest {

    @InjectMocks
    private UserSettingsService userSettingsService;

    @Mock
    private UserSettingsRepository userSettingsRepository;

    @Mock
    private PlatformUserService platformUserService;

    @Test
    void save() {
        PlatformUser user = generateTestUser();
        UserSettings settings = new UserSettings(null, true, true);

        when(platformUserService.findPlatformUserByLogin(user.getLogin()))
                .thenReturn(user);

        userSettingsService.save(settings, user.getLogin());
        verify(userSettingsRepository, times(1)).save(any());
    }
}
