package com.override.mappers;

import com.override.models.InterviewReport;
import com.override.models.PlatformUser;
import dtos.InterviewReportDTO;
import org.springframework.stereotype.Component;

@Component
public class InterviewReportMapper {

    public InterviewReport dtoToEntity(InterviewReportDTO interviewReportDTO,
                                       PlatformUser user) {
        return InterviewReport.builder()
                .id(interviewReportDTO.getId())
                .date(interviewReportDTO.getDate())
                .email(interviewReportDTO.getEmail())
                .company(interviewReportDTO.getCompany())
                .project(interviewReportDTO.getProject())
                .questions(interviewReportDTO.getQuestions())
                .impression(interviewReportDTO.getImpression())
                .minSalary(interviewReportDTO.getMinSalary())
                .maxSalary(interviewReportDTO.getMaxSalary())
                .status(interviewReportDTO.getStatus())
                .level(interviewReportDTO.getLevel())
                .user(user)
                .build();
    }

    public InterviewReportDTO entityToDto(InterviewReport interviewReport) {
        return InterviewReportDTO.builder()
                .id(interviewReport.getId())
                .date(interviewReport.getDate())
                .email(interviewReport.getEmail())
                .company(interviewReport.getCompany())
                .project(interviewReport.getProject())
                .questions(interviewReport.getQuestions())
                .impression(interviewReport.getImpression())
                .minSalary(interviewReport.getMinSalary())
                .maxSalary(interviewReport.getMaxSalary())
                .status(interviewReport.getStatus())
                .level(interviewReport.getLevel())
                .userId(interviewReport.getUser().getId())
                .build();
    }
}
