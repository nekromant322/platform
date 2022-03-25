package dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskIdentifierDTO {

    private Integer chapter;
    private Integer step;
    private Integer lesson;
}
