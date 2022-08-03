package com.override.model.enums;

public enum StatusFile {

    MISSING("Missing"),
    UPLOADED("Uploaded");

    private final String name;

    StatusFile(String statusFile) {
        this.name = statusFile;
    }

    public String getName() {
        return name;
    }

    public static StatusFile fromString(String statusFile) {
        if (statusFile != null) {
            for (StatusFile st : StatusFile.values()) {
                if (statusFile.equalsIgnoreCase(st.name)) {
                    return st;
                }
            }
        }
        throw new IllegalArgumentException("No such file status");
    }
}
