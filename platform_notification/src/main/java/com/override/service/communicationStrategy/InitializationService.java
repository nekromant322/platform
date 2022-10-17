package com.override.service.communicationStrategy;

import com.github.javafaker.Faker;
import com.override.service.RecipientService;
import dto.RecipientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Profile("dev")
public class InitializationService {

    @Autowired
    private RecipientService recipientService;

    @PostConstruct
    private void init() {
        createRecipientDTO();
    }

    public void createRecipientDTO() {
        RecipientDTO recipientDTO = RecipientDTO.builder()
                .login(new Faker().name().firstName())
                .email("test@yandex.ru")
                .telegramId("test")
                .phoneNumber("79998887766")
                .build();
        recipientService.save(recipientDTO);
    }
}