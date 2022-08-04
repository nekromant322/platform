package com.override.service;

import com.override.converter.NavbarElementConverter;
import com.override.model.domain.NavbarElement;
import com.override.model.enums.Role;
import com.override.properties.NavbarProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


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
        listForAdmin.add("PreProjectLinks /preProjectLinks");
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
        listNavbarElementsForAdmin.add(new NavbarElement("PreProjectLinks", "/preProjectLinks"));
        listNavbarElementsForAdmin.add(new NavbarElement("Собеседования", "/interviewReports"));
        listNavbarElementsForAdmin.add(new NavbarElement("Платежи", "/allPayments"));
        listNavbarElementsForAdmin.add(new NavbarElement("Настройки", "/userSettings"));
        listNavbarElementsForAdmin.add(new NavbarElement("Баги", "/allBugs"));

        when(request.isUserInRole(Role.ADMIN.getName())).thenReturn(true);

        when(navbarProperties.getAdmin()).thenReturn(listForAdmin);

        when(converter.convertListOfStringToListOfNavbarElement(listForAdmin)).thenReturn(listNavbarElementsForAdmin);

        List<NavbarElement> navbarElements = navbarService.getNavbar(request);

        assertEquals(listNavbarElementsForAdmin, navbarElements);
    }

    @Test
    public void testGetNavbarWhenRoleIsGraduate() {

        List<String> listForGraduate = new ArrayList<>();

        listForGraduate.add("Курсы /courses");
        listForGraduate.add("Собеседования /interviewReports");
        listForGraduate.add("Оплата /payments");
        listForGraduate.add("Личный кабинет /personalData");

        List<NavbarElement> listNavbarElementsForGraduate = new ArrayList<>();

        listNavbarElementsForGraduate.add(new NavbarElement("Курсы", "/courses"));
        listNavbarElementsForGraduate.add(new NavbarElement("Собеседования", "/interviewReports"));
        listNavbarElementsForGraduate.add(new NavbarElement("Мои ревью", "/userReviews"));
        listNavbarElementsForGraduate.add(new NavbarElement("Личный кабинет", "/personalData"));


        when(request.isUserInRole(Role.ADMIN.getName())).thenReturn(false);
        when(request.isUserInRole(Role.GRADUATE.getName())).thenReturn(true);

        when(navbarProperties.getGraduate()).thenReturn(listForGraduate);

        when(converter.convertListOfStringToListOfNavbarElement(listForGraduate)).thenReturn(listNavbarElementsForGraduate);

        List<NavbarElement> navbarElements = navbarService.getNavbar(request);

        assertEquals(listNavbarElementsForGraduate, navbarElements);
    }

    @Test
    public void testGetNavbarWhenRoleIsUser() {

        List<String> listForUser = new ArrayList<>();

        listForUser.add("Курсы /courses");
        listForUser.add("Написать отчет /report");
        listForUser.add("Вопросы к ревью /questions");
        listForUser.add("Мои ревью /userReviews");
        listForUser.add("Личный кабинет /personalData");
        listForUser.add("Собеседования /interviewReports");
        listForUser.add("Оплата /payments");


        List<NavbarElement> listNavbarElementsForUser = new ArrayList<>();

        listNavbarElementsForUser.add(new NavbarElement("Курсы", "/courses"));
        listNavbarElementsForUser.add(new NavbarElement("Написать отчет", "/report"));
        listNavbarElementsForUser.add(new NavbarElement("Вопросы к ревью", "/questions"));
        listNavbarElementsForUser.add(new NavbarElement("Мои ревью", "/userReviews"));
        listNavbarElementsForUser.add(new NavbarElement("Личный кабинет", "/personalData"));
        listNavbarElementsForUser.add(new NavbarElement("Собеседования", "/interviewReports"));

        when(request.isUserInRole(Role.ADMIN.getName())).thenReturn(false);
        when(request.isUserInRole(Role.GRADUATE.getName())).thenReturn(false);

        when(navbarProperties.getUser()).thenReturn(listForUser);

        when(converter.convertListOfStringToListOfNavbarElement(listForUser)).thenReturn(listNavbarElementsForUser);

        List<NavbarElement> navbarElements = navbarService.getNavbar(request);

        assertEquals(listNavbarElementsForUser, navbarElements);
    }
}
