package com.override.repositories;

import com.override.models.StudentAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentAccountRepository extends CrudRepository<StudentAccount, Long> {

    StudentAccount findFirstByTelegramChatId(String chatId);
}
