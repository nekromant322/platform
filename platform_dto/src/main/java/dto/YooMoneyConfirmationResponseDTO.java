package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class YooMoneyConfirmationResponseDTO {

    @Data
    public static class Amount {

        private double value;

        private String currency;
    }

    @Data
    public static class Recipient {

        private Integer account_id;

        private Integer gateway_id;
    }

    @Data
    public static class Confirmation {

        private String type;

        private String confirmation_token;
    }

    private String id;

    private String status;

    private Amount amount;

    private String description;

    private Recipient recipient;

    private String created_at;

    private Confirmation confirmation;

    private Boolean test;

    private Boolean paid;

    private Boolean refundable;

    private Object metadata;

    private String merchant_customer_id;
}
