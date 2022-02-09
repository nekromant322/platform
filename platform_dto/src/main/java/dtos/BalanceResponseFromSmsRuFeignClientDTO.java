package dtos;

import lombok.Data;

@Data
public class BalanceResponseFromSmsRuFeignClientDTO {
    private String status;

    private String statusCode;

    private double balance;
}
