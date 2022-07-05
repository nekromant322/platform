package dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PaymentDTO {
    private Long accountNumber;
    private String comment;
    private LocalDate date;
    private Long sum;
}



