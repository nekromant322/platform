package com.override.repositories;

import com.override.models.PlatformUser;
import com.override.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByUserAndChapter(PlatformUser platformUser, int chapter);
}
