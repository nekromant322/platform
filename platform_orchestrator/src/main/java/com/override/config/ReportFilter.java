package com.override.config;

import com.override.config.security.JwtProvider;
import com.override.model.PlatformUser;
import com.override.repository.StudentReportRepository;
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
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
public class ReportFilter extends GenericFilterBean {

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private PermitAllURLs permitAllUrls;
    @Autowired
    private PlatformUserService platformUserService;
    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private StudentReportRepository studentReportRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String token = jwtFilter.getTokenFromRequest(httpRequest);

        if (token != null && jwtProvider.validateToken(token)) {
            String userLogin = jwtProvider.getLoginFromToken(token);
            PlatformUser platformUser = platformUserService.findPlatformUserByLogin(userLogin);

            for (String url : permitAllUrls.getPermitAllUrls()) {
                if (httpRequest.getRequestURI().contains(url)) {
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }
            if (platformUser.getAuthorities().stream().noneMatch(role -> role.getAuthority().equals("ROLE_ADMIN")) &&
                    platformUser.getAuthorities().stream().noneMatch(role -> role.getAuthority().equals("ROLE_GRADUATE")) &&
                    !(httpRequest.getRequestURI().contains("/report")) &&
                    DAYS.between(studentReportRepository.findFirstByStudentOrderByDateDesc(platformUser).getDate(), LocalDate.now()) > 3){
                ((HttpServletResponse) servletResponse).sendRedirect("/report");
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}