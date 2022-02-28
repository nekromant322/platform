package com.override.models.enums;

public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String name;

    Role(String roleName) {
        name = roleName;
    }

    public String getName() {
        return name;
    }
}