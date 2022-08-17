package com.override.config;

import com.override.config.security.JwtProvider;
import com.override.model.PlatformUser;
import com.override.model.enums.CoursePart;
import com.override.service.PlatformUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LessonFilter extends GenericFilterBean {

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private PlatformUserService platformUserService;
    @Autowired
    private JwtFilter jwtFilter;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtFilter.getTokenFromRequest((HttpServletRequest) servletRequest);

        if (token != null && jwtProvider.validateToken(token)) {
            String userLogin = jwtProvider.getLoginFromToken(token);
            PlatformUser platformUser = platformUserService.findPlatformUserByLogin(userLogin);

            if ((((HttpServletRequest) servletRequest).getRequestURI().contains("/web") && platformUser.getCoursePart().ordinal() < CoursePart.WEB.ordinal())) {
                ((HttpServletResponse) servletResponse).sendError(404);
                return;
            }

            if (((HttpServletRequest) servletRequest).getRequestURI().contains("/lessons/spring") && platformUser.getCoursePart().ordinal() < CoursePart.PREPROJECT.ordinal()) {
                ((HttpServletResponse) servletResponse).sendError(404);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
