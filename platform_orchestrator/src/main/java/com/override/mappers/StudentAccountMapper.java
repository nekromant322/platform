package com.override.mappers;

import com.override.models.StudentAccount;
import dtos.StudentAccountDTO;
import org.springframework.stereotype.Component;

@Component
public class StudentAccountMapper {

    public StudentAccount dtoToEntity(StudentAccountDTO accountDTO) {
        return new StudentAccount(null, accountDTO.getLogin(), accountDTO.getPassword(), accountDTO.getTelegramChatId());
    }

    public StudentAccountDTO entityToDto(StudentAccount account) {
        return StudentAccountDTO.builder()
                .login(account.getLogin())
                .password(account.getPassword())
                .telegramChatId(account.getTelegramChatId())
                .build();
    }
}
