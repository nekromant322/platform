package com.override.repositories;

import com.override.models.StudentAccount;
import org.springframework.data.repository.CrudRepository;

public interface StudentAccountRepository extends CrudRepository<StudentAccount, Long> {

    StudentAccount findFirstByTelegramChatId(String chatId);

    StudentAccount findFirstByLogin(String login);
}
