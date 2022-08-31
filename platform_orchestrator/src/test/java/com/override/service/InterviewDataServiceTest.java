package com.override.service;

import com.override.mapper.InterviewDataMapper;
import com.override.model.InterviewData;
import com.override.repository.InterviewDataRepository;
import dto.InterviewDataDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.override.utils.TestFieldsUtil.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class InterviewDataServiceTest {

    @InjectMocks
    private InterviewDataService interviewDataService;

    @Mock
    private InterviewDataRepository interviewDataRepository;

    @Mock
    private InterviewDataMapper interviewDataMapper;

    @Test
    public void testFindAll() {
        List<InterviewDataDTO> testDTOList = List.of(generateTestInterviewDataDTO());
        List<InterviewData> testList = List.of(generateTestInterviewData());

        when(interviewDataRepository.findAll()).thenReturn(testList);
        when(interviewDataMapper.entityToDto(testList.iterator().next())).thenReturn(testDTOList.iterator().next());

        List<InterviewDataDTO> DTOList = interviewDataService.findAll();
        Assertions.assertEquals(DTOList.iterator().next(), testDTOList.iterator().next());
    }

    @Test
    public void testDelete() {
        interviewDataService.delete(1L);
        verify(interviewDataRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testSave() {
        InterviewDataDTO testInterviewDataDTO = generateTestInterviewDataDTO();

        interviewDataService.save(testInterviewDataDTO);
        verify(interviewDataRepository, times(1)).save(any());
        verify(interviewDataMapper, times(1)).dtoToEntity(any());

    }

    @Test
    public void findById() {
        Optional<InterviewData> optionalInterviewData = Optional.of(generateTestInterviewData());
        when(interviewDataRepository.findById(1L)).thenReturn(optionalInterviewData);

        InterviewData interviewData = interviewDataService.findById(1L);

        Assertions.assertEquals(interviewData, optionalInterviewData.get());
    }
}
