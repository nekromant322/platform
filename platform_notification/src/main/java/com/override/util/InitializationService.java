package com.override.util;

import com.override.mappers.RecipientMapper;
import com.override.repositories.RecipientRepository;
import com.override.service.RecipientService;
import dtos.RecipientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitializationService {

    @Autowired
    private RecipientService recipientService;

    @PostConstruct
    private void init() {
        createRecipientDTO();
    }

    public void createRecipientDTO() {
        RecipientDTO recipientDTO = RecipientDTO.builder()
                .login("Joe")
                .email("joe")
                .telegramId("123")
                .build();
        recipientService.save(recipientDTO);
    }

}