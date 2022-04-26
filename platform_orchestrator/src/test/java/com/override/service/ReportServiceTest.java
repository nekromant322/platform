package com.override.service;

import com.override.feigns.NotificatorFeign;
import com.override.models.Authority;
import com.override.models.PersonalData;
import com.override.models.PlatformUser;
import com.override.models.StudentReport;
import com.override.repositories.StudentReportRepository;
import com.override.utils.TestFieldsUtil;
import enums.Communication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        assertEquals(new ResponseEntity<>("Отчет принят\n" + report, HttpStatus.OK), entity);
        verify(reportRepository, times(1)).save(report);
    }

    @Test
    public void testWhenReportOnDateExist() {
        when(reportRepository.findFirstByDateAndStudentLogin(any(), any())).thenReturn(new StudentReport());

        ResponseEntity<String> entity = reportService.saveReport(new StudentReport(), "kemenchik");

        assertEquals(new ResponseEntity<>("Уже есть отчет на эту дату", HttpStatus.CONFLICT), entity);
        verify(userService, times(0)).findPlatformUserByLogin(any());
    }

    @Test
    public void testWhenSendDailyReminderOfReport() {
        List<PlatformUser> userList = List.of(new PlatformUser(null, "123", "123",
                Collections.singletonList(new Authority(null, "ROLE_USER")), new PersonalData()));

        when(userService.findStudentsWithoutReportOfCurrentDay()).thenReturn(userList);

        String message = "Привет, не забудь написать отчет \uD83D\uDE4A";

        reportService.sendDailyReminderOfReport();

        verify(userService, times(1)).findStudentsWithoutReportOfCurrentDay();
        verify(notificatorFeign, times(1)).sendMessage("123", message, Communication.TELEGRAM);
    }

    @Test
    public void testWhenSeveralUsersDidNotWriteAReportOnTheCurrentDay() {
        List<PlatformUser> userList = TestFieldsUtil.generateTestListOfThreeUsersWithoutReportsOnCurrentDay();

        when(userService.findStudentsWithoutReportOfCurrentDay()).thenReturn(userList);

        String message = "Привет, не забудь написать отчет \uD83D\uDE4A";

        reportService.sendDailyReminderOfReport();

        verify(userService, times(1)).findStudentsWithoutReportOfCurrentDay();
        verify(notificatorFeign, times(1)).sendMessage("1", message, Communication.TELEGRAM);
        verify(notificatorFeign, times(1)).sendMessage("2", message, Communication.TELEGRAM);
        verify(notificatorFeign, times(1)).sendMessage("3", message, Communication.TELEGRAM);
    }

    @Test
    public void testWhenNotFoundStudentsWithoutReportInCurrentDay() {
        when(userService.findStudentsWithoutReportOfCurrentDay()).thenReturn(new ArrayList<>());

        reportService.sendDailyReminderOfReport();

        verify(userService, times(1)).findStudentsWithoutReportOfCurrentDay();
        verify(notificatorFeign, times(0)).sendMessage(any(), any(), any());
    }
}