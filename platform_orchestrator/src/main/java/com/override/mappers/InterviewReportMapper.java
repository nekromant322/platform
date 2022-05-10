package com.override.mappers;

import com.override.models.InterviewReport;
import com.override.models.enums.Status;
import dtos.InterviewReportDTO;
import org.apache.logging.log4j.util.Timer;
import org.springframework.stereotype.Component;

@Component
public class InterviewReportMapper {

    public InterviewReport dtoToEntity(InterviewReportDTO interviewReportDTO) {
        return InterviewReport.builder()
                .id(interviewReportDTO.getId())
                .date(interviewReportDTO.getDate())
                .userLogin(interviewReportDTO.getUserLogin())
                .company(interviewReportDTO.getCompany())
                .project(interviewReportDTO.getProject())
                .questions(interviewReportDTO.getQuestions())
                .impression(interviewReportDTO.getImpression())
                .minSalary(interviewReportDTO.getMinSalary())
                .maxSalary(interviewReportDTO.getMaxSalary())
                .currency(interviewReportDTO.getCurrency())
                .status(Status.fromString(interviewReportDTO.getStatus()))
                .level(interviewReportDTO.getLevel())
                .build();
    }

    public InterviewReportDTO entityToDto(InterviewReport interviewReport) {
        return InterviewReportDTO.builder()
                .id(interviewReport.getId())
                .date(interviewReport.getDate())
                .userLogin(interviewReport.getUserLogin())
                .company(interviewReport.getCompany())
                .project(interviewReport.getProject())
                .questions(interviewReport.getQuestions())
                .impression(interviewReport.getImpression())
                .minSalary(interviewReport.getMinSalary())
                .maxSalary(interviewReport.getMaxSalary())
                .currency(interviewReport.getCurrency())
                .status(interviewReport.getStatus().getName())
                .level(interviewReport.getLevel())
                .build();
    }
}
