package dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PaymentDTO {
    @ApiModelProperty(value = "Номер рассчетного счета, куда делается платеж (номер карты получателя платежа)")
    private Long accountNumber;
    private String comment;
    private LocalDate date;
    private Long sum;
}



