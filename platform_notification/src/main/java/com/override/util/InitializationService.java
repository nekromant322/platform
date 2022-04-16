package com.override.util;

import com.override.models.Recipient;
import com.override.service.RecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitializationService {

    @Autowired
    private RecipientService recipientService;

    @PostConstruct
    private void init() {
        createRecipient();
    }

    public void createRecipient() {
        Recipient recipient = new Recipient(
                null,
                "admin",
                "1234",
                "1234",
                "1234",
                "1234"
        );
        recipientService.save(recipient);
    }

}