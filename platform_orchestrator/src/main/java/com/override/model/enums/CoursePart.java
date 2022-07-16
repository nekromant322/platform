package com.override.model.enums;

public enum CoursePart {
    CORE("Core"),
    WEB("Web"),
    SPRING("Spring");

    private final String name;

    CoursePart(String coursePartName){
        name = coursePartName;
    }

    public String getName() {
        return name;
    }
}
