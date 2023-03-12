package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import enums.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class YooMoneyConfirmationResponseDTO {

    @Schema(description = "Идентификатор платежа в ЮKassa.")
    private String id;

    @Schema(description = "Статус платежа. Возможные значения: pending, waiting_for_capture, succeeded и canceled.")
    private PaymentStatus status;

    private Amount amount;

    @Schema(description = "Описание транзакции (не более 128 символов)")
    private String description;

    private Recipient recipient;

    @Schema(description = "Время создания заказа. Указывается по UTC и передается в формате ISO 8601.")
    @JsonProperty("created_at")
    private String createdAt;

    private Confirmation confirmation;

    @Schema(description = "Признак тестовой операции.")
    private Boolean test;

    @Schema(description = "Признак успешной оплаты.")
    private Boolean paid;

    @Schema(description = "Признак возможности провести возврат по API.")
    private Boolean refundable;

    @Schema(description = "Любые дополнительные данные, которые нужны вам для работы.")
    private Object metadata;

    @Schema(description = "Идентификатор покупателя в нашей системе (Login)")
    @JsonProperty("merchant_customer_id")
    private String merchantCustomerId;

    @Data
    public static class Amount {

        @Schema(description = "Сумма платежа.")
        private double value;

        @Schema(description = "Сумма платежа.")
        private String currency;
    }

    @Data
    public static class Recipient {

        @Schema(description = "Идентификатор магазина в ЮKassa.")
        @JsonProperty("account_id")
        private Integer accountId;

        @Schema(description = "Идентификатор субаккаунта." +
                " Используется для разделения потоков платежей в рамках одного аккаунта.")
        @JsonProperty("gateway_id")
        private Integer gatewayId;
    }

    @Data
    public static class Confirmation {

        @Schema(description = "Код сценария подтверждения.", example = "embedded")
        private String type;

        @Schema(description = "Токен для инициализации платежного виджета ЮKassa ")
        @JsonProperty("confirmation_token")
        private String confirmationToken;
    }
}
