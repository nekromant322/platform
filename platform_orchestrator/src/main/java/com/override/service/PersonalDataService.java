package com.override.service;


import com.override.model.PersonalData;
import com.override.model.PlatformUser;
import com.override.repository.PersonalDataRepository;
import com.override.util.CheckerUnupdatableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalDataService {

    @Autowired
    private CheckerUnupdatableField<PersonalData> checkerUnupdatableField;

    @Autowired
    private PersonalDataRepository personalDataRepository;

    @Autowired
    private PlatformUserService platformUserService;

    public void save(PersonalData newPersonalData, String login) {
        PlatformUser user = platformUserService.findPlatformUserByLogin(login);
        PersonalData currentPersonalData = user.getPersonalData();

        checkerUnupdatableField.executeCheck(currentPersonalData, newPersonalData);

        newPersonalData.setId(currentPersonalData.getId());
        personalDataRepository.save(newPersonalData);
    }

}
