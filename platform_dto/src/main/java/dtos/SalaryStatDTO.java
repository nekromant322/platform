package dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SalaryStatDTO {
    /**
     * this is label for salary graph which contains logins
     */
    private String label;
    /**
     * this is dataset for salary graph which contains salaries of users
     */
    private List<Integer> data;
    private Integer borderWidth;
}
