package com.override.utils;

import com.override.model.*;
import com.override.model.enums.CoursePart;
import com.override.model.enums.Status;
import dto.*;
import enums.CodeExecutionStatus;
import enums.StudyStatus;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;


public class TestFieldsUtil {

    public static Payment generateTestPayment() {
        return Payment.builder()
                .sum(200000L)
                .message("Первый платеж")
                .accountNumber(123321L)
                .studentName("Andrey")
                .date(LocalDate.of(2022, 7, 24))
                .build();
    }

    public static PaymentDTO generateTestPaymentDTO() {
        return PaymentDTO.builder()
                .sum(200000L)
                .comment("Первый платеж")
                .accountNumber(123321L)
                .date(LocalDate.of(2022, 7, 24))
                .build();
    }

    public static String generateTestCode() {
        return "public class MyClass {public static void main(String[] args){ System.out.println(\"This is my Code\");}}";
    }

    public static PlatformUser generateTestUser() {
        return new PlatformUser(null, "Andrey", "a", StudyStatus.ACTIVE, CoursePart.CORE,
                Collections.singletonList(new Authority(null, "admin")), new PersonalData(), new UserSettings());
    }

    public static List<PlatformUser> generateTestListOfThreeUsersWithoutReportsOnCurrentDay() {
        PlatformUser firstUserWithoutReport = new PlatformUser(1L, "1", "1", StudyStatus.ACTIVE,CoursePart.CORE,
                Collections.singletonList(new Authority(1L, "ROLE_USER")), new PersonalData(), new UserSettings());
        PlatformUser secondUserWithoutReport = new PlatformUser(2L, "2", "2", StudyStatus.ACTIVE,CoursePart.CORE,
                Collections.singletonList(new Authority(1L, "ROLE_USER")), new PersonalData(), new UserSettings());
        PlatformUser thirdUserWithoutReport = new PlatformUser(3L, "3", "3", StudyStatus.ACTIVE,CoursePart.CORE,
                Collections.singletonList(new Authority(1L, "ROLE_USER")), new PersonalData(), new UserSettings());

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

    public static List<Object[]> generateListObjects() {
        Object[] obj = new Object[]{"testUser", new BigInteger(Integer.toBinaryString(1))};
        final ArrayList<Object[]> objects = new ArrayList<>();
        objects.add(obj);
        return objects;
    }

    public static List<Integer[]> generateListInteger() {
        Integer[] obj = new Integer[]{1, 2, 3, 4};
        final ArrayList<Integer[]> objects = new ArrayList<>();
        objects.add(obj);
        return objects;
    }

    public static List<Long[]> generateListLong() {
        Long[] obj = new Long[]{1L, 2L, 3L, 4L};
        final ArrayList<Long[]> objects = new ArrayList<>();
        objects.add(obj);
        return objects;
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
                .bookedDate(LocalDate.now())
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

    public static Document generateTestDocument() {
        Document document = new Document();
        document.setId(1L);
        document.setName("filename.txt");
        document.setType("text/plain");
        document.setContent("some content".getBytes());
        return document;
    }

    public static Bug generateTestBug() {
        Bug bug = new Bug();
        bug.setId(1L);
        bug.setName("screenshot.txt");
        bug.setType("image/png");
        bug.setText("some text");
        bug.setUser(generateTestUser());
        bug.setContent("some content".getBytes());
        return bug;
    }

    public static BugReportsDTO generateTestBugReportDTO() {
        Bug bug = generateTestBug();
        return BugReportsDTO.builder()
                .id(bug.getId())
                .name(bug.getName())
                .type(bug.getType())
                .text(bug.getText())
                .user(bug.getUser().getLogin())
                .build();
    }

    public static InterviewReport generateTestInterviewReport() {
        return InterviewReport.builder()
                .id(1L)
                .date(LocalDate.of(2022, 02, 22))
                .userLogin("testUser")
                .company("Test Company")
                .project("Test Project")
                .questions("standard")
                .impression(4)
                .minSalary(220000)
                .maxSalary(280000)
                .currency('₽')
                .status(Status.PASSED)
                .level("Middle")
                .build();
    }

    public static InterviewReportDTO generateTestInterviewReportDTO() {
        InterviewReport interviewReport = generateTestInterviewReport();
        return InterviewReportDTO.builder()
                .id(interviewReport.getId())
                .date(interviewReport.getDate())
                .userLogin(interviewReport.getUserLogin())
                .company(interviewReport.getCompany())
                .project(interviewReport.getProject())
                .questions(interviewReport.getQuestions())
                .impression(interviewReport.getImpression())
                .minSalary(interviewReport.getMinSalary())
                .maxSalary(interviewReport.getMaxSalary())
                .currency(interviewReport.getCurrency())
                .status(interviewReport.getStatus().getName())
                .level(interviewReport.getLevel())
                .build();
    }

    public static SalaryDTO generateTestSalaryDTO(List<InterviewReport> interviewReportList, SalaryStatDTO salaryStatDTO) {
        return SalaryDTO.builder()
                .salaryStats(List.of(salaryStatDTO))
                .labels(interviewReportList.stream().map(n -> n.getDate().toString()).collect(Collectors.toList()))
                .build();
    }

    public static List<InterviewReport> generateInterviewReportsList() {
        List<InterviewReport> interviewReportList = new ArrayList<>();

        interviewReportList.add(generateTestInterviewReport());

        interviewReportList.add(InterviewReport.builder()
                .id(2L)
                .date(LocalDate.of(2022, 01, 19))
                .userLogin("Kraken")
                .company("CompanyOfHeroes")
                .project("ProjectUmbrella")
                .questions("standard")
                .impression(3)
                .minSalary(198000)
                .maxSalary(260000)
                .currency('$')
                .status(Status.OFFER)
                .level("Junior")
                .build());

        interviewReportList.add(InterviewReport.builder()
                .id(3L)
                .date(LocalDate.of(2022, 03, 17))
                .userLogin("Joshua")
                .company("GoodCompany")
                .project("GoodProject")
                .questions("standard")
                .impression(5)
                .minSalary(240000)
                .maxSalary(2950000)
                .currency('$')
                .status(Status.ACCEPTED)
                .level("Senior")
                .build());

        return interviewReportList;
    }

    public static SalaryStatDTO generateSalaryStatDto(List<InterviewReport> interviewReportList) {
        return SalaryStatDTO.builder()
                .data(interviewReportList.stream().map(InterviewReport::getMaxSalary).collect(Collectors.toList()))
                .borderWidth(1)
                .label(interviewReportList.get(0).getUserLogin())
                .build();
    }
}
