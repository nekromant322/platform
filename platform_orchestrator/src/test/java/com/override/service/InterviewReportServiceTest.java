package com.override.service;

import com.override.mappers.InterviewReportMapper;
import com.override.models.InterviewReport;
import com.override.models.enums.Status;
import com.override.repositories.InterviewReportRepository;
import dtos.InterviewReportDTO;
import dtos.InterviewReportUpdateDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.override.utils.TestFieldsUtil.generateTestInterviewReport;
import static com.override.utils.TestFieldsUtil.generateTestInterviewReportDTO;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InterviewReportServiceTest {

    @InjectMocks
    private InterviewReportService interviewReportService;

    @Mock
    private InterviewReportRepository interviewReportRepository;

    @Mock
    private InterviewReportMapper interviewReportMapper;

    @Test
    void save() {
        InterviewReportDTO testInterviewReportDTO = generateTestInterviewReportDTO();

        interviewReportService.save(testInterviewReportDTO);
        verify(interviewReportRepository, times(1)).save(any());
        verify(interviewReportMapper, times(1)).dtoToEntity(any());
    }

    @Test
    void update() {
        InterviewReport testInterviewReport = generateTestInterviewReport();
        InterviewReportUpdateDTO interviewReportUpdateDTO =
                InterviewReportUpdateDTO.builder()
                        .id(1L)
                        .salary(320000)
                        .build();

        when(interviewReportRepository.getById(interviewReportUpdateDTO.getId())).thenReturn(testInterviewReport);

        interviewReportService.update(interviewReportUpdateDTO, Status.OFFER);
        verify(interviewReportRepository, times(1)).save(any());
    }

    @Test
    void delete() {
        interviewReportService.delete(1L);
        verify(interviewReportRepository,times(1)).deleteById(1L);
    }

    @Test
    void findAll() {
        List<InterviewReportDTO> testDTOList = List.of(generateTestInterviewReportDTO());
        List<InterviewReport> testList = List.of(generateTestInterviewReport());

        when(interviewReportRepository.findAll()).thenReturn(testList);
        when(interviewReportMapper.entityToDto(testList.iterator().next())).thenReturn(testDTOList.iterator().next());

        List<InterviewReportDTO> DTOList = interviewReportService.findAll();
        Assertions.assertEquals(DTOList.iterator().next(), testDTOList.iterator().next());
    }
}
