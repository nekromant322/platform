package com.override.config;

import com.override.config.security.JwtProvider;
import com.override.model.PlatformUser;
import com.override.model.enums.CoursePart;
import com.override.service.PlatformUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;


@Component
public class LessonFilter extends GenericFilterBean {

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private PlatformUserService platformUserService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = getTokenFromRequest((HttpServletRequest) servletRequest);

        if (token != null && jwtProvider.validateToken(token)) {
            String userLogin = jwtProvider.getLoginFromToken(token);
            PlatformUser platformUser = platformUserService.findPlatformUserByLogin(userLogin);

            if ((((HttpServletRequest) servletRequest).getRequestURI().contains("/web") && platformUser.getCoursePart() != CoursePart.WEB)) {
                ((HttpServletResponse) servletResponse).sendError(404);
                return;
            }

            if (((HttpServletRequest) servletRequest).getRequestURI().contains("/lessons/spring") && platformUser.getCoursePart() != CoursePart.SPRING) {
                ((HttpServletResponse) servletResponse).sendError(404);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getTokenFromRequest(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Cookie token = Arrays.stream(cookies).filter(el -> el.getName().equals("token")).findFirst().orElse(null);
            if (token != null) {
                return token.getValue();
            }
        }
        return null;
    }
}
