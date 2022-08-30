package com.override.service;

import com.override.exception.UnupdatableDataException;
import com.override.mapper.PersonalDataMapper;
import com.override.model.PersonalData;
import com.override.model.PlatformUser;
import com.override.model.RequestPersonalData;
import com.override.repository.PersonalDataRepository;
import com.override.repository.RequestPersonalDataRepository;
import com.override.util.UnupdatableFieldChecker;
import dto.PersonalDataDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.override.utils.TestFieldsUtil.generatePersonalData;
import static com.override.utils.TestFieldsUtil.generatePersonalDataDTO;
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

    @Mock
    private PersonalDataMapper personalDataMapper;

    @Test
    public void saveTest() {

        PersonalDataDTO newPersonalDataDTO = generatePersonalDataDTO();
        PersonalData newPersonalData = generatePersonalData();

        PersonalData existingPersonalData = new PersonalData();
        PlatformUser platformUser = new PlatformUser();
        platformUser.setPersonalData(existingPersonalData);

        when(personalDataMapper.dtoToEntity(newPersonalDataDTO)).thenReturn(newPersonalData);
        when(platformUserService.findPlatformUserByLogin("login")).thenReturn(platformUser);

        personalDataService.save(newPersonalDataDTO, "login");

        verify(personalDataRepository, times(1)).save(newPersonalData);

    }

    @Test
    public void saveUpdatableFieldsTest() {

        PersonalDataDTO newPersonalDataDTO = generatePersonalDataDTO();
        PersonalData newPersonalData = generatePersonalData();

        PersonalData existingPersonalData = new PersonalData();
        PlatformUser platformUser = new PlatformUser();
        platformUser.setPersonalData(existingPersonalData);

        when(personalDataMapper.dtoToEntity(newPersonalDataDTO)).thenReturn(newPersonalData);
        when(platformUserService.findPlatformUserByLogin("login")).thenReturn(platformUser);
        when(checker.executeCheckOfFillingInField(existingPersonalData, newPersonalData))
                .thenReturn(false);

        personalDataService.saveOrCreateRequest(newPersonalDataDTO, "login");

        verify(checker, times(1))
                .executeCheckOfFillingInField(existingPersonalData, newPersonalData);
        verify(checker, times(1))
                .executeCheckOfFieldChanges(existingPersonalData, newPersonalData);
        verify(personalDataRepository, times(1)).save(newPersonalData);

    }

    @Test
    public void createRequestTest() {

        PersonalDataDTO newPersonalDataDTO = generatePersonalDataDTO();
        PersonalData newPersonalData = new PersonalData();
        RequestPersonalData requestPersonalData = new RequestPersonalData();

        PersonalData existingPersonalData = new PersonalData();
        PlatformUser platformUser = new PlatformUser();
        platformUser.setPersonalData(existingPersonalData);

        when(personalDataMapper.dtoToEntity(newPersonalDataDTO)).thenReturn(newPersonalData);
        when(personalDataMapper.dataToRequest(newPersonalData)).thenReturn(requestPersonalData);
        when(platformUserService.findPlatformUserByLogin("login")).thenReturn(platformUser);
        when(checker.executeCheckOfFillingInField(existingPersonalData, newPersonalData))
                .thenReturn(true);

        personalDataService.saveOrCreateRequest(newPersonalDataDTO, "login");

        verify(checker, times(1))
                .executeCheckOfFillingInField(existingPersonalData, newPersonalData);
        verify(requestPersonalDataRepository, times(1)).save(requestPersonalData);

    }

    @Test
    public void exceptionExpectedTest() throws UnupdatableDataException {

        PersonalData existingPersonalData = new PersonalData();
        PlatformUser platformUser = new PlatformUser();
        platformUser.setPersonalData(existingPersonalData);

        PersonalDataDTO newPersonalDataDTO = generatePersonalDataDTO();
        PersonalData newPersonalData = new PersonalData();

        when(personalDataMapper.dtoToEntity(newPersonalDataDTO)).thenReturn(newPersonalData);
        when(platformUserService.findPlatformUserByLogin("login")).thenReturn(platformUser);
        when(checker.executeCheckOfFillingInField(existingPersonalData, newPersonalData))
                .thenReturn(false);

        doThrow(UnupdatableDataException.class)
                .when(checker).executeCheckOfFieldChanges(any(), any());

        assertThrows(UnupdatableDataException.class,
                () -> personalDataService.saveOrCreateRequest(newPersonalDataDTO, "login"));

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

        PersonalDataDTO personalDataDTO = generatePersonalDataDTO();

        personalDataService.deleteRequestToCheck(personalDataDTO);

        verify(requestPersonalDataRepository, times(1))
                .deleteById(personalDataDTO.getId());

    }

}
