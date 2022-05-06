package com.override.controller.rest;

import com.override.models.Question;
import com.override.service.CustomStudentDetailService;
import com.override.service.LessonStructureService;
import com.override.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionRestController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private LessonStructureService lessonStructureService;

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

    @GetMapping("/chapters")
    public List<String> findAllChapters() {
        return lessonStructureService.getChapterNamesList();
    }
}
