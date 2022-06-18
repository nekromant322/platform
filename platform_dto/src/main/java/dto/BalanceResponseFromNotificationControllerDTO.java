package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BalanceResponseFromNotificationControllerDTO {

    private double balance;

    private String urlToReplenishBalance;
}
