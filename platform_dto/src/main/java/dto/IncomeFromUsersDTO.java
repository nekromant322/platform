package dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class IncomeFromUsersDTO {
    @Schema(description = "Лист с именами всех студентов (логинами), имена не могу повторятся")
    private List<String> studentName;
    @Schema(description = "Лист суммарных доходов от каждого студента")
    private List<Long> income;
}
