package com.override.service;

import com.override.feign.NotificatorFeign;
import com.override.model.PlatformUser;
import com.override.model.StudentReport;
import com.override.repository.StudentReportRepository;
import enums.Communication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    private StudentReportRepository reportRepository;
    @Autowired
    private PlatformUserService userService;
    @Autowired
    private NotificatorFeign notificatorFeign;

    public ResponseEntity<String> saveReport(StudentReport report, String studentLogin) {
        if (reportRepository.findFirstByDateAndStudentLogin(report.getDate(), studentLogin) != null) {
            return new ResponseEntity<>("Уже есть отчет на эту дату", HttpStatus.CONFLICT);
        }
        PlatformUser student = userService.findPlatformUserByLogin(studentLogin);
        report.setStudent(student);
        reportRepository.save(report);
        return new ResponseEntity<>("Отчет принят\n" + report.toString(), HttpStatus.OK);
    }

    @Scheduled(cron = "${schedulers.report-reminders.cron}", zone = "${schedulers.zone}")
    public void sendDailyReminderOfReport() {
        String message = "Привет, не забудь написать отчет \uD83D\uDE4A";
        userService.findStudentsWithoutReportOfCurrentDay()
                .forEach(user -> notificatorFeign.sendMessage(user.getLogin(), message, Communication.TELEGRAM));
    }
}