package dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class IncomeFromUsersDTO {
    @ApiModelProperty(value = "Лист с именами всех студентов (логинами), имена не могу повторятся")
    private List<String> studentName;
    @ApiModelProperty(value = "Лист суммарных доходов от каждого студента")
    private List<Long> income;
}
