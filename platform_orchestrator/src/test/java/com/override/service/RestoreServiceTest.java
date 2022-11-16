package com.override.service;

import com.override.feign.NotificatorFeign;
import com.override.model.PlatformUser;
import com.override.repository.PlatformUserRepository;
import dto.ChangePasswordDTO;
import enums.Communication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestoreServiceTest {

    @InjectMocks
    private RestoreService restoreService;

    @Mock
    private NotificatorFeign notificatorFeign;

    @Mock
    private CacheManager cacheManager;

    @Mock
    private PlatformUserRepository platformUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void testGetTelegramCode() {
        restoreService.getTelegramCode("Artol7");
        verify(notificatorFeign, times(1)).sendMessage(eq("Artol7"), any(String.class), eq(Communication.TELEGRAM));
    }

    @Test
    public void testChangePassword() {
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
        changePasswordDTO.setPassword("12345");
        changePasswordDTO.setUsername("Artol7");
        PlatformUser platformUser = new PlatformUser();
        platformUser.setPassword("1234567");
        platformUser.setLogin("Artol7");
        when(platformUserRepository.findFirstByLogin(changePasswordDTO.getUsername())).thenReturn(platformUser);
        restoreService.changePassword(changePasswordDTO);
        verify(platformUserRepository, times(1)).save(any(PlatformUser.class));
    }
}
