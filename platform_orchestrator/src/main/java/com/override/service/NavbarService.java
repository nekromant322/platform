package com.override.service;

import com.override.model.domain.NavbarElement;
import com.override.converter.NavbarElementConverter;
import com.override.properties.NavbarProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class NavbarService {

    @Autowired
    private NavbarProperties navbarProperties;
    @Autowired
    private NavbarElementConverter converter;

    public List<NavbarElement> getNavbar(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return converter.convertListOfStringToListOfNavbarElement(navbarProperties.getAdmin());
        }
        if (request.isUserInRole("ROLE_GRADUATE")) {
            return converter.convertListOfStringToListOfNavbarElement(navbarProperties.getGraduate());
        }
            return converter.convertListOfStringToListOfNavbarElement(navbarProperties.getUser());
    }
}
