package dto;

import enums.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDateTime;

@Data
@Builder
public class PaymentDTO {
    @Schema(description = "Номер рассчетного счета, куда делается платеж (номер карты получателя платежа)")
    private Long accountNumber;
    private String comment;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime date;
    private Double sum;
    private PaymentStatus status;
    private String paymentId;
}



