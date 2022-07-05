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
    public ResponseEntity<List<DefaultQuestion>> findDefaultQuestions() {
        return new ResponseEntity<>(defaultQuestionService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<List<DefaultQuestion>> findDefaultQuestionsByChapterAndSection(@RequestBody DefaultQuestionDTO defaultQuestionDTO) {
        return new ResponseEntity<>(defaultQuestionService.findAllByChapterAndSection(defaultQuestionDTO.getChapter(), defaultQuestionDTO.getSection()), HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<String> patchDefaultQuestion(@RequestBody DefaultQuestion defaultQuestion) {
        defaultQuestionService.save(defaultQuestion);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<String> editDefaultQuestion(@RequestBody DefaultQuestion defaultQuestion) {
        defaultQuestionService.update(defaultQuestion);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestBody int id) {
        defaultQuestionService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
