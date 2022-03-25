package com.override.util;

import com.github.javafaker.Faker;
import com.override.models.Authority;
import com.override.models.PlatformUser;
import com.override.models.enums.Role;
import com.override.service.AuthorityService;
import com.override.service.CodeTryService;
import com.override.service.JoinRequestService;
import com.override.service.PlatformUserService;
import dtos.CodeTryDTO;
import dtos.RegisterUserRequestDTO;
import dtos.TaskIdentifierDTO;
import dtos.TestResultDTO;
import enums.CodeExecutionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private CodeTryService codeTryService;

    @Autowired
    private JoinRequestService joinRequestService;

    private final Faker faker = new Faker();

    @PostConstruct
    private void init() {
        authorityInit();
        adminInit();
        userInit();
        codeTryInit();
        joinRequestsInit();

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
                    String.valueOf(faker.number().numberBetween(1000, 10000)),
                    Role.USER);
        }
    }

    private void adminInit() {
        saveUser(adminLogin, adminPassword, adminTelegramChatId, Role.USER, Role.ADMIN);
    }

    private void saveUser(String login, String password, String telegramChatId, Role... userRoles) {
        List<Authority> roles = getAuthorityListFromRoles(userRoles);

        PlatformUser account = new PlatformUser(null, login, password, telegramChatId, roles);
        userService.save(account);
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
        students.forEach(this::addCodeTry);
    }

    private void addCodeTry(PlatformUser student) {
        CodeExecutionStatus status;
        for (int i = 0; i < 10; i++) {
            status = CodeExecutionStatus
                    .values()[new Random().nextInt(CodeExecutionStatus.values().length)];
            while (status != CodeExecutionStatus.OK) {
                saveCodeTry(student, i, status);
                status = CodeExecutionStatus
                        .values()[new Random().nextInt(CodeExecutionStatus.values().length)];
            }
            saveCodeTry(student, i, CodeExecutionStatus.OK);

        }
    }

    private void saveCodeTry(PlatformUser student, int task, CodeExecutionStatus status) {
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
                                .chapter(task)
                                .lesson(task)
                                .step(task)
                                .build())
                        .studentsCode(helloWorld)
                        .build(),
                TestResultDTO
                        .builder()
                        .codeExecutionStatus(status)
                        .output(faker.funnyName().name()).build(),
                student.getLogin());
    }

    private void joinRequestsInit() {
        for (int i = 0; i < requestsCount; i++) {
            joinRequestService.saveRequest(new RegisterUserRequestDTO(faker.name().username(),
                    String.valueOf(faker.number().numberBetween(1000, 10000))));
        }
    }


}