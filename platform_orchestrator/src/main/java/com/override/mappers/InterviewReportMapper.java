package com.override.mappers;

import com.override.models.InterviewReport;
import dtos.InterviewReportDTO;
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
                .status(interviewReportDTO.getStatus())
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
                .status(interviewReport.getStatus())
                .level(interviewReport.getLevel())
                .build();
    }
}
