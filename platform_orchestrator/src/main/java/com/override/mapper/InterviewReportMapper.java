package com.override.mapper;

import com.override.model.InterviewReport;
import com.override.model.enums.Status;
import dto.InterviewReportDTO;
import dto.SalaryDTO;
import dto.SalaryStatDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    public SalaryStatDTO LoginAndSalariesToDto(String userLogin, List<Integer> salaries) {
        return SalaryStatDTO.builder()
                .label(userLogin)
                .data(salaries)
                .borderWidth(1)
                .build();
    }

    public SalaryDTO salaryStatDtoToSalaryDto(List<LocalDate> localDate, List<SalaryStatDTO> salaryStatDTO) {
        return SalaryDTO.builder()
                .labels(localDate.stream().map(LocalDate::toString).collect(Collectors.toList()))
                .salaryStats(salaryStatDTO)
                .build();
    }
}
