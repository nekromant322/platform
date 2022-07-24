package com.override.model.enums;


public enum CoursePart {
    CORE("Core"),
    WEB("Web"),
    PREPROJECT("PreProject"),
    PROJECT("Project"),
    INTERVIEW("Interview");

    private final String name;

    CoursePart(String coursePartName) {
        name = coursePartName;
    }

    public String getName() {
        return name;
    }
}
