package com.override.repository;

import com.override.model.PlatformUser;
import com.override.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByUserAndChapter(PlatformUser platformUser, String chapter);
}
