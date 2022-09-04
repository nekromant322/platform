package com.override.mapper;

import com.override.model.InterviewData;
import dto.InterviewDataDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.override.utils.TestFieldsUtil.generateTestInterviewData;
import static com.override.utils.TestFieldsUtil.generateTestInterviewDataDTO;

@ExtendWith(MockitoExtension.class)
public class InterviewDataMapperTest {
    @InjectMocks
    private InterviewDataMapper InterviewDataMapper;

    @Test
    public void testDtoToEntity() {
        InterviewData testInterviewData = generateTestInterviewData();
        InterviewDataDTO testInterviewDataDTO = generateTestInterviewDataDTO();

        InterviewData interviewData = InterviewDataMapper.dtoToEntity(testInterviewDataDTO);

        Assertions.assertEquals(interviewData.getId(), testInterviewData.getId());
        Assertions.assertEquals(interviewData.getUserLogin(), testInterviewData.getUserLogin());
        Assertions.assertEquals(interviewData.getCompany(), testInterviewData.getCompany());
        Assertions.assertEquals(interviewData.getDescription(), testInterviewData.getDescription());
        Assertions.assertEquals(interviewData.getContacts(), testInterviewData.getContacts());
        Assertions.assertEquals(interviewData.getDate(), testInterviewData.getDate());
        Assertions.assertEquals(interviewData.getTime(), testInterviewData.getTime());
        Assertions.assertEquals(interviewData.getComment(), testInterviewData.getComment());
        Assertions.assertEquals(interviewData.getStack(), testInterviewData.getStack());
        Assertions.assertEquals(interviewData.getSalary(), testInterviewData.getSalary());
        Assertions.assertEquals(interviewData.getMeetingLink(), testInterviewData.getMeetingLink());
        Assertions.assertEquals(interviewData.getDistanceWork(), testInterviewData.getDistanceWork());
    }

    @Test
    public void testEntityToDto() {
        InterviewData testInterviewData = generateTestInterviewData();
        InterviewDataDTO testInterviewDataDTO = generateTestInterviewDataDTO();

        InterviewDataDTO interviewDataDTO = InterviewDataMapper.entityToDto(testInterviewData);

        Assertions.assertEquals(interviewDataDTO.getId(), testInterviewDataDTO.getId());
        Assertions.assertEquals(interviewDataDTO.getUserLogin(), testInterviewDataDTO.getUserLogin());
        Assertions.assertEquals(interviewDataDTO.getCompany(), testInterviewDataDTO.getCompany());
        Assertions.assertEquals(interviewDataDTO.getDescription(), testInterviewDataDTO.getDescription());
        Assertions.assertEquals(interviewDataDTO.getContacts(), testInterviewDataDTO.getContacts());
        Assertions.assertEquals(interviewDataDTO.getDate(), testInterviewDataDTO.getDate());
        Assertions.assertEquals(interviewDataDTO.getTime(), testInterviewDataDTO.getTime());
        Assertions.assertEquals(interviewDataDTO.getComment(), testInterviewDataDTO.getComment());
        Assertions.assertEquals(interviewDataDTO.getStack(), testInterviewDataDTO.getStack());
        Assertions.assertEquals(interviewDataDTO.getSalary(), testInterviewDataDTO.getSalary());
        Assertions.assertEquals(interviewDataDTO.getMeetingLink(), testInterviewDataDTO.getMeetingLink());
        Assertions.assertEquals(interviewDataDTO.getDistanceWork(), testInterviewDataDTO.getDistanceWork());
    }
}
