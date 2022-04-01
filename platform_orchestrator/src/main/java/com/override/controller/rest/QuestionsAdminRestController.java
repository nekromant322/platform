package com.override.controller.rest;

import com.override.service.QuestionService;
import dtos.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questionsAdmin")
public class QuestionsAdminRestController {

    @Autowired
    private QuestionService questionService;

    @Secured("ROLE_ADMIN")
    @PostMapping
    public void save(@RequestBody QuestionDTO questionDTO) {
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
