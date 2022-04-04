package com.override.repositories;

import com.override.models.PlatformUser;
import org.springframework.data.repository.CrudRepository;

public interface PersonalDataRepository extends CrudRepository<PlatformUser, Long> {
}
