package com.override.service;

import com.override.exception.UserAlreadyExistException;
import com.override.models.Authority;
import com.override.models.StudentAccount;
import com.override.models.enums.Role;
import com.override.repositories.StudentAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentAccountService {

    private final StudentAccountRepository accountRepository;
    private final PasswordGeneratorService passwordGeneratorService;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityService authorityService;

    public StudentAccount getAccountByChatId(String chatId) {
        return accountRepository.findFirstByTelegramChatId(chatId);
    }

    public StudentAccount generateAccount(String login, String chatId) {
        String password = passwordGeneratorService.generateStrongPassword();
        List<Authority> roles = getAuthorityListFromRoles(Role.USER);

        StudentAccount account = new StudentAccount(null, login, password, chatId, roles);
        register(account);

        return account;
    }

    public StudentAccount saveAdmin(String login, String password) {
        List<Authority> roles = getAuthorityListFromRoles(Role.ADMIN, Role.USER);

        StudentAccount account = new StudentAccount(null, login, password, null, roles);
        register(account);

        return account;
    }

    private void register(StudentAccount studentAccount) {
        String login = studentAccount.getLogin();

        StudentAccount account = new StudentAccount(null,
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
}
