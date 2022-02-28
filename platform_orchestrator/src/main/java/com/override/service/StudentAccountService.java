package com.override.service;

import com.override.models.JoinRequest;
import com.override.models.StudentAccount;
import com.override.repositories.StudentAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentAccountService {

    @Autowired
    private StudentAccountRepository accountRepository;

    @Autowired
    private PasswordGeneratorService passwordGeneratorService;

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
