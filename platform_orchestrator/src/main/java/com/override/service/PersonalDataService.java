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

        //checker.executeCheck(currentPersonalData, newPersonalData);

        newPersonalData.setId(currentPersonalData.getId());
        personalDataRepository.save(newPersonalData);

    }

    public void saveOrSendToReview(PersonalData newPersonalData, String login) {

        PlatformUser user = platformUserService.findPlatformUserByLogin(login);
        PersonalData currentPersonalData = user.getPersonalData();

        newPersonalData.setId(currentPersonalData.getId());

        if(checker.executeCheckOfFillingInField(currentPersonalData, newPersonalData)) {

            RequestPersonalData requestPersonalData = new RequestPersonalData(newPersonalData);
            requestPersonalDataRepository.save(requestPersonalData);
            /*======================================================*/
            System.out.println("Create new request to check personal data changes");
            /*======================================================*/

        } else {

            //checker.executeCheck(currentPersonalData, newPersonalData);
            personalDataRepository.save(newPersonalData);
            /*======================================================*/
            System.out.println("Save new personal data");
            /*======================================================*/

        }

    }

    public RequestPersonalData findRequestPersonalDataByLogin(String userLogin) {

        PlatformUser user = platformUserService.findPlatformUserByLogin(userLogin);
        PersonalData currentPersonalData = user.getPersonalData();

        return requestPersonalDataRepository.findById(currentPersonalData.getId()).get();

    }

    public void deleteRequestToCheck(PersonalData personalData) {
        requestPersonalDataRepository.deleteById(personalData.getId());
    }
}
