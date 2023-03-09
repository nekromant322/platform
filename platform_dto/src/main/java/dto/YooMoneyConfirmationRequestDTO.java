package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class YooMoneyConfirmationRequestDTO {

    private Amount amount;

    private Confirmation confirmation;

    @Schema(description = "Автоматический прием поступившего платежа.")
    private Boolean capture;

    @Schema(description = "Описание транзакции (не более 128 символов)")
    private String description;

    @Schema(description = "Идентификатор покупателя в нашей системе (Login)")
    @JsonProperty("merchant_customer_id")
    private String merchantCustomerId;

    @Builder
    @Data
    public static class Amount {

        @Schema(description = "Сумма платежа.")
        private double value;

        @Schema(description = "Сумма платежа.")
        private String currency;
    }

    @Builder
    @Data
    public static class Confirmation {

        @Schema(description = "Код сценария подтверждения.", example = "embedded")
        private String type;
    }
}
