package com.override.utils;

import com.override.models.*;
import dtos.CodeTryDTO;
import dtos.QuestionDTO;
import dtos.TaskIdentifierDTO;
import dtos.TestResultDTO;
import enums.CodeExecutionStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class TestFieldsUtil {

    public static String generateTestCode() {
        return "public class MyClass {public static void main(String[] args){ System.out.println(\"This is my Code\");}}";
    }

    public static PlatformUser generateTestUser() {
        return new PlatformUser(null, "Andrey", "a", "a",
                Collections.singletonList(new Authority(null, "admin")));
    }

    public static List<PlatformUser> generateTestListOfThreeUsersWithoutReportsOnCurrentDay() {
        PlatformUser firstUserWithoutReport = new PlatformUser(1L, "1", "1", "1",
                Collections.singletonList(new Authority(1L, "ROLE_USER")));
        PlatformUser secondUserWithoutReport = new PlatformUser(2L, "2", "2", "2",
                Collections.singletonList(new Authority(1L, "ROLE_USER")));
        PlatformUser thirdUserWithoutReport = new PlatformUser(3L, "3", "3", "3",
                Collections.singletonList(new Authority(1L, "ROLE_USER")));

        return List.of(firstUserWithoutReport, secondUserWithoutReport, thirdUserWithoutReport);
    }

    public static TaskIdentifierDTO generateTestTaskIdentifierDTO() {
        return TaskIdentifierDTO.builder()
                .chapter(1)
                .step(1)
                .lesson(1)
                .build();
    }

    public static CodeTry generateTestCodeTry() {
        return new CodeTry(null, generateTestCode(), 1, 1, 1,
                CodeExecutionStatus.OK, LocalDateTime.of(2022, 1, 1, 12, 0),
                generateTestUser());
    }

    public static TestResultDTO generateTestTestResultDTO() {
        return TestResultDTO.builder()
                .codeExecutionStatus(CodeExecutionStatus.OK)
                .output("blabla")
                .build();
    }

    public static CodeTryDTO generateTestCodeTryDTO() {
        return new CodeTryDTO(generateTestTaskIdentifierDTO(), generateTestCode());
    }

    public static Question generateTestQuestion() {
        return Question.builder()
                .id(1L)
                .question("А ты точно разработчик?")
                .answered(true)
                .chapter("core 3")
                .user(generateTestUser())
                .build();
    }

    public static QuestionDTO generateTestQuestionDTO() {
        PlatformUser user = generateTestUser();
        Question testQuestion = generateTestQuestion();
        return QuestionDTO.builder()
                .id(testQuestion.getId())
                .chapter(testQuestion.getChapter())
                .login(user.getLogin())
                .answered(testQuestion.isAnswered())
                .question(testQuestion.getQuestion())
                .build();
    }
}
