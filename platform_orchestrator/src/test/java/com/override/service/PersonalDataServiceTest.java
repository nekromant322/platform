package com.override.service;

import com.override.model.PersonalData;
import com.override.model.PlatformUser;
import com.override.repository.PersonalDataRepository;
import com.override.util.CheckerUnupdatableField;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PersonalDataServiceTest {

    @InjectMocks
    private PersonalDataService personalDataService;

    @Mock
    private PlatformUserService platformUserService;

    @Mock
    private PersonalDataRepository personalDataRepository;

    @Mock
    private CheckerUnupdatableField<PersonalData> checker;

    @Test
    public void saveTest() {
        PersonalData personalData = new PersonalData();

        PlatformUser platformUser = new PlatformUser();
        platformUser.setPersonalData(personalData);

        when(platformUserService.findPlatformUserByLogin("login")).thenReturn(platformUser);

        personalDataService.save(personalData, "login");

        verify(personalDataRepository, times(1)).save(personalData);
    }
}
