package com.override.controller.rest;

import com.override.models.PlatformUser;
import com.override.models.Question;
import com.override.service.CustomStudentDetailService;
import com.override.service.LessonStructureService;
import com.override.service.PlatformUserService;
import com.override.service.QuestionService;
import dtos.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionRestController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private LessonStructureService lessonStructureService;

    @Autowired
    private PlatformUserService platformUserService;

    @GetMapping("/{login}/{chapter}")
    public List<Question> findAll(@PathVariable String login,
                                  @PathVariable String chapter) {
        return questionService.findAllByUserAndChapter(login, chapter);
    }

    @GetMapping("/my/{chapter}")
    public List<Question> findAllPersonal(@AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user,
                                          @PathVariable String chapter) {
        return questionService.findAllByUserAndChapter(user.getUsername(), chapter);
    }

    @GetMapping("/current")
    public PlatformUser getUserInfo(@AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) {
        return platformUserService.findPlatformUserByLogin(user.getUsername());
    }

    @GetMapping("/chapters")
    public List<String> findAllChapters() {
        return lessonStructureService.getChapterNamesList();
    }
}
