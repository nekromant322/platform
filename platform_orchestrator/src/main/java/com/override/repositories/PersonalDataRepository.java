package com.override.repositories;

import com.override.models.PersonalData;
import org.springframework.data.repository.CrudRepository;

public interface PersonalDataRepository extends CrudRepository<PersonalData, Long> {
}
