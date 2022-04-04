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

    @Query("select distinct users from PlatformUser users where users.id not in (select studentReport.id " +
            "from StudentReport studentReport where studentReport.date = current_date) or users.id not in " +
            "(select studentReport.id from StudentReport studentReport)")
    List<PlatformUser> findPlatformUsersWithoutReportOfCurrentDay();
}
