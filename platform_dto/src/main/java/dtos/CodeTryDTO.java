package dtos;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CodeTryDTO {

    private TaskIdentifierDTO taskIdentifier;
    private String studentsCode;
}
