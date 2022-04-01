package com.override.utils;

import com.override.models.*;
import dtos.*;
import enums.CodeExecutionStatus;

import java.time.LocalDate;
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
                .chapter("core 3")
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

    public static Review generateTestReview() {
        return Review.builder()
                .id(1L)
                .student(generateTestUser())
                .mentor(generateTestUser())
                .title("Тема 1")
                .bookedDateTime(LocalDateTime.of(2022, 4, 1, 16, 30))
                .confirmed(false)
                .build();
    }

    public static ReviewDTO generateTestReviewDTO() {
        PlatformUser testStudent = generateTestUser();
        PlatformUser testMentor = generateTestUser();
        Review testReview = generateTestReview();
        int[] testSlots = {23, 25, 33};
        return ReviewDTO.builder()
                .id(testReview.getId())
                .studentLogin(testStudent.getLogin())
                .mentorLogin(testMentor.getLogin())
                .title(testReview.getTitle())
                .bookedDateTime(testReview.getBookedDateTime())
                .confirmed(testReview.getConfirmed())
                .date(LocalDate.of(2022, 4, 1))
                .slots(testSlots)
                .slot(2)
                .build();
    }
}
