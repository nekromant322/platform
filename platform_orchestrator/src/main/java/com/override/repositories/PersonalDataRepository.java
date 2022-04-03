package com.override.repositories;

import com.override.models.JoinRequest;
import org.springframework.data.repository.CrudRepository;

public interface PersonalDataRepository extends CrudRepository<JoinRequest, Long> {
}
