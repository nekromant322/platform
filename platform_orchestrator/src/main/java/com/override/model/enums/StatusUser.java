package com.override.model.enums;

public enum StatusUser{
    STUDY("Study"),
    BAN("Ban"),
    WORK("Work");

    private final String name;

    StatusUser(String status) {
        name = status;
    }

    public String getName() {
        return name;
    }
}
