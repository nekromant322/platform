package com.override.controller.rest;

import com.override.model.PreProjectLesson;
import com.override.service.CustomStudentDetailService;
import com.override.service.PreProjectLessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/preProjectLink")
public class PreProjectLessonsController {

    @Autowired
    private PreProjectLessonService preProjectLessonService;

    @GetMapping()
    public List<PreProjectLesson> getAll() {
        return preProjectLessonService.getAll();
    }

    @PostMapping("/current")
    public List<PreProjectLesson> getCurrent(@RequestBody PreProjectLesson link, @AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) {
        return preProjectLessonService.getAllByPathName(link, user.getUsername());
    }

    @PostMapping()
    public PreProjectLesson savePreProjectLessonLink(@RequestBody PreProjectLesson link, @AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) {
        return preProjectLessonService.saveLink(link, user.getUsername());
    }

    @PatchMapping()
    public ResponseEntity<String> updatePreProjectLessonLink(@RequestBody PreProjectLesson link) {
        preProjectLessonService.update(link);
        return new ResponseEntity<>("Комментарии сохранены!", HttpStatus.OK);
    }
}
