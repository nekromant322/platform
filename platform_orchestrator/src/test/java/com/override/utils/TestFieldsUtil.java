package com.override.utils;

import com.override.models.*;
import dtos.*;
import enums.CodeExecutionStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.math.BigInteger;
import java.util.*;


public class TestFieldsUtil {

    public static String generateTestCode() {
        return "public class MyClass {public static void main(String[] args){ System.out.println(\"This is my Code\");}}";
    }

    public static PlatformUser generateTestUser() {
        return new PlatformUser(null, "Andrey", "a",
                Collections.singletonList(new Authority(null, "admin")), new PersonalData());
    }

    public static List<PlatformUser> generateTestListOfThreeUsersWithoutReportsOnCurrentDay() {
        PlatformUser firstUserWithoutReport = new PlatformUser(1L, "1", "1",
                Collections.singletonList(new Authority(1L, "ROLE_USER")), new PersonalData());
        PlatformUser secondUserWithoutReport = new PlatformUser(2L, "2", "2",
                Collections.singletonList(new Authority(1L, "ROLE_USER")), new PersonalData());
        PlatformUser thirdUserWithoutReport = new PlatformUser(3L, "3", "3",
                Collections.singletonList(new Authority(1L, "ROLE_USER")), new PersonalData());

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

    public static List<Object[]> generateListObjects(){
        Object[] obj = new Object[]{"testUser", new BigInteger(Integer.toBinaryString(1))};
        final ArrayList<Object[]> objects = new ArrayList<>();
        objects.add(obj);
        return  objects;
    }

    public static List<Integer[]> generateListInteger(){
        Integer[] obj = new Integer[]{1, 2, 3, 4};
        final ArrayList<Integer[]> objects = new ArrayList<>();
        objects.add(obj);
        return  objects;
    }

    public static List<Long[]> generateListLong(){
        Long[] obj = new Long[]{1L, 2L, 3L, 4L};
        final ArrayList<Long[]> objects = new ArrayList<>();
        objects.add(obj);
        return  objects;
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

    public static Review generateTestReview() {
        Set<LocalTime> testTimeSlots = new HashSet<>();
        testTimeSlots.add(LocalTime.of(16, 30));
        testTimeSlots.add(LocalTime.of(17, 30));
        testTimeSlots.add(LocalTime.of(18, 30));
        return Review.builder()
                .id(1L)
                .topic("Тема 1")
                .student(generateTestUser())
                .mentor(generateTestUser())
                .bookedDate(LocalDate.of(2022, 4, 4))
                .bookedTime(LocalTime.of(16, 30))
                .timeSlots(testTimeSlots)
                .build();
    }

    public static ReviewDTO generateTestReviewDTO() {
        Review testReview = generateTestReview();
        return ReviewDTO.builder()
                .id(testReview.getId())
                .topic(testReview.getTopic())
                .studentLogin(testReview.getStudent().getLogin())
                .mentorLogin(testReview.getMentor().getLogin())
                .bookedDate(testReview.getBookedDate())
                .bookedTime(testReview.getBookedTime())
                .timeSlots(testReview.getTimeSlots())
                .build();
    }
}
