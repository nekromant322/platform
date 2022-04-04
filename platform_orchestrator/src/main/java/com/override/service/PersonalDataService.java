package com.override.service;

import com.override.mappers.PersonalDataMapper;
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

    public void save(PersonalDataDTO personalDataDTO) {
        PlatformUser platformUser = platformUserService.findPlatformUserByLogin(personalDataDTO.getLogin());
        platformUser.setPersonalData(personalDataMapper.dtoToEntity(personalDataDTO, platformUser));
        platformUserService.save(platformUser);
    }
}
