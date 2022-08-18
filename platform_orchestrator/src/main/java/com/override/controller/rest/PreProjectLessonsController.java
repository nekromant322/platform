package com.override.controller.rest;

import com.override.service.CustomStudentDetailService;
import com.override.service.PreProjectLessonService;
import dto.PreProjectLessonDTO;
import dto.PreProjectLessonMentorReactionDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiOperation(value = "Возвращает весь список \"предпроектных уроков\" из БД")
    public List<PreProjectLessonMentorReactionDTO> getAll() {
        return preProjectLessonService.getAll();
    }

    @PostMapping("/current")
    @ApiOperation(value = "Возвращает список \"предпроектных уроков\" для текущего юзера по идентификатору задачи, который берется из ДТОшки")
    public List<PreProjectLessonMentorReactionDTO> getCurrent(@RequestBody PreProjectLessonDTO preProjectLessonDTO, @AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) {
        return preProjectLessonService.getAllByPathName(preProjectLessonDTO, user.getUsername());
    }

    @PostMapping
    @ApiOperation(value = "Сохраняет \"предпроектный урок\" в БД, ссылку и идентификатор задачи берется из ДТОшки")
    public PreProjectLessonDTO savePreProjectLessonLink(@RequestBody PreProjectLessonDTO preProjectLessonDTO, @AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) {
        return preProjectLessonService.save(preProjectLessonDTO, user.getUsername());
    }

    @PatchMapping
    @ApiOperation(value = "Сохраняет \"предпроектный урок\" в БД, при этом обновляя поля Comments, Approve, " +
            "Viewed у \"предпроектного урока\", который мы получаем из ДТОшки.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Комментарии сохранены!")
    })
    public ResponseEntity<String> updatePreProjectLessonLink(@RequestBody PreProjectLessonMentorReactionDTO preProjectLessonMentorReactionDTO) {
        preProjectLessonService.update(preProjectLessonMentorReactionDTO);
        return new ResponseEntity<>("Комментарии сохранены!", HttpStatus.OK);
    }
}
