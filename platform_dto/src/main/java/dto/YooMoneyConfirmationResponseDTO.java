package dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class YooMoneyConfirmationResponseDTO {

    @ApiModelProperty(value = "Идентификатор платежа в ЮKassa.")
    private String id;

    @ApiModelProperty(value = "Статус платежа. Возможные значения: pending, waiting_for_capture, succeeded и canceled.")
    private String status;

    private Amount amount;

    @ApiModelProperty(value = "Описание транзакции (не более 128 символов)")
    private String description;

    private Recipient recipient;

    @ApiModelProperty(value = "Время создания заказа. Указывается по UTC и передается в формате ISO 8601.")
    private String created_at;

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
    private String merchant_customer_id;

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
        private Integer account_id;

        @ApiModelProperty(value = "Идентификатор субаккаунта." +
                " Используется для разделения потоков платежей в рамках одного аккаунта.")
        private Integer gateway_id;
    }

    @Data
    public static class Confirmation {

        @ApiModelProperty(value = "Код сценария подтверждения.", example = "embedded")
        private String type;

        @ApiModelProperty(value = "Токен для инициализации платежного виджета ЮKassa ")
        private String confirmation_token;
    }
}
