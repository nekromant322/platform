package dtos;

import enums.CodeExecutionStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestResultDTO {
    private CodeExecutionStatus codeExecutionStatus;
    private String output;
}
