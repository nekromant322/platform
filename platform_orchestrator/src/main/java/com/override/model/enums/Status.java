package com.override.model.enums;

public enum Status {
    PASSED("Passed"),
    OFFER("Offer"),
    ACCEPTED("Accepted");

    private final String name;

    Status(String status) {
        name = status;
    }

    public String getName() {
        return name;
    }

    public static Status fromString(String status) {
        if (status != null) {
            for (Status st : Status.values()) {
                if (status.equalsIgnoreCase(st.name)) {
                    return st;
                }
            }
        }
        throw new IllegalArgumentException("No such status");
    }
}
