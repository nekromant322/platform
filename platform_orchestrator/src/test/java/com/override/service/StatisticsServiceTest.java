package com.override.service;

import com.override.mapper.CodeTryStatMapper;
import com.override.mapper.InterviewReportMapper;
import com.override.model.InterviewReport;
import com.override.model.enums.Status;
import com.override.repository.CodeTryRepository;
import com.override.repository.InterviewReportRepository;
import com.override.utils.TestFieldsUtil;
import dto.SalaryDTO;
import dto.SalaryStatDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatisticsServiceTest {

    @InjectMocks
    private StatisticsService statisticsService;

    @Mock
    private CodeTryRepository codeTryRepository;

    @Mock
    private CodeTryStatMapper codeTryStatMapper;

    @Mock
    private InterviewReportRepository interviewReportRepository;

    @Mock
    private InterviewReportMapper interviewReportMapper;

    @Test
    void testWhenGetStatistics() {
        statisticsService.getCodeTryStatistics(1);

        verify(codeTryStatMapper, times(1)).entityToDto(anyList(), anyList(), anyList(), anyList());
        verify(codeTryRepository, times(1)).countCodeTryByChapterAndStep();
        verify(codeTryRepository, times(1)).countStatsOfHardTasks(1);
        verify(codeTryRepository, times(1)).countStatsOfStatus();
        verify(codeTryRepository, times(1)).countStatsOfUsers();
    }

    @Test
    void testWhenCountStatsOfHardTasks() {
        statisticsService.getCodeTryStatistics(1);

        verify(codeTryRepository, times(1)).countStatsOfHardTasks(1);
    }

    @Test
    void testWhenGetSalaryStat() {
        List<InterviewReport> interviewReportList = TestFieldsUtil.generateInterviewReportsList();
        SalaryStatDTO salaryStatDTO = TestFieldsUtil.generateSalaryStatDto(interviewReportList);
        SalaryDTO salaryDTO = TestFieldsUtil.generateTestSalaryDTO(interviewReportList, salaryStatDTO);

        when(interviewReportRepository.findAll()).thenReturn(interviewReportList);
        when(interviewReportRepository.findAllByStatus(Status.ACCEPTED)).thenReturn(interviewReportList);
        when(interviewReportRepository.findAllByUserLoginAndStatus(anyString(), any(Status.class))).thenReturn(interviewReportList);
        when(interviewReportMapper.LoginAndSalariesToDto(anyString(), anyList())).thenReturn(salaryStatDTO);
        when(interviewReportMapper.salaryStatDtoToSalaryDto(any(), anyList())).thenReturn(salaryDTO);

        SalaryDTO resultSalary = statisticsService.getSalaryStatistics();

        verify(interviewReportRepository, times(1)).findAll();
        verify(interviewReportRepository, times(1)).findAllByStatus(Status.ACCEPTED);
        verify(interviewReportRepository, times(3)).findAllByUserLoginAndStatus(anyString(), any(Status.class));
        verify(interviewReportMapper, times(3)).LoginAndSalariesToDto(anyString(), anyList());
        verify(interviewReportMapper, times(1)).salaryStatDtoToSalaryDto(any(), anyList());

        assertNotNull(resultSalary.getSalaryStats());
        assertNotNull(resultSalary.getLabels());
        assertEquals(3, resultSalary.getLabels().size());
        assertEquals(1, resultSalary.getSalaryStats().size());
    }
}