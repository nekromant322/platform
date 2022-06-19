package dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SalaryDTO {
    /**
     * labels which contains dates for salary graphic
     */
    private List<String> labels;
    private List<SalaryStatDTO> salaryStats;
}
