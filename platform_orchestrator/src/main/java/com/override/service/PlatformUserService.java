package com.override.service;

import com.override.exception.UserAlreadyExistException;
import com.override.models.Authority;
import com.override.models.PlatformUser;
import com.override.models.enums.Role;
import com.override.repositories.PlatformUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlatformUserService {

    @Autowired
    private PlatformUserRepository accountRepository;
    @Autowired
    private PasswordGeneratorService passwordGeneratorService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthorityService authorityService;

    public PlatformUser getAccountByChatId(String chatId) {
        return accountRepository.findFirstByTelegramChatId(chatId);
    }

    public PlatformUser save(PlatformUser platformUser) {
        return accountRepository.save(platformUser);
    }

    public PlatformUser generateAccount(String login, String chatId) {
        String password = passwordGeneratorService.generateStrongPassword();
        List<Authority> roles = getAuthorityListFromRoles(Role.USER);

        PlatformUser account = new PlatformUser(null, login, password, chatId, roles);
        register(account);

        return account;
    }

    public PlatformUser saveAdmin(String login, String password) {
        List<Authority> roles = getAuthorityListFromRoles(Role.ADMIN, Role.USER);

        PlatformUser account = new PlatformUser(null, login, password, null, roles);
        register(account);

        return account;
    }

    private void register(PlatformUser studentAccount) {
        String login = studentAccount.getLogin();

        PlatformUser account = new PlatformUser(null,
                login,
                passwordEncoder.encode(studentAccount.getPassword()),
                studentAccount.getTelegramChatId(),
                studentAccount.getAuthorities()
        );

        if (accountRepository.findFirstByLogin(login) == null) {
            accountRepository.save(account);
            log.info("Пользователь с логином {} был успешно создан", login);
        } else {
            throw new UserAlreadyExistException("Пользователь с логином " + login + " уже зарегистрирован");
        }
    }

    private List<Authority> getAuthorityListFromRoles(Role... roles) {
        List<Authority> authorityList = new ArrayList<>();
        for (Role role : roles) {
            authorityList.add(authorityService.getAuthorityByRole(role));
        }
        return authorityList;
    }


    public ResponseEntity<String> updateToAdmin(Long id) {
        PlatformUser student = accountRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с id " + id + " не найден"));

        Authority adminAuthority = authorityService.getAuthorityByRole(Role.ADMIN);
        List<Authority> studentAuthorities = student.getAuthorities();
        studentAuthorities.add(adminAuthority);

        accountRepository.save(student);

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    public List<PlatformUser> getAllStudents() {
        Authority adminAuthority = authorityService.getAuthorityByRole(Role.ADMIN);

        return accountRepository.findByAuthoritiesNotContaining(adminAuthority);
    }

    public PlatformUser findPlatformUserByLogin(String login) {
        return accountRepository.findFirstByLogin(login);
    }
}
