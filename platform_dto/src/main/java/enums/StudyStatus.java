package enums;

public enum StudyStatus {
    STUDY("Study"),
    BAN("Ban"),
    WORK("Work");

    private final String name;

    StudyStatus(String status) {
        name = status;
    }

    public String getName() {
        return name;
    }
}
