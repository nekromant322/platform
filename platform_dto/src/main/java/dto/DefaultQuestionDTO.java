package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class DefaultQuestionDTO {
    private String chapter;
    private int section;
}
