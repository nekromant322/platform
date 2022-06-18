package com.override.service;

import com.override.exception.UserAlreadyExistException;
import com.override.model.Authority;
import com.override.model.PersonalData;
import com.override.model.PlatformUser;
import com.override.model.UserSettings;
import com.override.model.enums.Role;
import com.override.repository.PlatformUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class PlatformUserService {

    @Autowired
    private PlatformUserRepository platformUserRepository;
    @Autowired
    private PasswordGeneratorService passwordGeneratorService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthorityService authorityService;

    public PlatformUser save(PlatformUser platformUser) {
        return register(platformUser);
    }

    public PlatformUser generateAccount(String login) {
        String password = passwordGeneratorService.generateStrongPassword();
        List<Authority> roles = Collections.singletonList(authorityService.getAuthorityByRole(Role.USER));

        PlatformUser account = new PlatformUser(null, login, password, roles, new PersonalData(), new UserSettings());
        register(account);

        return account;
    }

    private PlatformUser register(PlatformUser studentAccount) {
        String login = studentAccount.getLogin();

        PlatformUser account = new PlatformUser(null,
                login,
                passwordEncoder.encode(studentAccount.getPassword()),
                studentAccount.getAuthorities(),
                new PersonalData(),
                new UserSettings()
        );

        if (platformUserRepository.findFirstByLogin(login) == null) {
            PlatformUser user = platformUserRepository.save(account);
            log.info("Пользователь с логином {} был успешно создан", login);
            return user;
        } else {
            throw new UserAlreadyExistException("Пользователь с логином " + login + " уже зарегистрирован");
        }
    }

    public ResponseEntity<String> updateToAdmin(Long id) {
        PlatformUser student = platformUserRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с id " + id + " не найден"));

        Authority adminAuthority = authorityService.getAuthorityByRole(Role.ADMIN);
        List<Authority> studentAuthorities = student.getAuthorities();
        studentAuthorities.add(adminAuthority);

        platformUserRepository.save(student);

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    public List<PlatformUser> getAllStudents() {
        Authority adminAuthority = authorityService.getAuthorityByRole(Role.ADMIN);

        return platformUserRepository.findByAuthoritiesNotContaining(adminAuthority);
    }


    public PlatformUser findPlatformUserByLogin(String login) {
        return platformUserRepository.findFirstByLogin(login);
    }

    public List<PlatformUser> findStudentsWithoutReportOfCurrentDay() {
        return platformUserRepository.findStudentsWithoutReportOfCurrentDay();
    }

    public Role getPlatformUserRole(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return Role.ADMIN;
        } else {
            return Role.USER;
        }
    }
}
