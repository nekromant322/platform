package com.override.util;

import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.override.models.Authority;
import com.override.models.PlatformUser;
import com.override.models.enums.Role;
import com.override.service.AuthorityService;
import com.override.service.PlatformUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class TestDataInitializer {

    @Autowired
    AuthorityService authorityService;

    @Autowired
    PlatformUserService platformUserService;

    @PostConstruct
    public void init () {
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-GB"), new RandomService());
        List<PlatformUser> testPlatformUsers = new ArrayList<>();
        Authority userAuthority = authorityService.getAuthorityByRole(Role.USER);


        for (int i = 0; i < 10; i++) {
            testPlatformUsers.add(new PlatformUser());
        }

        testPlatformUsers.forEach(platformUser -> platformUser.getAuthorities().add(userAuthority));
        testPlatformUsers.forEach(platformUser -> platformUser.setLogin(fakeValuesService.bothify("?????##")));
        testPlatformUsers.forEach(platformUser -> platformUser.setPassword(fakeValuesService.bothify("########")));
        testPlatformUsers.forEach(platformUser -> platformUser.setTelegramChatId(fakeValuesService.bothify("@?????")));
        testPlatformUsers.forEach(platformUser -> platformUserService.save(platformUser));

    }
}


