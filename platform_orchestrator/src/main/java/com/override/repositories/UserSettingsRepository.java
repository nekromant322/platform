package com.override.repositories;

import com.override.models.UserSettings;
import org.springframework.data.repository.CrudRepository;

public interface UserSettingsRepository extends CrudRepository<UserSettings, Long> {
}
