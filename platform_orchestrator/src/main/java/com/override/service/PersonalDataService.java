package com.override.service;

import com.override.mapper.PersonalDataMapper;
import com.override.model.PersonalData;
import com.override.model.PlatformUser;
import com.override.model.RequestPersonalData;
import com.override.repository.PersonalDataRepository;
import com.override.repository.RequestPersonalDataRepository;
import com.override.util.UnupdatableFieldChecker;
import dto.PersonalDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalDataService {

    @Autowired
    private UnupdatableFieldChecker<PersonalData> checker;

    @Autowired
    private PersonalDataMapper personalDataMapper;

    @Autowired
    private PersonalDataRepository personalDataRepository;

    @Autowired
    private RequestPersonalDataRepository requestPersonalDataRepository;

    @Autowired
    private PlatformUserService platformUserService;

    public void save(PersonalDataDTO newPersonalDataDTO, String login) {

        PlatformUser user = platformUserService.findPlatformUserByLogin(login);
        PersonalData currentPersonalData = user.getPersonalData();

        PersonalData newPersonalData = personalDataMapper.dtoToEntity(newPersonalDataDTO);
        newPersonalData.setId(currentPersonalData.getId());

        personalDataRepository.save(newPersonalData);
    }

    public void saveOrCreateRequest(PersonalDataDTO newPersonalDataDTO, String login) {

        PlatformUser user = platformUserService.findPlatformUserByLogin(login);
        PersonalData currentPersonalData = user.getPersonalData();

        PersonalData newPersonalData = personalDataMapper.dtoToEntity(newPersonalDataDTO);
        newPersonalData.setId(currentPersonalData.getId());

        if(checker.executeCheckOfFillingInField(currentPersonalData, newPersonalData)) {
            createRequest(newPersonalData);
        } else {
            checker.executeCheckOfFieldChanges(currentPersonalData, newPersonalData);
            save(newPersonalData);
        }
    }

    public RequestPersonalData findRequestPersonalDataByLogin(String userLogin) {

        PlatformUser user = platformUserService.findPlatformUserByLogin(userLogin);
        PersonalData currentPersonalData = user.getPersonalData();

        return requestPersonalDataRepository.findById(currentPersonalData.getId())
                .orElse(new RequestPersonalData());
    }

    public void deleteRequestToCheck(PersonalDataDTO personalDataDTO) {
        requestPersonalDataRepository.deleteById(personalDataDTO.getId());
    }

    private void save(PersonalData personalData) {
        personalDataRepository.save(personalData);
    }

    private void createRequest(PersonalData personalData) {
        RequestPersonalData requestPersonalData = personalDataMapper.dataToRequest(personalData);
        requestPersonalDataRepository.save(requestPersonalData);
    }
}
