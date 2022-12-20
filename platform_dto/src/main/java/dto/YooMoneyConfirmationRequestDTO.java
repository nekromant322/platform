package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class YooMoneyConfirmationRequestDTO {

    @Builder
    @Data
    public static class Amount {

        private double value;

        private String currency;
    }

    @Builder
    @Data
    public static class Confirmation {

        private String type;
    }

    private Amount amount;

    private Confirmation confirmation;

    private Boolean capture;

    private String description;

    private String merchant_customer_id;
}
