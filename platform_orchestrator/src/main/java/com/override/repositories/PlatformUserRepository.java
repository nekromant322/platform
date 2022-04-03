package com.override.repositories;

import com.override.models.Authority;
import com.override.models.PlatformUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlatformUserRepository extends CrudRepository<PlatformUser, Long> {

    PlatformUser findFirstByTelegramChatId(String chatId);

    PlatformUser findFirstByLogin(String login);

    List<PlatformUser> findByAuthoritiesNotContaining(Authority authority);

    @Query("select distinct users from PlatformUser users left join StudentReport sr on users.id = sr.student.id " +
            "where sr.date <> current_date")
    List<PlatformUser> findPlatformUsersWithoutReportOfCurrentDay();
}
