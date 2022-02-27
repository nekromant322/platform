package com.override.util;

import com.override.models.Authority;
import com.override.models.PlatformUser;
import com.override.models.enums.Role;
import com.override.service.AuthorityService;
import com.override.service.PlatformUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitializationService implements CommandLineRunner {

    private static final int COUNT_USER_FOR_INIT = 5;

    private final AuthorityService authorityService;
    private final PlatformUserService userService;

    private final PasswordEncoder passwordEncoder;

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
        List<Authority> userAuthority = getAuthorityListFromRoles(Role.USER);
        for (int i = 0; i < COUNT_USER_FOR_INIT; i++) {
            PlatformUser user = new PlatformUser();
            user.setLogin(i + "login");
            user.setPassword(passwordEncoder.encode(String.valueOf(i)));
            user.setAuthorities(userAuthority);
            userService.save(user);
        }
    }

    private void AdminInit() {
        userService.saveAdmin("admin", "admin");
    }

    private List<Authority> getAuthorityListFromRoles(Role... roles) {
        List<Authority> authorityList = new ArrayList<>();
        for (Role role : roles) {
            authorityList.add(authorityService.getAuthorityByRole(role));
        }
        return authorityList;
    }
}