package com.override.repository;

import com.override.model.PlatformUser;
import com.override.model.PreProjectLesson;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PreProjectLessonRepository extends CrudRepository<PreProjectLesson, Long> {
    List<PreProjectLesson> findAllByTaskIdentifierAndUser(String taskIdentifier, PlatformUser user);
}
