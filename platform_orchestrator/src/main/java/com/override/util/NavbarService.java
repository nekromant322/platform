package com.override.util;

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
        } else {
            return converter.convertListOfStringToListOfNavbarElement(navbarProperties.getUser());
        }
    }

}
