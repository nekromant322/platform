package enums;

public enum PaymentStatus {
    SUCCEEDED("succeeded"),
    PENDING("pending"),
    CANCELED("canceled");

    private final String name;

    PaymentStatus(String status) {
        name = status;
    }

    public String getName() {
        return name;
    }
}
