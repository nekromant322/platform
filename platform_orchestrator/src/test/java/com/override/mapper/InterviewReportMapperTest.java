package com.override.mapper;

import com.override.model.InterviewReport;
import dto.InterviewReportDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.override.utils.TestFieldsUtil.generateTestInterviewReport;
import static com.override.utils.TestFieldsUtil.generateTestInterviewReportDTO;

@ExtendWith(MockitoExtension.class)
public class InterviewReportMapperTest {

    @InjectMocks
    private InterviewReportMapper interviewReportMapper;

    @Test
    public void testDtoToEntity() {
        InterviewReport testInterviewReport = generateTestInterviewReport();
        InterviewReportDTO testInterviewReportDTO = generateTestInterviewReportDTO();

        InterviewReport interviewReport = interviewReportMapper.dtoToEntity(testInterviewReportDTO);

        Assertions.assertEquals(interviewReport.getId(), testInterviewReport.getId());
        Assertions.assertEquals(interviewReport.getDate(), testInterviewReport.getDate());
        Assertions.assertEquals(interviewReport.getUserLogin(), testInterviewReport.getUserLogin());
        Assertions.assertEquals(interviewReport.getCompany(), testInterviewReport.getCompany());
        Assertions.assertEquals(interviewReport.getProject(), testInterviewReport.getProject());
        Assertions.assertEquals(interviewReport.getQuestions(), testInterviewReport.getQuestions());
        Assertions.assertEquals(interviewReport.getImpression(), testInterviewReport.getImpression());
        Assertions.assertEquals(interviewReport.getMinSalary(), testInterviewReport.getMinSalary());
        Assertions.assertEquals(interviewReport.getMaxSalary(), testInterviewReport.getMaxSalary());
        Assertions.assertEquals(interviewReport.getCurrency(), testInterviewReport.getCurrency());
        Assertions.assertEquals(interviewReport.getStatus(), testInterviewReport.getStatus());
        Assertions.assertEquals(interviewReport.getLevel(), testInterviewReport.getLevel());
    }

    @Test
    public void testEntityToDto() {
        InterviewReport testInterviewReport = generateTestInterviewReport();
        InterviewReportDTO testInterviewReportDTO = generateTestInterviewReportDTO();

        InterviewReportDTO interviewReportDTO = interviewReportMapper.entityToDto(testInterviewReport);

        Assertions.assertEquals(interviewReportDTO.getId(), testInterviewReportDTO.getId());
        Assertions.assertEquals(interviewReportDTO.getDate(), testInterviewReportDTO.getDate());
        Assertions.assertEquals(interviewReportDTO.getUserLogin(), testInterviewReportDTO.getUserLogin());
        Assertions.assertEquals(interviewReportDTO.getCompany(), testInterviewReportDTO.getCompany());
        Assertions.assertEquals(interviewReportDTO.getProject(), testInterviewReportDTO.getProject());
        Assertions.assertEquals(interviewReportDTO.getQuestions(), testInterviewReportDTO.getQuestions());
        Assertions.assertEquals(interviewReportDTO.getImpression(), testInterviewReportDTO.getImpression());
        Assertions.assertEquals(interviewReportDTO.getMinSalary(), testInterviewReportDTO.getMinSalary());
        Assertions.assertEquals(interviewReportDTO.getMaxSalary(), testInterviewReportDTO.getMaxSalary());
        Assertions.assertEquals(interviewReportDTO.getCurrency(), testInterviewReportDTO.getCurrency());
        Assertions.assertEquals(interviewReportDTO.getStatus(), testInterviewReportDTO.getStatus());
        Assertions.assertEquals(interviewReportDTO.getLevel(), testInterviewReportDTO.getLevel());
    }
}
