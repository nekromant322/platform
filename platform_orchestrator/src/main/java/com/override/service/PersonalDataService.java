package com.override.service;

import com.override.mappers.PersonalDataMapper;
import com.override.models.PersonalData;
import com.override.models.PlatformUser;
import dtos.PersonalDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalDataService {

    @Autowired
    private PersonalDataMapper personalDataMapper;

    @Autowired
    private PlatformUserService platformUserService;

    public void save(PersonalData personalData, String login) {
        PlatformUser platformUser = platformUserService.findPlatformUserByLogin(login);
        platformUser.setPersonalData(personalData);
        platformUserService.save(platformUser);
    }
}
