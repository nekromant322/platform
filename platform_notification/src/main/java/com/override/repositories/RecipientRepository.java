package com.override.repositories;

import com.override.models.Recipient;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RecipientRepository extends CrudRepository<Recipient, Long> {

    Optional<Recipient> findRecipientByLogin(String login);
}
