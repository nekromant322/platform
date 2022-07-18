package com.override.repository;

import com.override.model.Recipient;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RecipientRepository extends CrudRepository<Recipient, Long> {

    Optional<Recipient> findRecipientByLogin(String login);
}
