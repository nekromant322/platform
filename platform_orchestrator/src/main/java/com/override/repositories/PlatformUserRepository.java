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

    @Query(value = "select pu.id, pu.login, pu.password, pu.telegram_chat_id " +
            "from platform_user pu" +
            "         left join student_report sr on pu.id = sr.student_id " +
            "where pu.id not in (select sr.student_id " +
            "                    from student_report sr " +
            "                    where sr.date = current_date) " +
            "  and pu.id not in (select ua.user_id " +
            "                    from users_authorities ua " +
            "                             join role_t rt on rt.id = ua.authority_id " +
            "                    where rt.authority like 'ROLE_ADMIN') " +
            "group by pu.id, pu.login, pu.password, pu.telegram_chat_id",
            nativeQuery = true)
    List<PlatformUser> findStudentsWithoutReportOfCurrentDay();
}
