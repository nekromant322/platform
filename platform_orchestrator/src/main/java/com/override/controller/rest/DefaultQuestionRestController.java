package com.override.controller.rest;

import com.override.model.DefaultQuestion;
import com.override.service.DefaultQuestionService;
import dto.DefaultQuestionDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/defaultQuestions")
public class DefaultQuestionRestController {
    @Autowired
    private DefaultQuestionService defaultQuestionService;

    @Operation(summary = "Возвращает все дефолтные вопросы из БД")
    @GetMapping
    public List<DefaultQuestion> findDefaultQuestions() {
        return defaultQuestionService.findAll();
    }

//    @ApiOperation(value = "Возвращает дефолтные вопросы по главе и секции")
    @Operation(summary = "Возвращает дефолтные вопросы по главе и секции")
    @PostMapping
    public List<DefaultQuestion> findDefaultQuestionsByChapterAndSection(@RequestBody DefaultQuestionDTO defaultQuestionDTO) {
        return defaultQuestionService.findAllByChapterAndSection(defaultQuestionDTO.getChapter(), defaultQuestionDTO.getSection());
    }

    @PatchMapping
    @Operation(summary = "Сохраняет дефолтный вопрос в БД")
    public void patchDefaultQuestion(@RequestBody DefaultQuestion defaultQuestion) {
        defaultQuestionService.save(defaultQuestion);
    }

    @PutMapping
    @Operation(summary = "Обновляет дефолтный вопрос в БД")
    public void editDefaultQuestion(@RequestBody DefaultQuestion defaultQuestion) {
        defaultQuestionService.update(defaultQuestion);
    }

    @DeleteMapping
    @Operation(summary = "Удаляет дефолтный вопрос по его id из БД")
    public void delete(@RequestBody int id) {
        defaultQuestionService.delete(id);
    }
}
