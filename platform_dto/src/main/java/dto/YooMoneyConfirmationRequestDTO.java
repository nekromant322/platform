package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class YooMoneyConfirmationRequestDTO {

    private Amount amount;

    private Confirmation confirmation;

    @ApiModelProperty(value = "Автоматический прием поступившего платежа.")
    private Boolean capture;

    @ApiModelProperty(value = "Описание транзакции (не более 128 символов)")
    private String description;

    @ApiModelProperty(value = "Идентификатор покупателя в нашей системе (Login)")
    @JsonProperty("merchant_customer_id")
    private String merchantCustomerId;

    @Builder
    @Data
    public static class Amount {

        @ApiModelProperty(value = "Сумма платежа.")
        private double value;

        @ApiModelProperty(value = "Сумма платежа.")
        private String currency;
    }

    @Builder
    @Data
    public static class Confirmation {

        @ApiModelProperty(value = "Код сценария подтверждения.", example = "embedded")
        private String type;
    }
}
