package com.override.service;

import com.override.models.PlatformUser;
import com.override.models.StudentReport;
import com.override.repositories.PlatformUserRepository;
import com.override.repositories.StudentReportRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    private PlatformUserRepository userRepository;

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
    public void testWhenSendReminderOfReport() {
//        when(userRepository.findPlatformUsersWithoutReportOfCurrentDay());
    }
}