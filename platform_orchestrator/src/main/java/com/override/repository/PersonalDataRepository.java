package com.override.repository;

import com.override.model.PersonalData;
import org.springframework.data.repository.CrudRepository;

public interface PersonalDataRepository extends CrudRepository<PersonalData, Long> {
}
