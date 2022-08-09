package dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SalaryStatDTO {
    /**
     * this is label for salary graph which contains logins
     */
    @ApiModelProperty(value = "Логин юзера, которй уже принял оффер и работает")
    private String label;
    /**
     * this is dataset for salary graph which contains salaries of users
     */
    @ApiModelProperty(value = "Лист со списком зарплат данного юзера")
    private List<Integer> data;
    private Integer borderWidth;
}
