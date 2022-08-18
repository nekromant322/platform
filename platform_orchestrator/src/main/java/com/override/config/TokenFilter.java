package com.override.config;

import com.override.service.CustomStudentDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class TokenFilter extends GenericFilterBean {

    @Autowired
    private PermitAllURLs permitAllUrls;

    @Autowired
    private CustomStudentDetailService studentDetailService;

    @Value("${token.X-MS-AUTH}")
    private String token;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String requestToken = httpRequest.getHeader("X-MS-AUTH");

        for (String url : permitAllUrls.getPermitAllUrls()) {
            if (httpRequest.getRequestURI().contains(url) || httpRequest.getRequestURI().equals("/")) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        if (requestToken != null) {
            if (requestToken.equals(token)) {

                log.info("Авторизован запрос от другого микросервиса");

                UserDetails customUserDetails = studentDetailService.loadUserByUsername("microservice");
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                log.error("WRONG X-MS-AUTH TOKEN");
                ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
