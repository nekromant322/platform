package com.override.service;

import com.override.model.PersonalData;
import com.override.model.PlatformUser;
import com.override.model.RequestPersonalData;
import com.override.repository.PersonalDataRepository;
import com.override.repository.RequestPersonalDataRepository;
import com.override.util.UnupdatableFieldChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalDataService {

    @Autowired
    private UnupdatableFieldChecker<PersonalData> checker;

    @Autowired
    private PersonalDataRepository personalDataRepository;

    @Autowired
    private RequestPersonalDataRepository requestPersonalDataRepository;

    @Autowired
    private PlatformUserService platformUserService;

    public void save(PersonalData newPersonalData, String login) {

        PlatformUser user = platformUserService.findPlatformUserByLogin(login);
        PersonalData currentPersonalData = user.getPersonalData();

        newPersonalData.setId(currentPersonalData.getId());

        personalDataRepository.save(newPersonalData);

    }

    public void saveOrSendToCheck(PersonalData newPersonalData, String login) {

        PlatformUser user = platformUserService.findPlatformUserByLogin(login);
        PersonalData currentPersonalData = user.getPersonalData();

        newPersonalData.setId(currentPersonalData.getId());

        if(checker.executeCheckOfFillingInField(currentPersonalData, newPersonalData)) {

            RequestPersonalData requestPersonalData = new RequestPersonalData(newPersonalData);
            requestPersonalDataRepository.save(requestPersonalData);

        } else {

            checker.executeCheckOfFieldChanges(currentPersonalData, newPersonalData);
            personalDataRepository.save(newPersonalData);

        }

    }

    public RequestPersonalData findRequestPersonalDataByLogin(String userLogin) {

        PlatformUser user = platformUserService.findPlatformUserByLogin(userLogin);
        PersonalData currentPersonalData = user.getPersonalData();

        return requestPersonalDataRepository.findById(currentPersonalData.getId())
                .orElse(new RequestPersonalData());
    }

    public void deleteRequestToCheck(PersonalData personalData) {
        requestPersonalDataRepository.deleteById(personalData.getId());
    }
}
