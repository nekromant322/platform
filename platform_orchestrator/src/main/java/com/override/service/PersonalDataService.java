package com.override.service;

import com.override.mappers.PersonalDataMapper;
import com.override.repositories.PersonalDataRepository;
import dtos.PersonalDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalDataService {

    @Autowired
    private PersonalDataRepository personalDataRepository;

    @Autowired
    private PersonalDataMapper personalDataMapper;

    @Autowired
    private PlatformUserService platformUserService;

    public void save(PersonalDataDTO personalDataDTO) {
        personalDataRepository.save(personalDataMapper.dtoToEntity(personalDataDTO, platformUserService.findPlatformUserByLogin(personalDataDTO.getLogin())));
    }
}
