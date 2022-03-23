package com.override.repositories;

import com.override.models.CodeTry;
import com.override.models.PlatformUser;
import enums.CodeExecutionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CodeTryRepository extends JpaRepository<CodeTry, Long> {
    List<CodeTry> findAllByUserLogin(String login);
    List<CodeTry> findByUserLoginAndChapterAndStepAndLesson(String login, Integer chapter, Integer step, Integer lesson);

    //количество попыток по главам для всех студентов
    long countCodeTryByChapterAndStepAndCodeExecutionStatus(int chapter, int step, CodeExecutionStatus status);

    //количество попыток по студентам
    long countCodeTryCodeExecutionStatusAndUser(CodeExecutionStatus codeExecutionStatus, PlatformUser platformUser);

    //соотношение по CodeExecutionStatus  для всех попыток по всем студентам
    // (чтоб понимать насколько хуево работает code_executor, например,
    // если вдруг дофига RUNTIME_ERROR) можно бубликом
    long countCodeTryByCodeExecutionStatus(CodeExecutionStatus codeExecutionStatus);

    //топ 5/10/20 задач по которым больше всего попыток
    // (гистограммой с возможностью тыкать на 5/10/20)
    @Query(value = "SELECT chapter, step, lesson, status, count(*) as count " +
            "from code_try where status!='OK' group by chapter, step, lesson, status " +
            "order by count desc limit 20", nativeQuery = true)
    List<String> countStatsOfHardTasks();
}
