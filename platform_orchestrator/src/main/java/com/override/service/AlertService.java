package com.override.service;

import com.override.feign.NotificatorFeign;
import com.override.model.PlatformUser;
import com.override.model.enums.CoursePart;
import com.override.repository.CodeTryRepository;
import enums.Communication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class AlertService {

    @Value("${alert.daysWithoutNewSolutionsAlertThreshold}")
    private int days;

    @Autowired
    private PlatformUserService platformUserService;

    @Autowired
    private CodeTryRepository codeTryRepository;

    @Autowired
    private NotificatorFeign notificatorFeign;

    public void alertBadStudents() {

        List<PlatformUser> students = platformUserService.getStudentsByCoursePart(CoursePart.CORE.ordinal());
        List<PlatformUser> admins = platformUserService.getAllAdmins();

        for (PlatformUser student : students) {
            long countDays = DAYS.between(codeTryRepository.findFirstByUserIdOrderByDate(student.getId()).getDate(), LocalDateTime.now());
            String studentMessage = countDays + " - уже столько дней ты не отправляешь новых решений :(";
            String adminMessage = countDays + " - столько дней " + student.getLogin() + " не присылал новых решений на платформу";

            if (countDays > days) {
                notificatorFeign.sendMessage(student.getLogin(), studentMessage, Communication.EMAIL);

                for (PlatformUser admin : admins) {
                    notificatorFeign.sendMessage(admin.getLogin(), adminMessage, Communication.TELEGRAM);
                }
            }
        }
    }
}
