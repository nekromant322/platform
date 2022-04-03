package com.override.service;

import com.override.feigns.NotificatorFeign;
import com.override.feigns.TelegramBotFeign;
import com.override.models.PlatformUser;
import com.override.models.StudentReport;
import com.override.repositories.StudentReportRepository;
import dtos.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

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

    @Scheduled(cron = "${spring.datasource.hikari.scheduled-executor.cron}", zone = "${spring.datasource.hikari.scheduled-executor.zone}")
    public void reminderOfReport() {
        ZoneId zoneId = ZoneId.of("Europe/Moscow");

        for (PlatformUser user : userService.getAllStudents()) {
            if (reportRepository.findFirstByDateAndStudentLogin(LocalDate.now(zoneId), user.getLogin()) == null) {
                MessageDTO message = MessageDTO.builder().build();
                message.setMessage("Привет, не забудь написать отчет \uD83D\uDE4A");
                message.setChatId(user.getTelegramChatId());
                notificatorFeign.sendReminderOfReport(message);
            }
        }


    }
}