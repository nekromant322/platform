package dtos;

import lombok.Data;

@Data
public class SmsRuBalanceResponseDTO {
    private String status;

    private String statusCode;

    private double balance;
}
