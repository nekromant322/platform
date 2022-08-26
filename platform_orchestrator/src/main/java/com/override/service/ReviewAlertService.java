package com.override.service;

import com.override.feign.NotificatorFeign;
import com.override.model.PlatformUser;
import com.override.repository.ReviewRepository;
import enums.Communication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReviewAlertService {

    @Value("${reviewAlert.daysWithoutReviewAlertThreshold}")
    private int days;

    @Autowired
    private PlatformUserService platformUserService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private NotificatorFeign notificatorFeign;

    public void alertBadStudents() {

        List<PlatformUser> students = platformUserService.getAllStudents();
        List<PlatformUser> admins = platformUserService.getAllAdmins();

        for (PlatformUser student : students) {
            int countDays = Math.abs(reviewRepository.findFirstByStudentId(student.getId()).getBookedDate().getDayOfMonth() - LocalDate.now().getDayOfMonth());
            String adminMessage = "студент " + student.getLogin() + " давно не был на ревью ";
            if (countDays > days) {
                for (PlatformUser admin : admins) {
                    notificatorFeign.sendMessage(admin.getLogin(), adminMessage, Communication.TELEGRAM);
                }
            }
        }
    }
}
