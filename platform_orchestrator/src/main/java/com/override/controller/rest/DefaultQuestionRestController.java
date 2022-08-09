package com.override.controller.rest;

import com.override.model.DefaultQuestion;
import com.override.service.DefaultQuestionService;
import dto.DefaultQuestionDTO;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "Возвращает List<DefaultQuestion> из defaultQuestionRepository")
    @GetMapping
    public List<DefaultQuestion> findDefaultQuestions() {
        return defaultQuestionService.findAll();
    }

    @ApiOperation(value = "Возвращает List<DefaultQuestion> из defaultQuestionRepository по главе и секции")
    @PostMapping
    public List<DefaultQuestion> findDefaultQuestionsByChapterAndSection(@RequestBody DefaultQuestionDTO defaultQuestionDTO) {
        return defaultQuestionService.findAllByChapterAndSection(defaultQuestionDTO.getChapter(), defaultQuestionDTO.getSection());
    }

    @PatchMapping
    @ApiOperation(value = "Сохраняет DefaultQuestion в defaultQuestionRepository")
    public void patchDefaultQuestion(@RequestBody DefaultQuestion defaultQuestion) {
        defaultQuestionService.save(defaultQuestion);
    }

    @PutMapping
    @ApiOperation(value = "Обновляет DefaultQuestion в defaultQuestionRepository")
    public void editDefaultQuestion(@RequestBody DefaultQuestion defaultQuestion) {
        defaultQuestionService.update(defaultQuestion);
    }

    @DeleteMapping
    @ApiOperation(value = "Удаляет DefaultQuestion из defaultQuestionRepository")
    public void delete(@RequestBody int id) {
        defaultQuestionService.delete(id);
    }
}
