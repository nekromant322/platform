package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import enums.PaymentStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class YooMoneyConfirmationResponseDTO {

    @ApiModelProperty(value = "Идентификатор платежа в ЮKassa.")
    private String id;

    @ApiModelProperty(value = "Статус платежа. Возможные значения: pending, waiting_for_capture, succeeded и canceled.")
    private PaymentStatus status;

    private Amount amount;

    @ApiModelProperty(value = "Описание транзакции (не более 128 символов)")
    private String description;

    private Recipient recipient;

    @ApiModelProperty(value = "Время создания заказа. Указывается по UTC и передается в формате ISO 8601.")
    @JsonProperty("created_at")
    private String createdAt;

    private Confirmation confirmation;

    @ApiModelProperty(value = "Признак тестовой операции.")
    private Boolean test;

    @ApiModelProperty(value = "Признак успешной оплаты.")
    private Boolean paid;

    @ApiModelProperty("Признак возможности провести возврат по API.")
    private Boolean refundable;

    @ApiModelProperty(value = "Любые дополнительные данные, которые нужны вам для работы.")
    private Object metadata;

    @ApiModelProperty(value = "Идентификатор покупателя в нашей системе (Login)")
    @JsonProperty("merchant_customer_id")
    private String merchantCustomerId;

    @Data
    public static class Amount {

        @ApiModelProperty(value = "Сумма платежа.")
        private double value;

        @ApiModelProperty(value = "Сумма платежа.")
        private String currency;
    }

    @Data
    public static class Recipient {

        @ApiModelProperty(value = "Идентификатор магазина в ЮKassa.")
        @JsonProperty("account_id")
        private Integer accountId;

        @ApiModelProperty(value = "Идентификатор субаккаунта." +
                " Используется для разделения потоков платежей в рамках одного аккаунта.")
        @JsonProperty("gateway_id")
        private Integer gatewayId;
    }

    @Data
    public static class Confirmation {

        @ApiModelProperty(value = "Код сценария подтверждения.", example = "embedded")
        private String type;

        @ApiModelProperty(value = "Токен для инициализации платежного виджета ЮKassa ")
        @JsonProperty("confirmation_token")
        private String confirmationToken;
    }
}
