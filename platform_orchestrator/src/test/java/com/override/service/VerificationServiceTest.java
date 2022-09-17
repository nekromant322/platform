package com.override.service;

import com.override.feign.NotificatorFeign;
import com.override.mapper.PersonalDataMapper;
import com.override.model.PersonalData;
import com.override.model.PlatformUser;
import com.override.repository.PersonalDataRepository;
import com.override.repository.PlatformUserRepository;
import dto.MailDTO;
import dto.PersonalDataDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.CacheManager;

import java.util.Optional;

import static com.override.utils.TestFieldsUtil.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VerificationServiceTest {

    @InjectMocks
    private VerificationService verificationService;

    @Mock
    private NotificatorFeign notificatorFeign;

    @Mock
    private PersonalDataRepository personalDataRepository;

    @Mock
    private PlatformUserRepository platformUserRepository;

    @Mock
    private RequestInNotificationService requestInNotificationService;

    @Mock
    private PersonalDataMapper personalDataMapper;

    @Test
    public void testGetCodeCallSecurity() {
        verificationService.getCodeCallSecurity("89998887766");
        verify(notificatorFeign, times(1)).callToClient("89998887766");
    }

    @Test
    public void testGetCodeEmailSecurity() {
        verificationService.getCodeEmailSecurity("fraerok777@mail.ru");
        verify(notificatorFeign, times(1)).sendMessageByMail(any(MailDTO.class));
    }

    @Test
    public void testSavePhoneNumberAndEmail(){
        PlatformUser user = generateTestUser();
        PersonalData personalData = generatePersonalData();
        Optional<PersonalData> optionalPersonalData = Optional.of(personalData);
        PersonalDataDTO personalDataDTO = generatePersonalDataDTO();
        when(platformUserRepository.findByLogin(user.getLogin())).thenReturn(user);
        when(personalDataRepository.findById(user.getId())).thenReturn(optionalPersonalData);
        when(personalDataMapper.entityToDto(personalData)).thenReturn(personalDataDTO);

        verificationService.savePhoneNumberAndEmail(user.getLogin(), personalData.getEmail(), personalData.getPhoneNumber());

        verify(personalDataRepository, times(1)).save(personalData);
        verify(requestInNotificationService, times(1)).saveRecipient(personalDataDTO, user.getLogin());
    }
}
