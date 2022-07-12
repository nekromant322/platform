package com.override.controller.rest;

import com.override.model.DefaultQuestion;
import com.override.service.DefaultQuestionService;
import dto.DefaultQuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/defaultQuestions")
public class DefaultQuestionRestController {
    @Autowired
    private DefaultQuestionService defaultQuestionService;

    @GetMapping
    public List<DefaultQuestion> findDefaultQuestions() {
        return defaultQuestionService.findAll();
    }

    @PostMapping
    public List<DefaultQuestion> findDefaultQuestionsByChapterAndSection(@RequestBody DefaultQuestionDTO defaultQuestionDTO) {
        return defaultQuestionService.findAllByChapterAndSection(defaultQuestionDTO.getChapter(), defaultQuestionDTO.getSection());
    }

    @PatchMapping
    public void patchDefaultQuestion(@RequestBody DefaultQuestion defaultQuestion) {
        defaultQuestionService.save(defaultQuestion);
    }

    @PutMapping
    public void editDefaultQuestion(@RequestBody DefaultQuestion defaultQuestion) {
        defaultQuestionService.update(defaultQuestion);
    }

    @DeleteMapping
    public void delete(@RequestBody int id) {
        defaultQuestionService.delete(id);
    }
}
