package dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SalaryStatDTO {
    /**
     * this is label for salary graph which contains logins
     */
    @Schema(description = "Логин юзера, которй уже принял оффер и работает")
    private String label;
    /**
     * this is dataset for salary graph which contains salaries of users
     */
    @Schema(description = "Лист со списком зарплат данного юзера")
    private List<Integer> data;
    private Integer borderWidth;
}
