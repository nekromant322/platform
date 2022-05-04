package com.override.mappers;

import com.override.models.InterviewReport;
import com.override.models.PlatformUser;
import dtos.InterviewReportDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.override.utils.TestFieldsUtil.*;

@ExtendWith(MockitoExtension.class)
public class InterviewReportMapperTest {

    @InjectMocks
    private InterviewReportMapper interviewReportMapper;

    @Test
    public void testDtoToEntity() {
        InterviewReport testInterviewReport = generateTestInterviewReport();
        InterviewReportDTO testInterviewReportDTO = generateTestInterviewReportDTO();
        PlatformUser testUser = generateTestUser();

        InterviewReport interviewReport = interviewReportMapper.dtoToEntity(testInterviewReportDTO, testUser);

        Assertions.assertEquals(interviewReport.getId(), testInterviewReport.getId());
        Assertions.assertEquals(interviewReport.getDate(), testInterviewReport.getDate());
        Assertions.assertEquals(interviewReport.getEmail(), testInterviewReport.getEmail());
        Assertions.assertEquals(interviewReport.getCompany(), testInterviewReport.getCompany());
        Assertions.assertEquals(interviewReport.getProject(), testInterviewReport.getProject());
        Assertions.assertEquals(interviewReport.getQuestions(), testInterviewReport.getQuestions());
        Assertions.assertEquals(interviewReport.getImpression(), testInterviewReport.getImpression());
        Assertions.assertEquals(interviewReport.getMinSalary(), testInterviewReport.getMinSalary());
        Assertions.assertEquals(interviewReport.getMaxSalary(), testInterviewReport.getMaxSalary());
        Assertions.assertEquals(interviewReport.getOfferSalary(), testInterviewReport.getOfferSalary());
        Assertions.assertEquals(interviewReport.getResultSalary(), testInterviewReport.getResultSalary());
        Assertions.assertEquals(interviewReport.getLevel(), testInterviewReport.getLevel());
        Assertions.assertEquals(interviewReport.getUser(), testInterviewReport.getUser());
    }

    @Test
    public void testEntityToDto() {
        InterviewReport testInterviewReport = generateTestInterviewReport();
        InterviewReportDTO testInterviewReportDTO = generateTestInterviewReportDTO();

        InterviewReportDTO interviewReportDTO = interviewReportMapper.entityToDto(testInterviewReport);

        Assertions.assertEquals(interviewReportDTO.getId(), testInterviewReportDTO.getId());
        Assertions.assertEquals(interviewReportDTO.getDate(), testInterviewReportDTO.getDate());
        Assertions.assertEquals(interviewReportDTO.getEmail(), testInterviewReportDTO.getEmail());
        Assertions.assertEquals(interviewReportDTO.getCompany(), testInterviewReportDTO.getCompany());
        Assertions.assertEquals(interviewReportDTO.getProject(), testInterviewReportDTO.getProject());
        Assertions.assertEquals(interviewReportDTO.getQuestions(), testInterviewReportDTO.getQuestions());
        Assertions.assertEquals(interviewReportDTO.getImpression(), testInterviewReportDTO.getImpression());
        Assertions.assertEquals(interviewReportDTO.getMinSalary(), testInterviewReportDTO.getMinSalary());
        Assertions.assertEquals(interviewReportDTO.getMaxSalary(), testInterviewReportDTO.getMaxSalary());
        Assertions.assertEquals(interviewReportDTO.getOfferSalary(), testInterviewReportDTO.getOfferSalary());
        Assertions.assertEquals(interviewReportDTO.getResultSalary(), testInterviewReportDTO.getResultSalary());
        Assertions.assertEquals(interviewReportDTO.getLevel(), testInterviewReportDTO.getLevel());
        Assertions.assertEquals(interviewReportDTO.getUserId(), testInterviewReportDTO.getUserId());
    }
}
