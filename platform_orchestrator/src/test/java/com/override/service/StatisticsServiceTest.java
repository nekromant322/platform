package com.override.service;

import com.override.mappers.CodeTryMapper;
import com.override.mappers.CodeTryStatMapper;
import com.override.repositories.CodeTryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatisticsServiceTest {
    @InjectMocks
    StatisticsService statisticsService;
    @Mock
    CodeTryRepository codeTryRepository;
    @Mock
    CodeTryStatMapper codeTryStatMapper;

    @Test
    void getStatistics() {
        statisticsService.getStatistics(1);

        verify(codeTryStatMapper, times(1)).entityToDto(anyList(), anyList(), anyList(), anyList());
        verify(codeTryRepository,times(1)).countCodeTryByChapterAndStep();
        verify(codeTryRepository,times(1)).countStatsOfHardTasks(1);
        verify(codeTryRepository, times(1)).countStatsOfStatus();
        verify(codeTryRepository, times(1)).countStatsOfUsers();
    }

    @Test
    void countStatsOfHardTasks() {
        statisticsService.getStatistics(1);

        verify(codeTryRepository, times(1)).countStatsOfHardTasks(1);
    }
}