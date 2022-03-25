package dtos;

import enums.CodeExecutionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class TestResultDTO {
    private CodeExecutionStatus codeExecutionStatus;
    private String output;
}
