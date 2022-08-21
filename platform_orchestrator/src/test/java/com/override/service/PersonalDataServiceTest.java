package com.override.service;

import com.override.exception.UnupdatableDataException;
import com.override.model.PersonalData;
import com.override.model.PlatformUser;
import com.override.model.RequestPersonalData;
import com.override.repository.PersonalDataRepository;
import com.override.repository.RequestPersonalDataRepository;
import com.override.util.UnupdatableFieldChecker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private RequestPersonalDataRepository requestPersonalDataRepository;

    @Mock
    private UnupdatableFieldChecker<PersonalData> checker;

    @Test
    public void saveTest() {

        PersonalData newPersonalData = new PersonalData();

        PersonalData existingPersonalData = new PersonalData();
        PlatformUser platformUser = new PlatformUser();
        platformUser.setPersonalData(existingPersonalData);

        when(platformUserService.findPlatformUserByLogin("login")).thenReturn(platformUser);

        personalDataService.save(newPersonalData, "login");

        verify(personalDataRepository, times(1)).save(newPersonalData);

    }

    @Test
    public void saveUpdatableFieldsTest() {

        PersonalData newPersonalData = new PersonalData();

        PersonalData existingPersonalData = new PersonalData();
        PlatformUser platformUser = new PlatformUser();
        platformUser.setPersonalData(existingPersonalData);

        when(platformUserService.findPlatformUserByLogin("login")).thenReturn(platformUser);
        when(checker.executeCheckOfFillingInField(existingPersonalData, newPersonalData))
                .thenReturn(false);

        personalDataService.saveOrSendToCheck(newPersonalData, "login");

        verify(checker, times(1))
                .executeCheckOfFillingInField(existingPersonalData, newPersonalData);
        verify(checker, times(1))
                .executeCheckOfFieldChanges(existingPersonalData, newPersonalData);
        verify(personalDataRepository, times(1)).save(newPersonalData);

    }

    @Test
    public void sendToCheckTest() {

        PersonalData newPersonalData = new PersonalData();
        RequestPersonalData requestPersonalData = new RequestPersonalData(newPersonalData);

        PersonalData existingPersonalData = new PersonalData();
        PlatformUser platformUser = new PlatformUser();
        platformUser.setPersonalData(existingPersonalData);

        when(platformUserService.findPlatformUserByLogin("login")).thenReturn(platformUser);
        when(checker.executeCheckOfFillingInField(existingPersonalData, newPersonalData))
                .thenReturn(true);

        personalDataService.saveOrSendToCheck(newPersonalData, "login");

        verify(checker, times(1))
                .executeCheckOfFillingInField(existingPersonalData, newPersonalData);
        verify(requestPersonalDataRepository, times(1)).save(requestPersonalData);

    }

    @Test
    public void exceptionExpectedTest() throws UnupdatableDataException {

        PersonalData existingPersonalData = new PersonalData();
        PlatformUser platformUser = new PlatformUser();
        platformUser.setPersonalData(existingPersonalData);

        PersonalData newPersonalData = new PersonalData();

        when(platformUserService.findPlatformUserByLogin("login")).thenReturn(platformUser);
        when(checker.executeCheckOfFillingInField(existingPersonalData, newPersonalData))
                .thenReturn(false);

        doThrow(UnupdatableDataException.class)
                .when(checker).executeCheckOfFieldChanges(any(), any());

        assertThrows(UnupdatableDataException.class,
                () -> personalDataService.saveOrSendToCheck(newPersonalData, "login"));

    }

    @Test
    public void findRequestPersonalDataByLoginTest() {

        RequestPersonalData notNullRequestPersonalData = new RequestPersonalData();

        PlatformUser platformUser = new PlatformUser();
        PersonalData personalData = new PersonalData();
        personalData.setId(1L);
        platformUser.setPersonalData(personalData);

        when(platformUserService.findPlatformUserByLogin("login")).thenReturn(platformUser);
        when(requestPersonalDataRepository.findById(1L))
                .thenReturn(Optional.of(notNullRequestPersonalData));

        RequestPersonalData requestPersonalData = personalDataService
                .findRequestPersonalDataByLogin("login");

        assertEquals(notNullRequestPersonalData, requestPersonalData);

    }

    @Test
    public void deleteRequestToCheckTest() {

        PersonalData personalData = new PersonalData();

        personalDataService.deleteRequestToCheck(personalData);

        verify(requestPersonalDataRepository, times(1))
                .deleteById(personalData.getId());

    }

}
