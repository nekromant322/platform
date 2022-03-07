package com.override.repositories;

import com.override.models.CodeTry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodeTryRepository extends JpaRepository<CodeTry, Long> {
    List<CodeTry> findAllByUserLogin(String login);
    List<CodeTry> findByUserLoginAndChapter(String login, Integer chapter);
    List<CodeTry> findByUserLoginAndChapterAndStep(String login, Integer chapter, Integer step);
    List<CodeTry> findByUserLoginAndChapterAndStepAndLesson(String login, Integer chapter, Integer step, Integer lesson);
}
