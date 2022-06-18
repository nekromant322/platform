package com.override.service;

import com.override.model.PersonalData;
import com.override.model.PlatformUser;
import com.override.repository.PersonalDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalDataService {

    @Autowired
    private PersonalDataRepository personalDataRepository;


    @Autowired
    private PlatformUserService platformUserService;

    public void save(PersonalData personalData, String login) {
        PlatformUser user = platformUserService.findPlatformUserByLogin(login);
        PersonalData data = user.getPersonalData();
        personalData.setId(data.getId());
        personalDataRepository.save(personalData);
    }
}
