package dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
public class PaymentDTO {
    @ApiModelProperty(value = "Номер рассчетного счета, куда делается платеж (номер карты получателя платежа)")
    private Long accountNumber;
    private String comment;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDate date;
    private Double sum;
    private String status;
    private String paymentId;
}



