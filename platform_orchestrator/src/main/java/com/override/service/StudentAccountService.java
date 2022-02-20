package com.override.service;

import com.override.models.JoinRequest;
import com.override.models.StudentAccount;
import com.override.repositories.StudentAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentAccountService {

    private final StudentAccountRepository accountRepository;
    private final PasswordGeneratorService passwordGeneratorService;

    public StudentAccount generateAccount(JoinRequest requestInfo) {
        String login = requestInfo.getNickName();
        String password = passwordGeneratorService.generateStrongPassword();
        // TODO исправить на bcrypt.encode()
        return accountRepository.save(new StudentAccount(null, login, password, requestInfo.getChatId()));
    }

    public StudentAccount getAccountByChatId(String chatId) {
        return accountRepository.findFirstByTelegramChatId(chatId);
    }
}
