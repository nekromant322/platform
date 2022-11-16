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
import org.springframework.security.crypto.password.PasswordEncoder;

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
    private PlatformUserRepository platformUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void testSendSecurityCode() {
        restoreService.sendSecurityCode("Artol7");
        verify(notificatorFeign, times(1)).sendMessage(eq("Artol7"), any(String.class), eq(Communication.TELEGRAM));
    }

    @Test
    public void testChangePassword() {
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
        changePasswordDTO.setPassword("12345");
        changePasswordDTO.setUsername("Artol7");
        PlatformUser platformUser = new PlatformUser();
        platformUser.setLogin("Artol7");
        platformUser.setPassword("HZ");
        when(platformUserRepository.findFirstByLogin(changePasswordDTO.getUsername())).thenReturn(platformUser);
        when(passwordEncoder.encode(changePasswordDTO.getPassword())).thenReturn(changePasswordDTO.getPassword());
        restoreService.changePassword(changePasswordDTO);
        verify(platformUserRepository, times(1)).save(platformUser);
    }
}
