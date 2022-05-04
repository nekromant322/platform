package dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class InterviewReportDTO {
    private Long id;
    private LocalDate date;
    private String email;
    private String company;
    private String project;
    private String questions;
    private Byte impression;
    private String minSalary;
    private String maxSalary;
    private String offerSalary;
    private String resultSalary;
    private String level;
    private Long userId;
}
