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

    @GetMapping("/all")
    public List<Question> findAll(@RequestBody QuestionDTO questionDTO) {
            return questionService.findAllByUserAndChapter(questionDTO.getLogin(), questionDTO.getChapter());
    }

    @GetMapping("/chapters")
    public List<String> findAllChapters(){
        return lessonStructureService.getChapterNamesList();
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public void save(@RequestBody QuestionDTO questionDTO){
        questionService.save(questionDTO);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/update")
    public void patch(@RequestBody QuestionDTO questionDTO){
        questionService.patch(questionDTO);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete")
    public void delete(@RequestBody QuestionDTO questionDTO){
            questionService.delete(questionDTO);
    }
}
