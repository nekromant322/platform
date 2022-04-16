package com.override.service;

import com.override.mappers.RecipientMapper;
import com.override.models.Recipient;
import com.override.repositories.RecipientRepository;
import dtos.RecipientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipientService {

    @Autowired
    private RecipientRepository repository;
    @Autowired
    private RecipientMapper recipientMapper;

    public void save(RecipientDTO recipientDTO) {
        repository.save(recipientMapper.dtoToEntity(recipientDTO));
    }

    public void save(Recipient recipient) {
        repository.save(recipient);
    }

    public Recipient getRecipientByLogin(String login) {
        return repository.getRecipientByLogin(login);
    }
}
