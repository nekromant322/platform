package com.override.util;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NavbarElementConverter {

    public List<NavbarElement> convertListOfStringToListOfNavbarElement(List<String> source) {
        List<NavbarElement> navbarElements = new ArrayList<>();
        for (String value : source) {
            navbarElements.add(new NavbarElement(value.split(" /")[0], value.substring(value.lastIndexOf("/"))));
        }
        return navbarElements;
    }
}
