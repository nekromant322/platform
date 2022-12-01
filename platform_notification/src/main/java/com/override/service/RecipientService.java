package com.override.service;

import com.override.mapper.RecipientMapper;
import com.override.model.Recipient;
import com.override.repository.RecipientRepository;
import com.override.service.communication.CommunicationStrategy;
import com.override.service.communication.CommunicationStrategyFactory;
import dto.RecipientDTO;
import enums.Communication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
        if (repository.findRecipientByLogin(recipientDTO.getLogin()).isPresent()) {
            Recipient recipient = repository.findRecipientByLogin(recipientDTO.getLogin()).get();
            if (!recipientDTO.getEmail().equals("")) {
                recipient.setEmail(recipientDTO.getEmail());
            }
            if (recipientDTO.getPhoneNumber() != null) {
                recipient.setPhoneNumber(recipientDTO.getPhoneNumber());
            }
            repository.save(recipient);
        } else {
            repository.save(recipientMapper.dtoToEntity(recipientDTO));
        }
    }

    public void updateCommunication(String login, String value, Communication type) {
        Recipient recipient = repository.findRecipientByLogin(login).orElseThrow(() ->
                new EntityNotFoundException("Recipient with login " + login + " not found"));
        Map<Communication, CommunicationStrategy> strategyMap = strategyFactory.getSenderMap();

        recipient = strategyMap.get(type).updateRecipient(recipient, value);
        repository.save(recipient);
    }

    public void delete(RecipientDTO recipientDTO) {
        repository.delete(recipientMapper.dtoToEntity(recipientDTO));
    }

    public Recipient findRecipientByLogin(String login) {
        return repository.findRecipientByLogin(login).get();
    }

    public Boolean isPresent(String login) {
        return repository.findRecipientByLogin(login).isPresent();
    }
}
