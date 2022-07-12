package com.override.model.enums;

public enum CurrentCoursePart {
    CORE("ROLE_CORE"),
    WEB("ROLE_WEB"),
    SPRING("ROLE_SPRING");

    private final String name;

    CurrentCoursePart(String coursePartName){
        name = coursePartName;
    }

    public String getName() {
        return name;
    }
}
