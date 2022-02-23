package com.override.util;

import com.override.models.enums.Role;
import com.override.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorityInit implements CommandLineRunner {

    private final AuthorityService authorityService;

    @Override
    public void run(String... args) {
        for (Role role : Role.values()) {
            authorityService.save(role.getName());
        }
    }
}