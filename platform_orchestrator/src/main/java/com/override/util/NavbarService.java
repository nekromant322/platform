package com.override.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NavbarService {

    private static final String OPEN_LI = "<li class=\"nav-item\">\n<a class=\"nav-link\" href=\"";
    private static final String CLOSE_A = "\">";
    private static final String CLOSE_LI = "</a>\n</li>";
    private static final String LOGOUT = "<li class=\"nav-item\">\n<button class=\"button__logout\" onclick=\"logout()\">Выйти</button>\n</li>";

    @Autowired
    NavbarProperties navbarProperties;

    public String getAdminNavbar() {
        StringBuilder html = new StringBuilder();
        List<String> urls = navbarProperties.getAdmin().stream().map(url -> url.substring(url.lastIndexOf("/") - 1)).collect(Collectors.toList());
        List<String> values = navbarProperties.getAdmin().stream().map(value -> value.split(" /")[0]).collect(Collectors.toList());
        for (int i = 0; i < navbarProperties.getAdmin().size(); i++) {
            html
                    .append(OPEN_LI)
                    .append(urls.get(i))
                    .append(CLOSE_A)
                    .append(values.get(i))
                    .append(CLOSE_LI);
        }
        html.append(LOGOUT);
        return String.valueOf(html);
    }

    public String getUserNavbar() {
        StringBuilder html = new StringBuilder();
        List<String> urls = navbarProperties.getUser().stream().map(url -> url.substring(url.lastIndexOf("/") - 1)).collect(Collectors.toList());
        List<String> values = navbarProperties.getUser().stream().map(value -> value.split("/")[0]).collect(Collectors.toList());
        for (int i = 0; i < navbarProperties.getUser().size(); i++) {
            html
                    .append(OPEN_LI)
                    .append(urls.get(i))
                    .append(CLOSE_A)
                    .append(values.get(i))
                    .append(CLOSE_LI);
        }
        html.append(LOGOUT);
        return String.valueOf(html);
    }

    public String getNavbar(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return getAdminNavbar();
        } else {
            return getUserNavbar();
        }
    }

}
