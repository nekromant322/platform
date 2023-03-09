package dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BalanceResponseFromNotificationControllerDTO {
    @Schema(description = "Баланс на sms.ru в рублях (есть только у админа)")
    private double balance;
    @Schema(description = "Ссылка для пополнения баланса (https://sms.ru/pay.php)")
    private String urlToReplenishBalance;
}
