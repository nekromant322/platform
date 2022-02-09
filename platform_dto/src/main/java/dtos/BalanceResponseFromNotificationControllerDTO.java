package dtos;

import lombok.Data;

@Data
public class BalanceResponseFromNotificationControllerDTO {

    private double balance;

    private String urlToReplenishBalance;
}
