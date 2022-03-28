package dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
public class CodeTryDTO {

    private TaskIdentifierDTO taskIdentifier;
    @Size(max = 8096)
    private String studentsCode;
}
