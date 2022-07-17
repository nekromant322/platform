package com.override.service;

import com.override.converter.NavbarElementConverter;
import com.override.model.domain.NavbarElement;
import com.override.properties.NavbarProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class NavbarServiceTest {

    @InjectMocks
    private NavbarService navbarService;

    @Mock
    private NavbarProperties navbarProperties;

    @Mock
    private NavbarElementConverter converter;

    @Mock
    private HttpServletRequest request;

    @Test
    public void testGetNavbarWhenRoleIsAdmin() {

        List<String> listForAdmin = new ArrayList<>();

        listForAdmin.add("Курсы /courses");
        listForAdmin.add("Баланс /balancePage");
        listForAdmin.add("Все студенты /allStudents");
        listForAdmin.add("Запросы на регистрацию /joinRequests");
        listForAdmin.add("Вопросы к ревью /questionsAdmin");
        listForAdmin.add("Статистика /statistics");
        listForAdmin.add("Ревью /reviewAdmin");
        listForAdmin.add("Собеседования /interviewReports");
        listForAdmin.add("Платежи /allPayments");
        listForAdmin.add("Настройки /userSettings");
        listForAdmin.add("Баги /allBugs");

        List<NavbarElement> listNavbarElementsForAdmin = new ArrayList<>();

        listNavbarElementsForAdmin.add(new NavbarElement("Курсы", "/courses"));
        listNavbarElementsForAdmin.add(new NavbarElement("Баланс", "/balancePage"));
        listNavbarElementsForAdmin.add(new NavbarElement("Все студенты", "/allStudents"));
        listNavbarElementsForAdmin.add(new NavbarElement("Запросы на регистрацию", "/joinRequests"));
        listNavbarElementsForAdmin.add(new NavbarElement("Вопросы к ревью", "/questionsAdmin"));
        listNavbarElementsForAdmin.add(new NavbarElement("Статистика", "/statistics"));
        listNavbarElementsForAdmin.add(new NavbarElement("Ревью", "/reviewAdmin"));
        listNavbarElementsForAdmin.add(new NavbarElement("Собеседования", "/interviewReports"));
        listNavbarElementsForAdmin.add(new NavbarElement("Платежи", "/allPayments"));
        listNavbarElementsForAdmin.add(new NavbarElement("Настройки", "/userSettings"));
        listNavbarElementsForAdmin.add(new NavbarElement("Баги", "/allBugs"));

        when(request.isUserInRole("ROLE_ADMIN")).thenReturn(true);

        when(navbarProperties.getAdmin()).thenReturn(listForAdmin);

        when(converter.convertListOfStringToListOfNavbarElement(listForAdmin)).thenReturn(listNavbarElementsForAdmin);

        List<NavbarElement> navbarElements = navbarService.getNavbar(request);

        assertEquals(listNavbarElementsForAdmin, navbarElements);
    }

    @Test
    public void testGetNavbarWhenRoleIsGraduate() {

        List<String> listForGraduate = new ArrayList<>();

        listForGraduate.add("Курсы /courses");
        listForGraduate.add("Написать отчет /report");
        listForGraduate.add("Вопросы к ревью /questions");
        listForGraduate.add("Мои ревью /userReviews");
        listForGraduate.add("Персональные данные /personalData");
        listForGraduate.add("Собеседования /interviewReports");
        listForGraduate.add("Настройки /userSettings");

        List<NavbarElement> listNavbarElementsForGraduate = new ArrayList<>();

        listNavbarElementsForGraduate.add(new NavbarElement("Курсы", "/courses"));
        listNavbarElementsForGraduate.add(new NavbarElement("Написать отчет", "/report"));
        listNavbarElementsForGraduate.add(new NavbarElement("Вопросы к ревью", "/questions"));
        listNavbarElementsForGraduate.add(new NavbarElement("Мои ревью", "/userReviews"));
        listNavbarElementsForGraduate.add(new NavbarElement("Персональные данные", "/personalData"));
        listNavbarElementsForGraduate.add(new NavbarElement("Собеседования", "/interviewReports"));
        listNavbarElementsForGraduate.add(new NavbarElement("Настройки", "/userSettings"));

        when(request.isUserInRole("ROLE_ADMIN")).thenReturn(false);
        when(request.isUserInRole("ROLE_GRADUATE")).thenReturn(true);

        when(navbarProperties.getGraduate()).thenReturn(listForGraduate);

        when(converter.convertListOfStringToListOfNavbarElement(listForGraduate)).thenReturn(listNavbarElementsForGraduate);

        List<NavbarElement> navbarElements = navbarService.getNavbar(request);

        assertEquals(listNavbarElementsForGraduate, navbarElements);
    }

    @Test
    public void testGetNavbarWhenRoleIsUser() {

        List<String> listForUser = new ArrayList<>();

        listForUser.add("Курсы /courses");
        listForUser.add("Собеседования /interviewReports");
        listForUser.add("Оплата /payments");
        listForUser.add("Персональные данные /personalData");
        listForUser.add("Настройки /userSettings");

        List<NavbarElement> listNavbarElementsForUser = new ArrayList<>();

        listNavbarElementsForUser.add(new NavbarElement("Курсы", "/courses"));
        listNavbarElementsForUser.add(new NavbarElement("Собеседования", "/interviewReports"));
        listNavbarElementsForUser.add(new NavbarElement("Оплата", "/payments"));
        listNavbarElementsForUser.add(new NavbarElement("Персональные данные", "/personalData"));
        listNavbarElementsForUser.add(new NavbarElement("Настройки", "/userSettings"));

        when(request.isUserInRole("ROLE_ADMIN")).thenReturn(false);
        when(request.isUserInRole("ROLE_GRADUATE")).thenReturn(false);

        when(navbarProperties.getUser()).thenReturn(listForUser);

        when(converter.convertListOfStringToListOfNavbarElement(listForUser)).thenReturn(listNavbarElementsForUser);

        List<NavbarElement> navbarElements = navbarService.getNavbar(request);

        assertEquals(listNavbarElementsForUser, navbarElements);
    }
}
