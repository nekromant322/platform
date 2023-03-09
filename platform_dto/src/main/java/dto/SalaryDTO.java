package dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SalaryDTO {
    /**
     * labels which contains dates for salary graphic
     */
    @Schema(description = "Лист с датами зарплат, даты для каждого юзера начинаются с принятия оффера по сегодняшний день")
    private List<String> labels;
    @Schema(description = "SalaryStatDTO, в этой ДТОшке логин юзера и список зарплат данного юзера для каждой даты из листа 'labels'")
    private List<SalaryStatDTO> salaryStats;
}
