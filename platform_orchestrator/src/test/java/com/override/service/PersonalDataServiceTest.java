package com.override.service;

import com.override.exception.UnupdatableDataException;
import com.override.model.PersonalData;
import com.override.model.PlatformUser;
import com.override.repository.PersonalDataRepository;
import com.override.util.UnupdatableFieldChecker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
    private UnupdatableFieldChecker<PersonalData> unupdatableFieldChecker;

    @Test
    public void saveTest() {

        PersonalData newPersonalData = new PersonalData();

        PersonalData existingPersonalData = new PersonalData();
        PlatformUser platformUser = new PlatformUser();
        platformUser.setPersonalData(existingPersonalData);

        when(platformUserService.findPlatformUserByLogin("login")).thenReturn(platformUser);

        personalDataService.save(newPersonalData, "login");

        verify(unupdatableFieldChecker, times(1)).executeCheck(platformUser.getPersonalData(), newPersonalData);
        verify(personalDataRepository, times(1)).save(newPersonalData);

    }

    @Test
    public void exceptionExpectedTest() throws UnupdatableDataException {

        PersonalData existingPersonalData = new PersonalData();
        PlatformUser platformUser = new PlatformUser();
        platformUser.setPersonalData(existingPersonalData);

        PersonalData newPersonalData = new PersonalData();

        when(platformUserService.findPlatformUserByLogin("login")).thenReturn(platformUser);

        doThrow(UnupdatableDataException.class)
                .when(unupdatableFieldChecker)
                .executeCheck(any(), any());

        assertThrows(UnupdatableDataException.class, () -> personalDataService.save(newPersonalData, "login"));

    }
}
