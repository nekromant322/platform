package com.override.controller.rest;

import com.override.model.Question;
import com.override.service.CustomStudentDetailService;
import com.override.service.LessonStructureService;
import com.override.service.QuestionService;
import dto.QuestionDTO;
import io.swagger.annotations.ApiOperation;
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

    @GetMapping("/{login}/{chapter}")
    @ApiOperation(value = "Возвращает List<Question> по логину и главе")
    public List<Question> findAll(@PathVariable String login,
                                  @PathVariable String chapter) {
        return questionService.findAllByUserAndChapter(login, chapter);
    }

    @GetMapping("/my/{chapter}")
    @ApiOperation(value = "Возвращает List<Question> по логину и главе для текущего пользователся")
    public List<Question> findAllPersonal(@AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user,
                                          @PathVariable String chapter) {
        return questionService.findAllByUserAndChapter(user.getUsername(), chapter);
    }

    @GetMapping("/chapters")
    @ApiOperation(value = "До меня так и не дошло окончательно, зачем этот метод(")
    public List<String> findAllChapters() {
        return lessonStructureService.getChapterNamesList();
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    @ApiOperation(value = "Сохраняет объект в questionRepository, данные берет из questionDTO")
    public void save(@RequestBody QuestionDTO questionDTO) {
        questionService.save(questionDTO);
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping
    @ApiOperation(value = "Обновляет объект в questionRepository, данные берет из questionDTO")
    public void patch(@RequestBody QuestionDTO questionDTO) {
        questionService.patch(questionDTO);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping
    @ApiOperation(value = "Удаляет объект в questionRepository по id")
    public void delete(@RequestParam long id) {
        questionService.delete(id);
    }
}
