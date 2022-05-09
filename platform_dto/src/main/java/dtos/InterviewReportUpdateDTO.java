package dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InterviewReportUpdateDTO {
    private Long id;
    private Integer salary;
}
