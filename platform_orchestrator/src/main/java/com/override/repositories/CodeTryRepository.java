package com.override.repositories;

import com.override.models.CodeTry;
import com.override.models.PlatformUser;
import enums.CodeExecutionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface CodeTryRepository extends JpaRepository<CodeTry, Long> {
    List<CodeTry> findAllByUserLogin(String login);
    List<CodeTry> findByUserLoginAndChapterAndStepAndLesson(String login, Integer chapter, Integer step, Integer lesson);
    long countCodeTryByChapterAndStepAndLessonAndCodeExecutionStatus(int chapter, int step, int lesson, CodeExecutionStatus status);
    long countCodeTryByChapterAndCodeExecutionStatusAndUser(int chapter, CodeExecutionStatus codeExecutionStatus, PlatformUser platformUser);
    long countCodeTryByChapterAndCodeExecutionStatus(int chapter, CodeExecutionStatus codeExecutionStatus);
    long countCodeTryByCodeExecutionStatus(CodeExecutionStatus codeExecutionStatus);
}
