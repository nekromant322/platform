package com.override.controller;

import com.github.benmanes.caffeine.cache.Cache;
import dto.HelpMeDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {

    @Autowired
    private Cache<Integer, HelpMeDTO> helpMeCache;

    @GetMapping("/")
    @ApiOperation(value = "Возвращает index.html")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/courses")
    @ApiOperation(value = "Возвращает courses.html")
    public String coursesPage() {
        return "courses";
    }

    @GetMapping("/codeTryList")
    @ApiOperation(value = "Возвращает codeTryList.html")
    public String codeTryListPage() {
        return "codeTryList";
    }

    @GetMapping("/helpMe/{key}")
    @ApiOperation(value = "Возвращает emptyHelpMe.html, либо helpMe.html")
    public String getHelpView(@PathVariable int key) {
        if (helpMeCache.getIfPresent(key) == null) {
            return "emptyHelpMe";
        } else {
            return "helpMe";
        }
    }

    @GetMapping("/questions")
    @ApiOperation(value = "Возвращает questions.html")
    public String questionsPage() {
        return "questions";
    }

    @GetMapping("/defQuestions")
    @ApiOperation(value = "Возвращает defaultQuestions.html")
    public String defaultQuestionsPage() {
        return "defaultQuestions";
    }

    @GetMapping("/personalData")
    @ApiOperation(value = "Возвращает personalData.html")
    public String personalDataPage() {
        return "personalData";
    }

    @GetMapping("/report")
    @ApiOperation(value = "Возвращает report.html")
    public String reportPage() {
        return "report";
    }

    @GetMapping("/userSettings")
    @ApiOperation(value = "Возвращает userSettings.html")
    public String userSettingsPage() {
        return "userSettings";
    }

    @GetMapping("/reviewRequest")
    @ApiOperation(value = "Возвращает fragments/reviewRequestButton.html")
    public String reviewRequestForm() {
        return "fragments/reviewRequestButton";
    }

    @GetMapping("/interviewReports")
    @ApiOperation(value = "Возвращает interviewReport.html")
    public String interviewReportPage() {
        return "interviewReport";
    }

    @GetMapping("/payments")
    @ApiOperation(value = "Возвращает payment.html")
    public String payPage() {
        return "payment";
    }

    @GetMapping("/preProjectLinks")
    @ApiOperation(value = "Возвращает allPreProjectLinks.html")
    public String allPreProjectLinksPage() {
        return "allPreProjectLinks";
    }

    @GetMapping("/allPayments")
    @ApiOperation(value = "Возвращает allPayments.html")
    public String allPaymentsPage() {
        return "allPayments";
    }

    @GetMapping("/userReviews")
    @ApiOperation(value = "Возвращает userReviews.html")
    public String showUsersReviews() {
        return "userReviews";
    }

    @GetMapping("/login")
    @ApiOperation(value = "Возвращает login.html")
    public String authPage() {
        return "login";
    }
}
