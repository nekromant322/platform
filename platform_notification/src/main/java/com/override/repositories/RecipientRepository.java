package com.override.repositories;

import com.override.models.Recipient;
import org.springframework.data.repository.CrudRepository;

public interface RecipientRepository extends CrudRepository<Recipient, Long> {

    Recipient getRecipientByLogin(String login);
}
