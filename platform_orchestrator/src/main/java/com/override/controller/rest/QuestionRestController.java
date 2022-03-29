package com.override.controller.rest;

import com.override.models.PlatformUser;
import com.override.models.Question;
import com.override.service.LessonStructureService;
import com.override.service.PlatformUserService;
import com.override.service.QuestionService;
import dtos.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionRestController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    LessonStructureService lessonStructureService;

    @Autowired
    PlatformUserService platformUserService;

    @GetMapping("/users/{login}/{chapter}")
    public List<Question> findAll(@PathVariable String login,
                                  @PathVariable String chapter) {
        return questionService.findAllByUserAndChapter(login, chapter);
    }

    @GetMapping("/get/current")
    public PlatformUser getUserInfo() {
        return platformUserService.findPlatformUserByLogin(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
    }

    @GetMapping("/chapters")
    public List<String> findAllChapters() {
        return lessonStructureService.getChapterNamesList();
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public void save(@RequestBody QuestionDTO questionDTO){
        questionService.save(questionDTO);
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping
    public void patch(@RequestBody QuestionDTO questionDTO) {
        questionService.patch(questionDTO);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping
    public void delete(@RequestParam long id) {
        questionService.delete(id);
    }
}
