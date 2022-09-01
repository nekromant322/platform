package com.override.service;

import com.override.feign.NotificatorFeign;
import com.override.model.PlatformUser;
import com.override.model.enums.CoursePart;
import com.override.repository.CodeTryRepository;
import com.override.repository.ReviewRepository;
import enums.Communication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class AlertService {

    @Value("${alert.daysWithoutNewSolutionsAlertThreshold}")
    private long days;

    @Value("${reviewAlert.daysWithoutReviewAlertThreshold}")
    private long daysForReview;

    @Autowired
    private PlatformUserService platformUserService;

    @Autowired
    private CodeTryRepository codeTryRepository;

    @Autowired
    private NotificatorFeign notificatorFeign;

    @Autowired
    private ReviewRepository reviewRepository;

    public void setDays(long days) {
        this.days = days;
    }

    public void setDaysForReview(long daysForReview) {
        this.daysForReview = daysForReview;
    }

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

    public void alertMentorsAboutBadStudents() {

        List<PlatformUser> students = platformUserService.getAllStudents();
        List<PlatformUser> admins = platformUserService.getAllAdmins();

        for (PlatformUser student : students) {
            if (reviewRepository.findFirstByStudentIdOrderByIdDesc(student.getId()) != null) {
                long countDays = DAYS.between(reviewRepository.findFirstByStudentIdOrderByIdDesc(student.getId()).getBookedDate(), LocalDate.now());
                if (countDays > daysForReview) {
                    String adminMessage = "студент " + student.getLogin() + " давно не был на ревью ";
                    for (PlatformUser admin : admins) {
                        notificatorFeign.sendMessage(admin.getLogin(), adminMessage, Communication.TELEGRAM);
                    }
                }
            }
        }
    }
}
