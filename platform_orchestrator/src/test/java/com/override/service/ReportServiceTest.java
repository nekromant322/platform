package com.override.service;

import com.override.feigns.NotificatorFeign;
import com.override.models.Authority;
import com.override.models.PersonalData;
import com.override.models.PlatformUser;
import com.override.models.StudentReport;
import com.override.repositories.StudentReportRepository;
import com.override.utils.TestFieldsUtil;
import dtos.MessageDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
        List<PlatformUser> userList = List.of(new PlatformUser(null, "123", "123", "123",
                Collections.singletonList(new Authority(null, "ROLE_USER")), new PersonalData()));

        when(userService.findStudentsWithoutReportOfCurrentDay()).thenReturn(userList);

        MessageDTO message = MessageDTO.builder()
                .message("Привет, не забудь написать отчет \uD83D\uDE4A")
                .chatId("123")
                .build();

        reportService.sendDailyReminderOfReport();

        verify(userService, times(1)).findStudentsWithoutReportOfCurrentDay();
        verify(notificatorFeign, times(1)).sendTelegramMessages(message);
    }

    @Test
    public void testWhenSeveralUsersDidNotWriteAReportOnTheCurrentDay() {
        List<PlatformUser> userList = TestFieldsUtil.generateTestListOfThreeUsersWithoutReportsOnCurrentDay();

        when(userService.findStudentsWithoutReportOfCurrentDay()).thenReturn(userList);

        MessageDTO firstMessage = MessageDTO.builder()
                .message("Привет, не забудь написать отчет \uD83D\uDE4A")
                .chatId("1")
                .build();
        MessageDTO secondMessage = MessageDTO.builder()
                .message("Привет, не забудь написать отчет \uD83D\uDE4A")
                .chatId("2")
                .build();
        MessageDTO thirdMessage = MessageDTO.builder()
                .message("Привет, не забудь написать отчет \uD83D\uDE4A")
                .chatId("3")
                .build();

        reportService.sendDailyReminderOfReport();

        verify(userService, times(1)).findStudentsWithoutReportOfCurrentDay();
        verify(notificatorFeign, times(1)).sendTelegramMessages(firstMessage);
        verify(notificatorFeign, times(1)).sendTelegramMessages(secondMessage);
        verify(notificatorFeign, times(1)).sendTelegramMessages(thirdMessage);
    }

    @Test
    public void testWhenNotFoundStudentsWithoutReportInCurrentDay() {
        when(userService.findStudentsWithoutReportOfCurrentDay()).thenReturn(new ArrayList<>());

        reportService.sendDailyReminderOfReport();

        verify(userService, times(1)).findStudentsWithoutReportOfCurrentDay();
        verify(notificatorFeign, times(0)).sendTelegramMessages(any());
    }
}