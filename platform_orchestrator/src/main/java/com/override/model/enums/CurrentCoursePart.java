package com.override.model.enums;

public enum CurrentCoursePart {
    CORE("CORE"),
    WEB("WEB"),
    SPRING("SPRING");

    private final String name;

    CurrentCoursePart(String coursePartName){
        name = coursePartName;
    }

    public String getName() {
        return name;
    }
}
