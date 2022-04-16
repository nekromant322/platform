package com.override.service;

import com.override.mappers.RecipientMapper;
import com.override.models.Recipient;
import com.override.repositories.RecipientRepository;
import dtos.RecipientDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RecipientService {

    @Autowired
    private RecipientRepository repository;
    @Autowired
    private RecipientMapper recipientMapper;

    public void save(RecipientDTO recipientDTO) {
        if (repository.getRecipientByLogin(recipientDTO.getLogin()) != null) {
            log.info("Пользователь с логином {} уже есть в системе", recipientDTO.getLogin());
        } else {
            repository.save(recipientMapper.dtoToEntity(recipientDTO));
        }
    }

    public void save(Recipient recipient) {
        if (repository.getRecipientByLogin(recipient.getLogin()) != null) {
            log.info("Пользователь с логином {} уже есть в системе", recipient.getLogin());
        } else {
            repository.save(recipient);
        }
    }

    public void delete(RecipientDTO recipientDTO) {
        repository.delete(recipientMapper.dtoToEntity(recipientDTO));
    }

    public void delete(Recipient recipient) {
        repository.delete(recipient);
    }

    public Recipient getRecipientByLogin(String login) {
        return repository.getRecipientByLogin(login);
    }
}
