package enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PaymentStatus {
    @JsonProperty("succeeded")
    SUCCEEDED,
    @JsonProperty("pending")
    PENDING,
    @JsonProperty("canceled")
    CANCELED
}
