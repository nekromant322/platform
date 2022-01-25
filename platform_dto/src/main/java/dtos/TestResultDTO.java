package dtos;

import enums.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestResultDTO {
    private Status status;
    private String output;
}
