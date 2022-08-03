package dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class IncomeFromUsersDTO {
    private List<String> studentName;
    private List<Long> income;
}
