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

    @Query(value = "select p_user.id, p_user.login, p_user.password, p_user.telegram_chat_id " +
            "from platform_user p_user" +
            "         left join student_report report on p_user.id = report.student_id " +
            "where p_user.id not in (select report.student_id " +
            "                    from student_report report " +
            "                    where report.date = current_date) " +
            "  and p_user.id not in (select authorities.user_id " +
            "                    from users_authorities authorities " +
            "                             join role_t role on role.id = authorities.authority_id " +
            "                    where role.authority like 'ROLE_ADMIN') " +
            "group by p_user.id, p_user.login, p_user.password, p_user.telegram_chat_id",
            nativeQuery = true)
    List<PlatformUser> findStudentsWithoutReportOfCurrentDay();
}
