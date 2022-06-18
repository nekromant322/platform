package com.override.repository;

import com.override.model.UserSettings;
import org.springframework.data.repository.CrudRepository;

public interface UserSettingsRepository extends CrudRepository<UserSettings, Long> {
}
