package dtos;

import lombok.Data;

@Data
public class CodeCallResponseDTO {
    private String status;

    private String code;

    private String callID;

    private String cost;

    private String balance;
}
