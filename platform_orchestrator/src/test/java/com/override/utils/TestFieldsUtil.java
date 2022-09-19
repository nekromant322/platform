package com.override.utils;

import com.override.model.*;
import com.override.model.enums.CoursePart;
import com.override.model.enums.Status;
import dto.*;
import enums.CodeExecutionStatus;
import enums.StudyStatus;
import org.thymeleaf.context.Context;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


public class TestFieldsUtil {

    public static Context generateContextByPersonalData() {
        PersonalData personalData = generatePersonalData();

        Context context = new Context();

        context.setVariable("actNumber", personalData.getActNumber());
        context.setVariable("contractNumber", personalData.getContractNumber());
        context.setVariable("contractDate", personalData.getContractDate().toString());
        context.setVariable("fullName", personalData.getFullName());
        context.setVariable("passportSeries", personalData.getPassportSeries());
        context.setVariable("passportNumber", personalData.getPassportNumber());
        context.setVariable("passportIssued", personalData.getPassportIssued());
        context.setVariable("issueDate", personalData.getIssueDate().toString());
        context.setVariable("birthDate", personalData.getBirthDate().toString());
        context.setVariable("registration", personalData.getRegistration());
        context.setVariable("email", personalData.getEmail());
        context.setVariable("phoneNumber", personalData.getPhoneNumber());
        context.setVariable("actualDate", LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

        return context;
    }

    public static PersonalData generatePersonalData() {
        return PersonalData.builder()
                .id(5l)
                .actNumber(950L)
                .contractNumber("9/6/2022")
                .contractDate(LocalDate.of(2022, 6, 10))
                .fullName("Erich Zieme DVM")
                .passportSeries(5369L)
                .passportNumber(642705L)
                .passportIssued("Apt. 470 17253 Bradtke Extensions, South Betsey, NV 60678-6968")
                .issueDate(LocalDate.of(2024, 03, 12))
                .birthDate(LocalDate.of(1991, 10, 07))
                .registration("Apt. 403 1396 Little Ranch, Volkmanland, NC 35258-6649")
                .email("arnette22@gmail.com")
                .phoneNumber(82531250128L)
                .build();
    }

    public static PersonalDataDTO generatePersonalDataDTO() {

        PersonalData personalData = generatePersonalData();
        return PersonalDataDTO.builder()
                .id(personalData.getId())
                .actNumber(personalData.getActNumber())
                .contractNumber(personalData.getContractNumber())
                .contractDate(personalData.getContractDate())
                .fullName(personalData.getFullName())
                .passportSeries(personalData.getPassportSeries())
                .passportNumber(personalData.getPassportNumber())
                .passportIssued(personalData.getPassportIssued())
                .issueDate(personalData.getIssueDate())
                .birthDate(personalData.getBirthDate())
                .registration(personalData.getRegistration())
                .email(personalData.getEmail())
                .phoneNumber(personalData.getPhoneNumber())
                .build();
    }

    public static PreProjectLessonMentorReactionDTO generateTestPreProjectLessonMentorReactionDTO() {
        return PreProjectLessonMentorReactionDTO.builder()
                .link("https://github.com/nekromant/platform")
                .login(generateTestUser().getLogin())
                .comments(new ArrayList<>())
                .viewed(false)
                .approve(false)
                .build();
    }

    public static PreProjectLessonDTO generateTestPreProjectLessonDTO() {
        return PreProjectLessonDTO.builder()
                .link("https://github.com/nekromant/platform")
                .taskIdentifier("1/1/1")
                .build();
    }

    public static PreProjectLesson generateTestPreProjectLesson() {
        return PreProjectLesson.builder()
                .link("https://github.com/nekromant/platform")
                .approve(false)
                .comments(new ArrayList<>())
                .taskIdentifier("1/1/1")
                .user(generateTestUser())
                .build();
    }

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
        PlatformUser firstUserWithoutReport = new PlatformUser(1L, "1", "1", StudyStatus.ACTIVE, CoursePart.CORE,
                Collections.singletonList(new Authority(1L, "ROLE_USER")), new PersonalData(), new UserSettings());
        PlatformUser secondUserWithoutReport = new PlatformUser(2L, "2", "2", StudyStatus.ACTIVE, CoursePart.CORE,
                Collections.singletonList(new Authority(1L, "ROLE_USER")), new PersonalData(), new UserSettings());
        PlatformUser thirdUserWithoutReport = new PlatformUser(3L, "3", "3", StudyStatus.ACTIVE, CoursePart.CORE,
                Collections.singletonList(new Authority(1L, "ROLE_USER")), new PersonalData(), new UserSettings());

        return List.of(firstUserWithoutReport, secondUserWithoutReport, thirdUserWithoutReport);
    }

    public static List<PlatformUser> generateTestListOfTwoAdmins() {
        PlatformUser firstUserWithoutReport = new PlatformUser(4L, "Admin1", "a", StudyStatus.ACTIVE, CoursePart.CORE,
                Collections.singletonList(new Authority(null, "admin")), new PersonalData(), new UserSettings());
        PlatformUser secondUserWithoutReport = new PlatformUser(5L, "Admin2", "s", StudyStatus.ACTIVE, CoursePart.CORE,
                Collections.singletonList(new Authority(null, "admin")), new PersonalData(), new UserSettings());

        return List.of(firstUserWithoutReport, secondUserWithoutReport);
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

    public static DefaultQuestion generateTestDefaultQuestion() {
        return DefaultQuestion.builder()
                .id(1L)
                .question("Самый стандартный вопрос на ревью")
                .chapter("core")
                .section(3)
                .build();
    }

    public static VkCall generateTestVkCall() {
        return VkCall.builder()
                .id(1L)
                .joinLink("joinLink")
                .callId("")
                .reviewId(1L)
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
                .vkCall(generateTestVkCall())
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
                .callLink(testReview.getVkCall().getJoinLink())
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
                .date(LocalDate.of(2022, 2, 22))
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
                .date(LocalDate.of(2022, 1, 19))
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
                .date(LocalDate.of(2022, 3, 17))
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

    public static InterviewData generateTestInterviewData() {
        return InterviewData.builder()
                .id(1L)
                .userLogin("testUser")
                .company("Test Company")
                .description("Test description")
                .contacts("880055535535")
                .date(LocalDate.of(2022, 7, 3))
                .time(LocalTime.of(22, 22, 0))
                .comment("Test comment")
                .stack("Test stack")
                .salary(250)
                .meetingLink("https://meet.google.com/oqc-sijo-esb")
                .distanceWork("Да")
                .build();
    }

    public static InterviewDataDTO generateTestInterviewDataDTO() {
        InterviewData interviewData = generateTestInterviewData();
        return InterviewDataDTO.builder()
                .id(interviewData.getId())
                .userLogin(interviewData.getUserLogin())
                .company(interviewData.getCompany())
                .description(interviewData.getDescription())
                .contacts(interviewData.getContacts())
                .date(interviewData.getDate())
                .time(interviewData.getTime())
                .comment(interviewData.getComment())
                .stack(interviewData.getStack())
                .salary(interviewData.getSalary())
                .meetingLink(interviewData.getMeetingLink())
                .distanceWork(interviewData.getDistanceWork())
                .build();
    }
}
