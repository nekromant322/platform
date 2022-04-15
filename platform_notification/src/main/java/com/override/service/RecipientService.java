package com.override.service;

import com.override.models.Recipient;
import com.override.repositories.RecipientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipientService {

    @Autowired
    private RecipientRepository repository;

    public void save(Recipient recipient) {
        repository.save(recipient);
    }

    public Recipient getRecipientByLogin(String login) {
        return repository.getRecipientByLogin(login);
    }
}
