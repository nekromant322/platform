package com.override.controller.rest;

import com.override.models.Question;
import com.override.service.QuestionService;
import dtos.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionRestController {
    @Autowired
    QuestionService questionService;

    @GetMapping("/all")
    public List<Question> findAll(@RequestBody QuestionDTO questionDTO) {
            return questionService.findAllByUserAndChapter(questionDTO.getLogin(), questionDTO.getChapter());
    }

    @PostMapping
    public void save(@RequestBody QuestionDTO questionDTO){
        questionService.save(questionDTO);
    }

    @PostMapping("/update")
    public void patch(@RequestBody QuestionDTO questionDTO){
        questionService.patch(questionDTO);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody QuestionDTO questionDTO){
        questionService.delete(questionDTO);
    }
}
