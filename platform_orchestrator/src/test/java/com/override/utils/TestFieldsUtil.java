package com.override.utils;

import com.override.models.Authority;
import com.override.models.CodeTry;
import com.override.models.PlatformUser;
import com.override.models.Question;
import dtos.CodeTryDTO;
import dtos.QuestionDTO;
import dtos.TaskIdentifierDTO;
import dtos.TestResultDTO;
import enums.CodeExecutionStatus;

import java.time.LocalDateTime;
import java.util.Collections;

public class TestFieldsUtil {

    public static String generateTestCode() {
        return "public class MyClass {public static void main(String[] args){ System.out.println(\"This is my Code\");}}";
    }

    public static PlatformUser generateTestUser() {
        return new PlatformUser(null, "Andrey", "a", "a",
                Collections.singletonList(new Authority(null, "admin")));
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
                .chapter(2)
                .user(generateTestUser())
                .build();
    }

    public static QuestionDTO generateTestQuestionDTO(){
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
