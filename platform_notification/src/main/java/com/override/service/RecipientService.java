package com.override.service;

import com.override.mappers.RecipientMapper;
import com.override.models.Recipient;
import com.override.repositories.RecipientRepository;
import com.override.util.CommunicationStrategy;
import com.override.util.CommunicationStrategyFactory;
import dtos.RecipientDTO;
import enums.Communication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class RecipientService {

    @Autowired
    private RecipientRepository repository;
    @Autowired
    private RecipientMapper recipientMapper;
    @Autowired
    private CommunicationStrategyFactory strategyFactory;

    public void save(RecipientDTO recipientDTO) {
        if (repository.findRecipientByLogin(recipientDTO.getLogin()) != null) {
            log.info("Пользователь с логином {} уже есть в системе", recipientDTO.getLogin());
        } else {
            repository.save(recipientMapper.dtoToEntity(recipientDTO));
        }
    }

    public void save(Recipient recipient) {
        if (repository.findRecipientByLogin(recipient.getLogin()) != null) {
            log.info("User with this login \"{}\" already exists", recipient.getLogin());
        } else {
            repository.save(recipient);
        }
    }

    public void updateCommunication(String login, String value, Communication type) {
        Recipient recipient = repository.findRecipientByLogin(login);
        Map<Communication, CommunicationStrategy> strategyMap = strategyFactory.getSenderMap();

        recipient = strategyMap.get(type).setCommunication(recipient, value);
        repository.save(recipient);
    }

    public void delete(RecipientDTO recipientDTO) {
        repository.delete(recipientMapper.dtoToEntity(recipientDTO));
    }

    public Recipient getRecipientByLogin(String login) {
        return repository.findRecipientByLogin(login);
    }
}
