package com.override.service;

import com.override.feigns.NotificatorFeign;
import com.override.models.Authority;
import com.override.models.PlatformUser;
import com.override.models.StudentReport;
import com.override.repositories.PlatformUserRepository;
import com.override.repositories.StudentReportRepository;
import com.override.utils.TestFieldsUtil;
import dtos.MessageDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @InjectMocks
    private ReportService reportService;

    @Mock
    private StudentReportRepository reportRepository;

    @Mock
    private PlatformUserService userService;

    @Mock
    private NotificatorFeign notificatorFeign;

    @Test
    public void testWhenNewReport() {
        StudentReport report = new StudentReport();

        when(reportRepository.findFirstByDateAndStudentLogin(any(), any())).thenReturn(null);
        when(userService.findPlatformUserByLogin(any())).thenReturn(new PlatformUser());

        ResponseEntity<String> entity = reportService.saveReport(report, "kemenchik");

        Assertions.assertEquals(new ResponseEntity<>("Отчет принят\n" + report.toString(), HttpStatus.OK), entity);
        verify(reportRepository, times(1)).save(report);
    }

    @Test
    public void testWhenReportOnDateExist() {
        when(reportRepository.findFirstByDateAndStudentLogin(any(), any())).thenReturn(new StudentReport());

        ResponseEntity<String> entity = reportService.saveReport(new StudentReport(), "kemenchik");

        Assertions.assertEquals(new ResponseEntity<>("Уже есть отчет на эту дату", HttpStatus.CONFLICT), entity);
        verify(userService, times(0)).findPlatformUserByLogin(any());
    }

    @Test
    public void testWhenSendDailyReminderOfReport() {
        List<PlatformUser> userList = new ArrayList<>();
        userList.add(new PlatformUser());

        when(userService.findStudentsWithoutReportOfCurrentDay()).thenReturn(userList);
        doNothing().when(notificatorFeign).sendTelegramMessages(any());

        reportService.sendDailyReminderOfReport();

        verify(userService, times(1)).findStudentsWithoutReportOfCurrentDay();
        verify(notificatorFeign, times(1)).sendTelegramMessages(any());
    }

    @Test
    public void testWhenNotFoundStudentsWithoutReportInCurrentDay() {
        when(userService.findStudentsWithoutReportOfCurrentDay()).thenReturn(new ArrayList<>());

        reportService.sendDailyReminderOfReport();

        verify(userService, times(1)).findStudentsWithoutReportOfCurrentDay();
        verify(notificatorFeign, times(0)).sendTelegramMessages(any());
    }
}