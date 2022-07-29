package com.override.service;

import com.override.annotation.Unupdatable;
import com.override.exception.UnupdatableDataException;
import com.override.model.PersonalData;
import com.override.repository.PersonalDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
public class PersonalDataService {

    @Autowired
    private PersonalDataRepository personalDataRepository;


    @Autowired
    private PlatformUserService platformUserService;

    public void save(PersonalData newPersonalData, String login) {
        PersonalData oldPersonalData = platformUserService.findPlatformUserByLogin(login).getPersonalData();
        if(isUpdateLockedData(oldPersonalData, newPersonalData)) {
            throw new UnupdatableDataException("Attempt to change data in the locked field");
        }
        newPersonalData.setId(oldPersonalData.getId());
        personalDataRepository.save(newPersonalData);
    }

    private boolean isUpdateLockedData(PersonalData oldData, PersonalData newData) {

        try {
            for(Field field : oldData.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Unupdatable.class)) {
                    field.setAccessible(true);
                    if((field.get(oldData) != null) && (field.get(oldData) != field.get(newData))) { return true; }
                }
            }
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
