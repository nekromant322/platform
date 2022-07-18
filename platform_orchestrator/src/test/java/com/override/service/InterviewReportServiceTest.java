package com.override.service;

import com.override.mapper.InterviewReportMapper;
import com.override.model.InterviewReport;
import com.override.model.enums.Status;
import com.override.repository.InterviewReportRepository;
import dto.InterviewReportDTO;
import dto.InterviewReportUpdateDTO;
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
    public void testSave() {
        InterviewReportDTO testInterviewReportDTO = generateTestInterviewReportDTO();

        interviewReportService.save(testInterviewReportDTO);
        verify(interviewReportRepository, times(1)).save(any());
        verify(interviewReportMapper, times(1)).dtoToEntity(any());
    }

    @Test
    public void testUpdate() {
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
    public void testDelete() {
        interviewReportService.delete(1L);
        verify(interviewReportRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindAll() {
        List<InterviewReportDTO> testDTOList = List.of(generateTestInterviewReportDTO());
        List<InterviewReport> testList = List.of(generateTestInterviewReport());

        when(interviewReportRepository.findAll()).thenReturn(testList);
        when(interviewReportMapper.entityToDto(testList.iterator().next())).thenReturn(testDTOList.iterator().next());

        List<InterviewReportDTO> DTOList = interviewReportService.findAll();
        Assertions.assertEquals(DTOList.iterator().next(), testDTOList.iterator().next());
    }
}
