package com.override.controller.rest;

import com.override.service.CustomStudentDetailService;
import com.override.service.PreProjectLessonService;
import dto.PreProjectLessonDTO;
import dto.PreProjectLessonMentorReactionDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/preProjectLink")
public class PreProjectLessonsController {

    @Autowired
    private PreProjectLessonService preProjectLessonService;

    @Secured("ROLE_ADMIN")
    @GetMapping
    @ApiOperation(value = "Возвращает List<PreProjectLessonMentorReactionDTO> (весь список из preProjectLessonRepository)")
    public List<PreProjectLessonMentorReactionDTO> getAll() {
        return preProjectLessonService.getAll();
    }


    @PostMapping("/current")
    @ApiOperation(value = "Возвращает List<PreProjectLessonMentorReactionDTO> (возвращается список для текущего юзера по идентификатору задачи, который берется из preProjectLessonDTO)")
    public List<PreProjectLessonMentorReactionDTO> getCurrent(@RequestBody PreProjectLessonDTO preProjectLessonDTO, @AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) {
        return preProjectLessonService.getAllByPathName(preProjectLessonDTO, user.getUsername());
    }

    @PostMapping
    @ApiOperation(value = "Сохраняет объект в preProjectLessonRepository, ссылку и идентификатор задачи берется из preProjectLessonDTO")
    public PreProjectLessonDTO savePreProjectLessonLink(@RequestBody PreProjectLessonDTO preProjectLessonDTO, @AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) {
        return preProjectLessonService.save(preProjectLessonDTO, user.getUsername());
    }

    @PatchMapping
    @ApiOperation(value = "Сохраняет объект в preProjectLessonRepository, при этом обновляя поля Comments, Approve, Viewed у PreProjectLesson, который мы получаем из preProjectLessonMentorReactionDTO. " +
            "Возвращает ResponseEntity<>(body:\"Комментарии сохранены!\", HttpStatus.OK)")
    public ResponseEntity<String> updatePreProjectLessonLink(@RequestBody PreProjectLessonMentorReactionDTO preProjectLessonMentorReactionDTO) {
        preProjectLessonService.update(preProjectLessonMentorReactionDTO);
        return new ResponseEntity<>("Комментарии сохранены!", HttpStatus.OK);
    }
}
