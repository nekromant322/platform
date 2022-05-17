package com.override.util;

import com.github.javafaker.Faker;
import com.override.models.*;
import com.override.models.enums.Role;
import com.override.models.enums.Status;
import com.override.service.*;
import dtos.*;
import enums.CodeExecutionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(prefix = "testData", name = "enabled", havingValue = "true")
public class InitializationService {

    @Value("${jwt.primeAdminLogin}")
    private String adminLogin;

    @Value("${jwt.primeAdminPassword}")
    private String adminPassword;

    @Value("${jwt.primeAdminChatId}")
    private String adminTelegramChatId;

    @Value("${testData.usersCount}")
    private int usersCount;

    @Value("${testData.requestsCount}")
    private int requestsCount;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private PlatformUserService userService;

    @Autowired
    private PersonalDataService personalDataService;

    @Autowired
    private CodeTryService codeTryService;

    @Autowired
    private JoinRequestService joinRequestService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private LessonStructureService lessonStructureService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserSettingsService userSettingsService;

    @Autowired
    private InterviewReportService interviewReportService;

    @Autowired
    private Faker faker;

    @PostConstruct
    private void init() {
        authorityInit();
        adminInit();
        userInit();
        codeTryInit();
        joinRequestsInit();
        questionsInit();
        reportsInit();
        reviewInit();
        interviewReportsInit();
    }

    private void authorityInit() {
        for (Role role : Role.values()) {
            authorityService.save(role.getName());
        }
    }

    private void userInit() {
        String usernameAndPassword;

        for (int i = 0; i < usersCount; i++) {
            usernameAndPassword = faker.name().firstName();
            saveUser(usernameAndPassword,
                    usernameAndPassword,
                    Role.USER);
        }
    }

    private void adminInit() {
        saveUser(adminLogin, adminPassword, Role.USER, Role.ADMIN);
    }

    private void saveUser(String login, String password, Role... userRoles) {
        List<Authority> roles = getAuthorityListFromRoles(userRoles);
        PlatformUser account = new PlatformUser(null, login, password, roles, new PersonalData(), new UserSettings());
        userService.save(account);
        personalDataInit(account);
        userSettingsInit(account);
    }

    private List<Authority> getAuthorityListFromRoles(Role... roles) {
        List<Authority> authorityList = new ArrayList<>();
        for (Role role : roles) {
            authorityList.add(authorityService.getAuthorityByRole(role));
        }
        return authorityList;
    }

    private void codeTryInit() {
        List<PlatformUser> students = userService.getAllStudents();
        students.forEach(this::addCodeTryForEveryChapter);
    }

    private void addCodeTryForEveryChapter(PlatformUser student) {
        List<String> chapters = lessonStructureService.getChapterNamesList();
        chapters.forEach(chapter -> generateCodeTryAttempts(student, chapters.indexOf(chapter) + 1,
                chapters.indexOf(chapter) + 1 + new Random().nextInt(chapters.size() + 1),
                chapters.indexOf(chapter) + 1 + new Random().nextInt(chapters.size() + 2)));
    }

    private void generateCodeTryAttempts(PlatformUser student, int chapter, int lesson, int step) {
        CodeExecutionStatus status = CodeExecutionStatus
                .values()[new Random().nextInt(CodeExecutionStatus.values().length)];
        while (status != CodeExecutionStatus.OK) {
            saveCodeTry(student, chapter, lesson, step, status);
            status = CodeExecutionStatus
                    .values()[new Random().nextInt(CodeExecutionStatus.values().length)];
        }
        saveCodeTry(student, chapter, lesson, step, CodeExecutionStatus.OK);
    }

    private void saveCodeTry(PlatformUser student, int chapter, int lesson, int step, CodeExecutionStatus status) {
        String helloWorld = "class HelloWorld {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Hello World!\");\n" +
                "    }\n" +
                "}";
        codeTryService.saveCodeTry(
                CodeTryDTO
                        .builder()
                        .taskIdentifier(TaskIdentifierDTO
                                .builder()
                                .chapter(chapter)
                                .lesson(lesson)
                                .step(step)
                                .build())
                        .studentsCode(helloWorld)
                        .build(),
                TestResultDTO
                        .builder()
                        .codeExecutionStatus(status)
                        .output(faker.funnyName().name()).build(),
                student.getLogin());
    }

    private void questionsInit() {
        List<PlatformUser> students = userService.getAllStudents();
        students.forEach(this::saveQuestions);
    }

    private void saveQuestions(PlatformUser student) {
        int randomCount = faker.number().numberBetween(0, 10);
        for (int i = 0; i < randomCount; i++) {
            List<String> chapters = lessonStructureService.getChapterNamesList();
            chapters.forEach(chapter -> questionService.save(QuestionDTO
                    .builder()
                    .login(student.getLogin())
                    .chapter(chapter)
                    .answered(new Random().nextBoolean())
                    .question(faker.funnyName().name())
                    .build()));
        }
    }

    private void reportsInit() {
        List<PlatformUser> students = userService.getAllStudents();
        students.forEach(this::saveReport);
    }

