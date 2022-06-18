package com.override.util;

import com.override.service.RecipientService;
import dto.RecipientDTO;
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
                .login("test")
                .email("test@yandex.ru")
                .telegramId("test")
                .phoneNumber("79998887766")
                .build();
        recipientService.save(recipientDTO);
    }

}