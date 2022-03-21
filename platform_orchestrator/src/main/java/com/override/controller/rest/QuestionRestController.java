package com.override.controller.rest;

import com.override.models.Question;
import com.override.service.LessonStructureService;
import com.override.service.QuestionService;
import dtos.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionRestController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    LessonStructureService lessonStructureService;

    @GetMapping("/users")
    public List<Question> findAll(@RequestBody QuestionDTO questionDTO) {
            return questionService.findAllByUserAndChapter(questionDTO.getLogin(), questionDTO.getChapter());
    }

    @GetMapping("/chapters")
    public List<String> findAllChapters(){
        return lessonStructureService.getChapterNamesList();
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public void save(@RequestBody QuestionDTO questionDTO){
        questionService.save(questionDTO);
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping
    public void patch(@RequestBody QuestionDTO questionDTO){
        questionService.patch(questionDTO);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping
    public void delete(@RequestParam long id){
            questionService.delete(id);
    }
}
