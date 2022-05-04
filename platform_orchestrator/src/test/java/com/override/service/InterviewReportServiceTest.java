package com.override.service;

import com.override.mappers.InterviewReportMapper;
import com.override.models.InterviewReport;
import com.override.models.PlatformUser;
import com.override.repositories.InterviewReportRepository;
import com.override.repositories.PlatformUserRepository;
import dtos.InterviewReportDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.override.utils.TestFieldsUtil.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InterviewReportServiceTest {

    @InjectMocks
    private InterviewReportService interviewReportService;

    @Mock
    private InterviewReportRepository interviewReportRepository;

    @Mock
    private InterviewReportMapper interviewReportMapper;

    @Mock
    private PlatformUserRepository platformUserRepository;

    @Test
    void saveInterviewReport() {
        InterviewReportDTO testInterviewReportDTO = generateTestInterviewReportDTO();
        PlatformUser testUser = generateTestUser();

        when(platformUserRepository.findFirstByLogin(testUser.getLogin())).thenReturn(testUser);

        interviewReportService.saveInterviewReport(testInterviewReportDTO, testUser.getLogin());
        verify(interviewReportRepository, times(1)).save(any());
        verify(interviewReportMapper, times(1)).dtoToEntity(any(), any());
    }

    @Test
    void deleteInterviewReport() {
        interviewReportService.deleteInterviewReport(1L);
        verify(interviewReportRepository,times(1)).deleteById(1L);
    }

    @Test
    void findAllInterviewReports() {
        List<InterviewReportDTO> testDTOList = List.of(generateTestInterviewReportDTO());
        List<InterviewReport> testList = List.of(generateTestInterviewReport());

        when(interviewReportRepository.findAll()).thenReturn(testList);
        when(interviewReportMapper.entityToDto(testList.iterator().next())).thenReturn(testDTOList.iterator().next());

        List<InterviewReportDTO> DTOList = interviewReportService.findAllInterviewReports();
        Assertions.assertEquals(DTOList.iterator().next(), testDTOList.iterator().next());
    }
}
