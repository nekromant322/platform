package com.override.util;

import com.override.models.Authority;
import com.override.models.PlatformUser;
import com.override.models.enums.Role;
import com.override.service.AuthorityService;
import com.override.service.PlatformUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitializationService implements CommandLineRunner {

    @Value("${jwt.primeAdminLogin}")
    private String adminLogin;

    @Value("${jwt.primeAdminLogin}")
    private String adminPassword;

    private static final int COUNT_USER_FOR_INIT = 5;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private PlatformUserService userService;

    @Override
    public void run(String... args) {
        AuthorityInit();
        UserInit();
        AdminInit();
    }

    private void AuthorityInit() {
        for (Role role : Role.values()) {
            authorityService.save(role.getName());
        }
    }

    private void UserInit() {
        for (int i = 0; i < COUNT_USER_FOR_INIT; i++) {
            saveUser(i + "login", String.valueOf(i), Role.USER);
        }
    }

    private void AdminInit() {
        saveUser(adminLogin, adminPassword, Role.USER, Role.ADMIN);
    }

    private PlatformUser saveUser(String login, String password, Role... userRoles) {
        List<Authority> roles = getAuthorityListFromRoles(userRoles);

        PlatformUser account = new PlatformUser(null, login, password, null, roles);
        return userService.save(account);
    }

    private List<Authority> getAuthorityListFromRoles(Role... roles) {
        List<Authority> authorityList = new ArrayList<>();
        for (Role role : roles) {
            authorityList.add(authorityService.getAuthorityByRole(role));
        }
        return authorityList;
    }
}