    private void saveReport(PlatformUser student) {
        List<LocalDate> datesOfReports = new ArrayList<>();
        LocalDate currentDate;

        for (int i = 0; i < 5; i++) {
            StudentReport report = new StudentReport();
            report.setStudent(student);
            currentDate = getRandomDate();
            while (datesOfReports.contains(currentDate)) {
                currentDate = getRandomDate();
            }

            report.setDate(currentDate);
            datesOfReports.add(report.getDate());
            report.setText(faker.elderScrolls().firstName());
            report.setHours((double) faker.number().numberBetween(0, 10));
            reportService.saveReport(report, student.getLogin());
        }
    }

    private LocalDate getRandomDate() {
        long minDay = LocalDate.now().minusDays(12).toEpochDay();
        long maxDay = LocalDate.now().minusDays(2).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }

    private void joinRequestsInit() {
        for (int i = 0; i < requestsCount; i++) {
            joinRequestService.saveRequest(new RegisterUserRequestDTO(faker.name().username(),
                    String.valueOf(faker.number().numberBetween(1000, 10000))));
        }
    }

    private void reviewInit() {
        List<PlatformUser> students = userService.getAllStudents();
        students.forEach(this::saveOrUpdateReview);
    }

    private void saveOrUpdateReview(PlatformUser student) {
        List<LocalTime> timeSlots = new ArrayList<>();
        LocalTime time = LocalTime.of(00, 00);

        for (int i = 0; i < 47; i++) {
            timeSlots.add(time);
            time = time.plusMinutes(30);
        }

        Set<LocalTime> selectedTimeSlots = new HashSet<>();
        selectedTimeSlots.add(timeSlots.get(faker.number().numberBetween(0, 47)));
        selectedTimeSlots.add(timeSlots.get(faker.number().numberBetween(0, 47)));

        reviewService.saveOrUpdateReview(ReviewDTO.builder()
                .id(null)
                .topic(faker.book().title())
                .studentLogin(null)
                .mentorLogin(null)
                .bookedDate(LocalDate.now().plusDays(1))
                .bookedTime(null)
                .timeSlots(selectedTimeSlots)
                .build(), student.getLogin());

        selectedTimeSlots.add(timeSlots.get(faker.number().numberBetween(0, 47)));

        reviewService.saveOrUpdateReview(ReviewDTO.builder()
                .id(null)
                .topic(faker.book().title())
                .studentLogin(null)
                .mentorLogin(adminLogin)
                .bookedDate(LocalDate.now())
                .bookedTime(selectedTimeSlots.iterator().next())
                .timeSlots(selectedTimeSlots)
                .build(), student.getLogin());
    }

    private void personalDataInit(PlatformUser user) {

        int day = faker.number().numberBetween(1, 30);
        int month = faker.number().numberBetween(1, 12);
        int year = 2022;

        PersonalData personalData = user.getPersonalData();
        personalData.setActNumber(faker.number().numberBetween(1L, 1000L));
        personalData.setContractNumber(day + "/" + month + "/" + year);
        personalData.setDate(new Date(year - 1900, month - 1, day + 1));
        personalData.setFullName(faker.name().fullName());
        personalData.setPassportSeries(Long.valueOf(faker.bothify("####")));
        personalData.setPassportNumber(Long.valueOf(faker.bothify("######")));
        personalData.setPassportIssued(faker.address().fullAddress());
        personalData.setIssueDate(new Date(faker.number().numberBetween(123, 127),
                faker.number().numberBetween(0, 11),
                faker.number().numberBetween(1, 30)));
        personalData.setBirthDate(new Date(faker.number().numberBetween(90, 104),
                faker.number().numberBetween(0, 11),
                faker.number().numberBetween(1, 30)));
        personalData.setRegistration(faker.address().fullAddress());
        personalData.setEmail(faker.name().firstName().toLowerCase(Locale.ROOT) +
                faker.bothify("##@") + "gmail.com");
        personalData.setPhoneNumber(Long.valueOf("8" + faker.bothify("##########")));

        personalDataService.save(personalData, user.getLogin());

    }

    public void userSettingsInit(PlatformUser user) {
        UserSettings userSettings = new UserSettings();
        userSettings.setTelegramNotification(true);
        userSettings.setVkNotification(true);
        userSettingsService.save(userSettings, user.getLogin());
    }

    public void interviewReportsInit() {
        List<PlatformUser> students = userService.getAllStudents();
        students.forEach(this::saveOrUpdateInterviewReport);
    }

    public void saveOrUpdateInterviewReport(PlatformUser user) {
        List<String> statusList = new ArrayList<>(List.of(Status.PASSED.name(), Status.OFFER.name(), Status.ACCEPTED.name(),
                Status.PASSED.name(), Status.OFFER.name(), Status.ACCEPTED.name()));
        List<String> levelList = new ArrayList<>(List.of("Junior", "Middle", "Senior", "Middle", "Senior", "Middle"));
        int salary = (faker.number().numberBetween(150, 350)) * 1000;
        interviewReportService.save(InterviewReportDTO.builder()
                .date(LocalDate.now())
                .userLogin(user.getLogin())
                .company(faker.company().name())
                .project(faker.company().industry())
                .questions(faker.food().dish())
                .impression(faker.number().numberBetween(1, 5))
                .minSalary(salary)
                .maxSalary(salary + 26000)
                .currency('₽')
                .status(statusList.get(new Random().nextInt(statusList.size())))
                .level(levelList.get(new Random().nextInt(levelList.size())))
                .build());
    }
}
