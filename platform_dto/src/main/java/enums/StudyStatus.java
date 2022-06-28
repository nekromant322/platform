package enums;

public enum StudyStatus {
    ACTIVE("Active"),
    BAN("Ban");

    private final String name;

    StudyStatus(String status) {
        name = status;
    }

    public String getName() {
        return name;
    }
}
