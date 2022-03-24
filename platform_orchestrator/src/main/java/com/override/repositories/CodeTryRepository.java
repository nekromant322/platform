package com.override.repositories;

import com.override.models.CodeTry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CodeTryRepository extends JpaRepository<CodeTry, Long> {
    List<CodeTry> findAllByUserLogin(String login);
    List<CodeTry> findByUserLoginAndChapterAndStepAndLesson(String login, Integer chapter, Integer step, Integer lesson);

    @Query(value = "SELECT chapter, step, count(*) as count from code_try group by chapter, step", nativeQuery = true)
    List<Integer[]> countCodeTryByChapterAndStep();

    @Query(value = "SELECT login, count(*) from code_try join platform_user as pu" +
            " on pu.id = code_try.user_id group by login", nativeQuery = true)
    List<Object[]> countStatsOfUsers();

    @Query(value = "SELECT status, count(*) from code_try group by status", nativeQuery = true)
    List<Object[]> countStatsOfStatus();

    @Query(value = "SELECT chapter, step, lesson, count(*) as count " +
            "from code_try where status!='OK' group by chapter, step, lesson " +
            "order by count desc limit 20", nativeQuery = true)
    List<Integer[]> countStatsOfHardTasks();
}
