package com.override.util;

import com.github.javafaker.Faker;
import com.override.exception.UserAlreadyExistException;
import com.override.model.*;
import com.override.model.enums.CoursePart;
import com.override.model.enums.Role;
import com.override.model.enums.Status;
import com.override.service.*;
import dto.*;
import enums.CodeExecutionStatus;
import enums.StudyStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Component
@ConditionalOnProperty(prefix = "testData", name = "enabled", havingValue = "true")
@Profile("dev")
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

    @Value("${testData.paymentsCount}")
    private int paymentsCount;

    @Value("${testData.preProjectLessonCount}")
    private int preProjectLessonCount;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private PreProjectLessonService preProjectLessonService;

    @Autowired
    private PlatformUserService userService;

    @Autowired
    private PaymentService paymentService;

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
    private DefaultQuestionService defaultQuestionService;

    @Autowired
    private Faker faker;

    public void initTestData() {
        authorityInit();
        adminInit();
        userInit();
        codeTryInit();
        joinRequestsInit();
        questionsInit();
        reportsInit();
        reviewInit();
        interviewReportsInit();
        paymentInit();
        preProjectLessonsInit();
        defaultQuestionsInit();
    }

    private void preProjectLessonsInit() {
        for (int i = 0; i < preProjectLessonCount; i++) {
            preProjectLessonService.save(PreProjectLesson.builder()
                    .link(faker.bothify("https://github.com/??????##/???##??#/####.com"))
                    .comments(new ArrayList<>())
                    .user(getRandomUser())
                    .build());
        }
    }

    private PlatformUser getRandomUser() {
        List<PlatformUser> userList = userService.getAllStudents();
        Random rand = new Random();
        return userList.get(rand.nextInt(userList.size()));
    }

    private void paymentInit() {
        List<PlatformUser> userList = userService.getAllStudents();
        List<PlatformUser> graduateUserList = new ArrayList<>();

        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getAuthorities().listIterator().next().getAuthority().equals("ROLE_GRADUATE")) {
                graduateUserList.add(userList.get(i));
            }
        }

        Random rand = new Random();

        for (int i = 0; i < paymentsCount; i++) {
            paymentService.save(
                    Payment.builder()
                            .studentName(graduateUserList.get(rand.nextInt(graduateUserList.size())).getLogin())
                            .date(getRandomDateForTestPayment())
                            .accountNumber((long) faker.number().numberBetween(100000000, 900000000))
                            .sum((long) faker.number().numberBetween(10000, 100000))
                            .message(faker.letterify("???????????????????????????????????????????"))
                            .build()
            );
        }
    }

    private void authorityInit() {
        if (authorityService.checkIfTableIsEmpty()) {
            for (Role role : Role.values()) {
                authorityService.save(role.getName());
            }
        }
    }

    private void userInit() {
        String usernameAndPassword;

        for (int i = 0; i < usersCount; i++) {
            usernameAndPassword = faker.name().firstName();
            saveUser(usernameAndPassword,
                    usernameAndPassword,
                    StudyStatus.ACTIVE, CoursePart.CORE, Role.USER);
        }
        for (int i = 0; i < usersCount; i++) {
            usernameAndPassword = faker.name().firstName();
            saveUser(usernameAndPassword,
                    usernameAndPassword,
                    StudyStatus.ACTIVE, CoursePart.PREPROJECT, Role.GRADUATE);
        }
    }

    private void adminInit() {
        saveUser(adminLogin, adminPassword, StudyStatus.ACTIVE, CoursePart.PREPROJECT, Role.USER, Role.ADMIN);
    }

    private void saveUser(String login, String password, StudyStatus study, CoursePart coursePart, Role... userRoles) {
        List<Authority> roles = getAuthorityListFromRoles(userRoles);
        PlatformUser account = new PlatformUser(null, login, password, study, coursePart, roles, new PersonalData(), new UserSettings());
        try {
            userService.save(account);
            personalDataInit(account);
            userSettingsInit(account);
        } catch (UserAlreadyExistException e) {
            e.printStackTrace();
        }
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

    private LocalDate getRandomDateForTestPayment() {
        long minDay = LocalDate.now().minusDays(150).toEpochDay();
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


        long minDay = LocalDate.now().minusDays(1).toEpochDay();
        long maxDay = LocalDate.now().plusDays(2).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);

        reviewService.saveOrUpdate(ReviewDTO.builder()
                .id(null)
                .topic(faker.book().title())
                .studentLogin(null)
                .mentorLogin(null)
                .bookedDate(randomDate)
                .bookedTime(null)
                .timeSlots(selectedTimeSlots)
                .build(), student.getLogin());

        selectedTimeSlots.add(timeSlots.get(faker.number().numberBetween(0, 47)));

        reviewService.saveOrUpdate(ReviewDTO.builder()
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
        long minDay = LocalDate.now().minusDays(365).toEpochDay();
        long maxDay = LocalDate.now().toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        interviewReportService.save(InterviewReportDTO.builder()
                .date(LocalDate.ofEpochDay(randomDay))
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

    private void defaultQuestionsInit() {

        // 1 + 2
        defaultQuestionService.save(new DefaultQuestion("jvm jre jdk, что это такое, чем отличаются", "core", 12));
        defaultQuestionService.save(new DefaultQuestion("Запуск HelloWorld из консоли", "core", 12));
        defaultQuestionService.save(new DefaultQuestion("Что происходит (поэтапно) с кодом с момента нажатия на play в ide до вывода сообщения в консоль?", "core", 12));
        defaultQuestionService.save(new DefaultQuestion("Примитивные типы и их размеры", "core", 12));
        defaultQuestionService.save(new DefaultQuestion("Можно ли положить максимальное значения типа long во float переменную?", "core", 12));
        defaultQuestionService.save(new DefaultQuestion("Логические операции: и, или, xor", "core", 12));
        defaultQuestionService.save(new DefaultQuestion("Как в памяти хранятся целые числа? Перевод из двоичной в десятичную и наоборот.", "core", 12));
        defaultQuestionService.save(new DefaultQuestion("Как хранятся дробные числа? Откуда погрешность при вычислениях? Почему 0.1 + 0.7 != 0.8", "core", 12));
        defaultQuestionService.save(new DefaultQuestion("Чем отличаются явные и неявные приведения типов? Когда возможны неявные приведения?", "core", 12));
        defaultQuestionService.save(new DefaultQuestion("AutoBoxing и Unboxing, когда происходят автоматически?", "core", 12));
        defaultQuestionService.save(new DefaultQuestion("Что такое кодировка? Какие бывают кодировки? Чем отличаются?", "core", 12));
        defaultQuestionService.save(new DefaultQuestion("Что такое массив?", "core", 12));
        defaultQuestionService.save(new DefaultQuestion("Зачем нужны регулярные выражения?", "core", 12));
        defaultQuestionService.save(new DefaultQuestion("Что такое рекурсия? Плюсы и минусы использования рекурсии", "core", 12));
        defaultQuestionService.save(new DefaultQuestion("StringBuilder, когда стоит использовать, почему и когда стоит использовать .append() вместо конкатенации строк", "core", 12));
        defaultQuestionService.save(new DefaultQuestion("Что такое пул строк? Зачем он нужен?", "core", 12));


        //4
        defaultQuestionService.save(new DefaultQuestion("Зачем нужны исключения?", "core", 4));
        defaultQuestionService.save(new DefaultQuestion("Иерархия исключений", "core", 4));
        defaultQuestionService.save(new DefaultQuestion("Можно ли поймать и обработать Error? примеры Error", "core", 4));
        defaultQuestionService.save(new DefaultQuestion("Что такое стектрейс? Какую информацию хранит?", "core", 4));
        defaultQuestionService.save(new DefaultQuestion("какая информация хранится внутри каждого объекта-исключения?", "core", 4));
        defaultQuestionService.save(new DefaultQuestion("Зачем нужен механизм try-catch? как он работает?", "core", 4));
        defaultQuestionService.save(new DefaultQuestion("Что значит \"пробросить исключение\"? Зачем это может быть нужно?", "core", 4));
        defaultQuestionService.save(new DefaultQuestion("Зачем нужен finally? Когда он может не выполниться?", "core", 4));
        defaultQuestionService.save(new DefaultQuestion("Что будет происходить в программе, если исключение выбрасывается в блоке catch, а потом еще одно выбрасывается в блоке finally?", "core", 4));
        defaultQuestionService.save(new DefaultQuestion("Try with resources", "core", 4));
        defaultQuestionService.save(new DefaultQuestion("Объекты каких классов можно использовать в try with resources?", "core", 4));
        defaultQuestionService.save(new DefaultQuestion("Как влияет на использование кода, который бросает исключение, то что эти исключения проверяемые?", "core", 4));
        defaultQuestionService.save(new DefaultQuestion("От чего наследоваться при создании собственных  исключений: когда создавать проверяемые, а когда непроверяемые исключения?", "core", 4));
        defaultQuestionService.save(new DefaultQuestion("Подавленные исключения. Что это? Как достать? Пример кода", "core", 4));
        defaultQuestionService.save(new DefaultQuestion("Зачем нужны логеры? Почему не обойтись использованием System.out.println()?", "core", 4));
        defaultQuestionService.save(new DefaultQuestion("Как работает instanceOf? В чем отличие от obj.getClass() == MailPackage.class?", "core", 4));

        //5
        defaultQuestionService.save(new DefaultQuestion("как создать файл на диске?", "core", 5));
        defaultQuestionService.save(new DefaultQuestion("задача joke.java", "core", 5));
        defaultQuestionService.save(new DefaultQuestion("задача ecoPersons", "core", 5));
        defaultQuestionService.save(new DefaultQuestion("что такое поток (данных)?", "core", 5));
        defaultQuestionService.save(new DefaultQuestion("Как работает flush()?", "core", 5));
        defaultQuestionService.save(new DefaultQuestion("Какие есть наследники у класса InputStream? Зачем они нужны?", "core", 5));
        defaultQuestionService.save(new DefaultQuestion("Какой паттерн проектирования лежит в основе иерархии InputStream?", "core", 5));
        defaultQuestionService.save(new DefaultQuestion("Что такое сериализация?", "core", 5));
        defaultQuestionService.save(new DefaultQuestion("Как отключить сериализацию поля?", "core", 5));
        defaultQuestionService.save(new DefaultQuestion("Externalizible, зачем нужен?", "core", 5));
        defaultQuestionService.save(new DefaultQuestion("Задачка на сериализацию паспорта", "core", 5));
        defaultQuestionService.save(new DefaultQuestion("Глубокое vs поверхностное клонирование", "core", 5));
        defaultQuestionService.save(new DefaultQuestion("Как клонировать объекты в java (глубоко)?", "core", 5));

        //6
        defaultQuestionService.save(new DefaultQuestion("что такое дженерики?", "core", 6));
        defaultQuestionService.save(new DefaultQuestion("какую проблему они решают?", "core", 6));
        defaultQuestionService.save(new DefaultQuestion("optional", "core", 6));
        defaultQuestionService.save(new DefaultQuestion("зачем типизация <T,R> для of?", "core", 6));
        defaultQuestionService.save(new DefaultQuestion("wildcards", "core", 6));
        defaultQuestionService.save(new DefaultQuestion("PECS", "core", 6));
        defaultQuestionService.save(new DefaultQuestion("Iterable vs Iterator", "core", 6));
        defaultQuestionService.save(new DefaultQuestion("иерархия коллекций, какие структуры данных представляют коллекции стандартной библиотеки", "core", 6));
        defaultQuestionService.save(new DefaultQuestion("HashMap, бинарные, красночерные деревья", "core", 6));
        defaultQuestionService.save(new DefaultQuestion("comparable vs comparator", "core", 6));
        defaultQuestionService.save(new DefaultQuestion("Function vs Operator", "core", 6));
        defaultQuestionService.save(new DefaultQuestion("что такое Stream", "core", 6));
        defaultQuestionService.save(new DefaultQuestion("как можно создать стрим?", "core", 6));
        defaultQuestionService.save(new DefaultQuestion("зачем нужны стримы? какие преимущества дают ?", "core", 6));
        defaultQuestionService.save(new DefaultQuestion("какие операции бывают в стримах?", "core", 6));

        //web1

        defaultQuestionService.save(new DefaultQuestion("Что такое maven?", "web", 1));
        defaultQuestionService.save(new DefaultQuestion("Какие задачи решает maven?", "web", 1));
        defaultQuestionService.save(new DefaultQuestion("Что такое зависимость?", "web", 1));
        defaultQuestionService.save(new DefaultQuestion("Где хранятся зависимости локально?", "web", 1));
        defaultQuestionService.save(new DefaultQuestion("Maven lifecycle", "web", 1));
        defaultQuestionService.save(new DefaultQuestion("Что такое Jetty?", "web", 1));
        defaultQuestionService.save(new DefaultQuestion("HTTP, структура запросов и ответов, методы, коды состояний", "web", 1));
        defaultQuestionService.save(new DefaultQuestion("Что такое сервлет?", "web", 1));


//spring 1
        defaultQuestionService.save(new DefaultQuestion("Что такое Maven? Для чего он нужен? Как добавлять в проект библиотеки без него?", "spring", 1));
        defaultQuestionService.save(new DefaultQuestion("Как добавить dependency в Maven? Для чего они нужны? Откуда они скачиваются?", "spring", 1));
        defaultQuestionService.save(new DefaultQuestion("Основные фазы проекта под управлением Maven?", "spring", 1));
        defaultQuestionService.save(new DefaultQuestion("Что такое JDBC? Какие классы/интерфейсы относятся к JDBC?", "spring", 1));
        defaultQuestionService.save(new DefaultQuestion("Для чего нужен DriverManager?", "spring", 1));
        defaultQuestionService.save(new DefaultQuestion("Что такое Statement, PreparedStatement, CallableStatement?", "spring", 1));
        defaultQuestionService.save(new DefaultQuestion("Что такое sql-injection?", "spring", 1));
        defaultQuestionService.save(new DefaultQuestion("Что такое ResultSet? Как с ним работать?", "spring", 1));
        defaultQuestionService.save(new DefaultQuestion("Рассказать про паттерн DAO", "spring", 1));
        defaultQuestionService.save(new DefaultQuestion("Что такое JPA?", "spring", 1));
        defaultQuestionService.save(new DefaultQuestion("В чем разница между JPA и Hibernate? Как связаны все эти понятия?", "spring", 1));
        defaultQuestionService.save(new DefaultQuestion("Что такое Hibernate?", "spring", 1));
        defaultQuestionService.save(new DefaultQuestion("Какие классы/интерфейсы относятся к JPA/Hibernate?", "spring", 1));
        defaultQuestionService.save(new DefaultQuestion("Основные аннотации Hibernate, рассказать.", "spring", 1));
        defaultQuestionService.save(new DefaultQuestion("Чем HQL отличается от SQL?", "spring", 1));
        defaultQuestionService.save(new DefaultQuestion("Что такое ORM?", "spring", 1));
        defaultQuestionService.save(new DefaultQuestion("Что такое Query? Как передать в объект Query параметры?", "spring", 1));
        defaultQuestionService.save(new DefaultQuestion("Какие можно устанавливать параметры в hbm2ddl, рассказать про каждый из них.", "spring", 1));
        defaultQuestionService.save(new DefaultQuestion("Требования JPA к Entity-классам? Не менее пяти.", "spring", 1));
        defaultQuestionService.save(new DefaultQuestion("Жизненный цикл Entity в Hibernate? Рассказать.", "spring", 1));

        //spring 2
        defaultQuestionService.save(new DefaultQuestion("Что такое бин?", "spring", 2));
        defaultQuestionService.save(new DefaultQuestion("Какие бывают скоупы у бинов?", "spring", 2));
        defaultQuestionService.save(new DefaultQuestion("Что такое Inversion of Control и как Spring реализует этот принцип?", "spring", 2));
        defaultQuestionService.save(new DefaultQuestion("Как можно связать бины?", "spring", 2));
        defaultQuestionService.save(new DefaultQuestion("Что такое Dependency Injection?", "spring", 2));
        defaultQuestionService.save(new DefaultQuestion("Как получить данные из файла .property?", "spring", 2));
        defaultQuestionService.save(new DefaultQuestion("Что такое Spring Boot?", "spring", 2));
        defaultQuestionService.save(new DefaultQuestion("Какая разница между аннотациями @Component, @Repository, @Service и @Controller , @RestController в Spring?", "spring", 2));
        defaultQuestionService.save(new DefaultQuestion("Как выглядит структура MVC-приложения?", "spring", 2));
        defaultQuestionService.save(new DefaultQuestion("За что отвечают ViewResolver, DispatcherServlet и как между собой взаимодействуют?", "spring", 2));
        defaultQuestionService.save(new DefaultQuestion("Как вернуть страницу в контроллере? Как вернуть данные (например в json)?", "spring", 2));
        defaultQuestionService.save(new DefaultQuestion("Для чего нужны интерфейсы UserDetails и UserDetailsService?", "spring", 2));
        defaultQuestionService.save(new DefaultQuestion("Как мы можем добавить секьюрность к контроллеру? (минимум 2 способа).", "spring", 2));
        defaultQuestionService.save(new DefaultQuestion("Что будет являться эквивалентом пользователя и роли в приложении со Spring Security?", "spring", 2));
    }
}
