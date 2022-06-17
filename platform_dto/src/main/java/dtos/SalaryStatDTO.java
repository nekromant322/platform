package dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SalaryStatDTO {
    private String label;
    private List<Integer> data;
    private Integer borderWidth;
}
