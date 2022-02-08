package dtos;

import lombok.Data;

@Data
public class BalanceResponseDTO {
    private String status;

    private String statusCode;

    private String balance;
}
