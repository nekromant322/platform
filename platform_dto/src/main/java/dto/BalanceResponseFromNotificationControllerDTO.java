package dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BalanceResponseFromNotificationControllerDTO {

    @ApiModelProperty(value = "Баланс в рублях (есть только у админа)")
    private double balance;
    @ApiModelProperty(value = "Ссылка для пополнения баланса (https://sms.ru/pay.php)")
    private String urlToReplenishBalance;
}
