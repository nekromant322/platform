package com.override.repository;

import com.override.model.CodeTry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CodeTryRepository extends JpaRepository<CodeTry, Long> {
    List<CodeTry> findAllByUserLogin(String login);

    List<CodeTry> findAllByUserIdOrderByDateDesc(Long userId);

    CodeTry findFirstByUserIdOrderByDate(Long userId);

    List<CodeTry> findByUserLoginAndChapterAndStepAndLesson(String login, Integer chapter, Integer step, Integer lesson);

    @Query(value = "SELECT chapter, step, count(*) as count, count(distinct lesson) as counLessons from code_try group by chapter, step", nativeQuery = true)
    List<Long[]> countCodeTryByChapterAndStep();

    @Query(value = "SELECT login, count(*) from code_try join platform_user as pu" +
            " on pu.id = code_try.user_id group by login", nativeQuery = true)
    List<Object[]> countStatsOfUsers();

    @Query(value = "SELECT code_execution_status as status, count(*) from code_try group by status", nativeQuery = true)
    List<Object[]> countStatsOfStatus();

    @Query(value = "SELECT chapter, step, lesson, count(*) as count " +
            "from code_try where code_execution_status!='OK' group by chapter, step, lesson " +
            "order by count desc limit :size", nativeQuery = true)
    List<Integer[]> countStatsOfHardTasks(@Param("size") int size);
}
