package com.override.controller.rest;

import com.override.model.Question;
import com.override.service.CustomStudentDetailService;
import com.override.service.LessonStructureService;
import com.override.service.QuestionService;
import dto.QuestionDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionRestController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private LessonStructureService lessonStructureService;

    @Secured("ROLE_ADMIN")
    @GetMapping("/{login}/{chapter}")
    @Operation(summary = "Возвращает список вопросов по логину и главе")
    public List<Question> findAll(@PathVariable String login,
                                  @PathVariable String chapter) {
        return questionService.findAllByUserAndChapter(login, chapter);
    }

    @GetMapping("/my/{chapter}")
    @Operation(summary = "Возвращает список вопросов по логину и главе для текущего пользователся")
    public List<Question> findAllPersonal(@AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user,
                                          @PathVariable String chapter) {
        return questionService.findAllByUserAndChapter(user.getUsername(), chapter);
    }

    @GetMapping("/chapters")
    @Operation(summary = "Возвращает список глав, по которым есть вопросы")
    public List<String> findAllChapters() {
        return lessonStructureService.getChapterNamesList();
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    @Operation(summary = "Сохраняет вопрос в БД, данные берет из ДТОшки")
    public void save(@RequestBody QuestionDTO questionDTO) {
        questionService.save(questionDTO);
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping
    @Operation(summary = "Обновляет вопрос в БД, данные берет из ДТОшки")
    public void patch(@RequestBody QuestionDTO questionDTO) {
        questionService.patch(questionDTO);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping
    @Operation(summary = "Удаляет вопрос из БД по id")
    public void delete(@RequestParam long id) {
        questionService.delete(id);
    }
}
