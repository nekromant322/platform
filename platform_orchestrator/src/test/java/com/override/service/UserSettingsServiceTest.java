package com.override.service;

import com.override.model.PlatformUser;
import com.override.model.UserSettings;
import com.override.repository.UserSettingsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.override.utils.TestFieldsUtil.generateTestUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserSettingsServiceTest {

    @InjectMocks
    private UserSettingsService userSettingsService;

    @Mock
    private UserSettingsRepository userSettingsRepository;

    @Mock
    private PlatformUserService platformUserService;

    @Test
    public void testSave() {
        PlatformUser user = generateTestUser();
        UserSettings settings = new UserSettings(null, true, true);

        when(platformUserService.findPlatformUserByLogin(user.getLogin()))
                .thenReturn(user);

        userSettingsService.save(settings, user.getLogin());
        verify(userSettingsRepository, times(1)).save(any());
    }
}
