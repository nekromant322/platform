package dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class GeneralIncomeDTO {
    @Schema(description = "Лист с датами по месяцам. Здесь каждая дата - первое число каждого месяца. " +
            "Самая первая дата - это первое число того месяца, в котором был первый платеж от студента")
    private List<LocalDate> dataMonth;
    @Schema(description = "Лист с доходами от студентов за каждый месяц")
    private List<Double> income;
}
