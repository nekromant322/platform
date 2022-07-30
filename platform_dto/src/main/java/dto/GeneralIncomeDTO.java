package dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class GeneralIncomeDTO {
    private List<LocalDate> dataMonth;
    private List<Long> income;
}
