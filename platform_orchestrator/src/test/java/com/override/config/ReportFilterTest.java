package com.override.config;

import com.override.config.security.JwtProvider;
import com.override.model.*;
import com.override.model.enums.CoursePart;
import com.override.repository.StudentReportRepository;
import com.override.service.PlatformUserService;
import enums.StudyStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportFilterTest {

    @InjectMocks
    private ReportFilter reportFilter;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private PermitAllURLs permitAllUrls;

    @Mock
    private PlatformUserService platformUserService;

    @Mock
    private JwtFilter jwtFilter;

    @Mock
    private StudentReportRepository studentReportRepository;

    @Mock(extraInterfaces = {HttpServletRequest.class})
    private ServletRequest servletRequest;

    @Mock(extraInterfaces = {HttpServletResponse.class})
    private ServletResponse servletResponse;

    @Mock
    private FilterChain filterChain;

    @Test
    public void testDoFilterWhenPermitUrlsIsPresentInRequest() throws ServletException, IOException {

        String token = "token";
        String userLogin = "userLogin";

        PlatformUser user = new PlatformUser(1L, "User", "User", StudyStatus.ACTIVE, CoursePart.CORE,
                Collections.singletonList(new Authority(1L, "ROLE_USER")), new PersonalData(), new UserSettings());

        String[] urls = (new String[]{"/login", "/init", "/css/*", "/images/*", "/js/*", "css", "js", "bugs", "navbar"});

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        when(jwtFilter.getTokenFromRequest(httpRequest)).thenReturn(token);
        when(jwtProvider.validateToken(token)).thenReturn(true);
        when(jwtProvider.getLoginFromToken(token)).thenReturn(userLogin);
        when(platformUserService.findPlatformUserByLogin(userLogin)).thenReturn(user);
        when(permitAllUrls.getPermitAllUrls()).thenReturn(urls);
        when(httpRequest.getRequestURI()).thenReturn("/navbar");

        reportFilter.doFilter(servletRequest, servletResponse, filterChain);

        verify(filterChain, times(1)).doFilter(servletRequest, servletResponse);
    }

    @Test
    public void testDoFilterWhenRequestFromGraduate() throws ServletException, IOException {

        String token = "token";
        String userLogin = "userLogin";

        PlatformUser user = new PlatformUser(1L, "User", "User", StudyStatus.ACTIVE, CoursePart.CORE,
                Collections.singletonList(new Authority(2L, "ROLE_GRADUATE")), new PersonalData(), new UserSettings());

        String[] urls = (new String[]{"/login", "/init", "/css/*", "/images/*", "/js/*", "css", "js", "bugs", "navbar"});

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        when(jwtFilter.getTokenFromRequest(httpRequest)).thenReturn(token);
        when(jwtProvider.validateToken(token)).thenReturn(true);
        when(jwtProvider.getLoginFromToken(token)).thenReturn(userLogin);
        when(platformUserService.findPlatformUserByLogin(userLogin)).thenReturn(user);
        when(permitAllUrls.getPermitAllUrls()).thenReturn(urls);
        when(httpRequest.getRequestURI()).thenReturn("/personalData");

        reportFilter.doFilter(servletRequest, servletResponse, filterChain);

        verify(filterChain, times(1)).doFilter(servletRequest, servletResponse);
    }

    @Test
    public void testDoFilterWhenRequestFromAdmin() throws ServletException, IOException {

        String token = "token";
        String userLogin = "userLogin";

        Authority userAuthority = new Authority(1L, "ROLE_USER");
        Authority adminAuthority = new Authority(3L, "ROLE_ADMIN");
        List<Authority> authorityList = new ArrayList<>();
        authorityList.add(userAuthority);
        authorityList.add(adminAuthority);

        PlatformUser user = new PlatformUser(1L, "User", "User", StudyStatus.ACTIVE, CoursePart.CORE,
                authorityList, new PersonalData(), new UserSettings());

        String[] urls = (new String[]{"/login", "/init", "/css/*", "/images/*", "/js/*", "css", "js", "bugs", "navbar"});

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        when(jwtFilter.getTokenFromRequest(httpRequest)).thenReturn(token);
        when(jwtProvider.validateToken(token)).thenReturn(true);
        when(jwtProvider.getLoginFromToken(token)).thenReturn(userLogin);
        when(platformUserService.findPlatformUserByLogin(userLogin)).thenReturn(user);
        when(permitAllUrls.getPermitAllUrls()).thenReturn(urls);
        when(httpRequest.getRequestURI()).thenReturn("/personalData");

        reportFilter.doFilter(servletRequest, servletResponse, filterChain);

        verify(filterChain, times(1)).doFilter(servletRequest, servletResponse);
    }

    @Test
    public void testDoFilterWhenRequestFromUserForReport() throws ServletException, IOException {

        String token = "token";
        String userLogin = "userLogin";

        PlatformUser user = new PlatformUser(1L, "User", "User", StudyStatus.ACTIVE, CoursePart.CORE,
                Collections.singletonList(new Authority(3L, "ROLE_USER")), new PersonalData(), new UserSettings());

        String[] urls = (new String[]{"/login", "/init", "/css/*", "/images/*", "/js/*", "css", "js", "bugs", "navbar"});

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        when(jwtFilter.getTokenFromRequest(httpRequest)).thenReturn(token);
        when(jwtProvider.validateToken(token)).thenReturn(true);
        when(jwtProvider.getLoginFromToken(token)).thenReturn(userLogin);
        when(platformUserService.findPlatformUserByLogin(userLogin)).thenReturn(user);
        when(permitAllUrls.getPermitAllUrls()).thenReturn(urls);
        when(httpRequest.getRequestURI()).thenReturn("/report");

        reportFilter.doFilter(servletRequest, servletResponse, filterChain);

        verify(filterChain, times(1)).doFilter(servletRequest, servletResponse);
    }

    @Test
    public void testDoFilterWhenNewUser() throws ServletException, IOException {

        String token = "token";
        String userLogin = "userLogin";

        PlatformUser user = new PlatformUser(1L, "User", "User", StudyStatus.ACTIVE, CoursePart.CORE,
                Collections.singletonList(new Authority(3L, "ROLE_USER")), new PersonalData(), new UserSettings());

        String[] urls = (new String[]{"/login", "/init", "/css/*", "/images/*", "/js/*", "css", "js", "bugs", "navbar"});

        Optional<StudentReport> studentReport = Optional.ofNullable(null);

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        when(jwtFilter.getTokenFromRequest(httpRequest)).thenReturn(token);
        when(jwtProvider.validateToken(token)).thenReturn(true);
        when(jwtProvider.getLoginFromToken(token)).thenReturn(userLogin);
        when(platformUserService.findPlatformUserByLogin(userLogin)).thenReturn(user);
        when(permitAllUrls.getPermitAllUrls()).thenReturn(urls);
        when(httpRequest.getRequestURI()).thenReturn("/personalData");
        when(studentReportRepository.findFirstByStudentOrderByDateDesc(user)).thenReturn(studentReport);

        reportFilter.doFilter(servletRequest, servletResponse, filterChain);

        verify(filterChain, times(1)).doFilter(servletRequest, servletResponse);
    }

    @Test
    public void testDoFilterWhenRequestFromUserNotForReportAndReportPresent() throws ServletException, IOException {

        String token = "token";
        String userLogin = "userLogin";

        PlatformUser user = new PlatformUser(1L, "User", "User", StudyStatus.ACTIVE, CoursePart.CORE,
                Collections.singletonList(new Authority(3L, "ROLE_USER")), new PersonalData(), new UserSettings());

        String[] urls = (new String[]{"/login", "/init", "/css/*", "/images/*", "/js/*", "css", "js", "bugs", "navbar"});

        Optional<StudentReport> studentReport = Optional.of(new StudentReport(LocalDate.now().minusDays(1), "Отчет", 4.0));

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        when(jwtFilter.getTokenFromRequest(httpRequest)).thenReturn(token);
        when(jwtProvider.validateToken(token)).thenReturn(true);
        when(jwtProvider.getLoginFromToken(token)).thenReturn(userLogin);
        when(platformUserService.findPlatformUserByLogin(userLogin)).thenReturn(user);
        when(permitAllUrls.getPermitAllUrls()).thenReturn(urls);
        when(httpRequest.getRequestURI()).thenReturn("/personalData");
        when(studentReportRepository.findFirstByStudentOrderByDateDesc(user)).thenReturn(studentReport);

        reportFilter.doFilter(servletRequest, servletResponse, filterChain);

        verify(filterChain, times(1)).doFilter(servletRequest, servletResponse);
    }

    @Test
    public void testDoFilterWhenRequestFromUserNotForReportAndReportNotPresent() throws ServletException, IOException {

        String token = "token";
        String userLogin = "userLogin";

        PlatformUser user = new PlatformUser(1L, "User", "User", StudyStatus.ACTIVE, CoursePart.CORE,
                Collections.singletonList(new Authority(3L, "ROLE_USER")), new PersonalData(), new UserSettings());

        String[] urls = (new String[]{"/login", "/init", "/css/*", "/images/*", "/js/*", "css", "js", "bugs", "navbar"});

        Optional<StudentReport> studentReport = Optional.of(new StudentReport(LocalDate.now().minusDays(5), "Отчет", 4.0));

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        when(jwtFilter.getTokenFromRequest(httpRequest)).thenReturn(token);
        when(jwtProvider.validateToken(token)).thenReturn(true);
        when(jwtProvider.getLoginFromToken(token)).thenReturn(userLogin);
        when(platformUserService.findPlatformUserByLogin(userLogin)).thenReturn(user);
        when(permitAllUrls.getPermitAllUrls()).thenReturn(urls);
        when(httpRequest.getRequestURI()).thenReturn("/personalData");
        when(studentReportRepository.findFirstByStudentOrderByDateDesc(user)).thenReturn(studentReport);

        reportFilter.doFilter(servletRequest, servletResponse, filterChain);

        verify((HttpServletResponse) servletResponse, times(1)).sendRedirect("/report");
        verify(filterChain, times(0)).doFilter(servletRequest, servletResponse);
    }
}
