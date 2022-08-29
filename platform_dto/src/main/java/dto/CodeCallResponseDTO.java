package dto;

import lombok.Data;

@Data
public class CodeCallResponseDTO {
    private String status;

    private String code;

    private String callID;

    private double cost;

    private double balance;
}
