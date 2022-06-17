package dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SalaryDTO {
    private List<String> labels;
    private List<SalaryStatDTO> userStats;
}
