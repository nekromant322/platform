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
    private Integer impression;
    private String minSalary;
    private String maxSalary;
    private String status;
    private String level;
    private Long userId;
}
