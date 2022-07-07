package com.override.model.enums;

public enum Role {
    USER("ROLE_USER"),
    GRADUATE("ROLE_GRADUATE"),
    ADMIN("ROLE_ADMIN");

    private final String name;

    Role(String roleName) {
        name = roleName;
    }

    public String getName() {
        return name;
    }
}