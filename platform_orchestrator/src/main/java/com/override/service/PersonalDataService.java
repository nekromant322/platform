package com.override.service;

import com.override.repositories.PersonalDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalDataService {

    @Autowired
    PersonalDataRepository personalDataRepository;
}
