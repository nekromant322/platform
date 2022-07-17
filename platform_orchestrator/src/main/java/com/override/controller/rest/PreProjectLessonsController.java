package com.override.controller.rest;

import com.override.service.CustomStudentDetailService;
import com.override.service.PreProjectLessonService;
import dto.PreProjectLessonDTO;
import dto.PreProjectLessonMentorReactionDTO;
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
    public List<PreProjectLessonMentorReactionDTO> getAll() {
        return preProjectLessonService.getAll();
    }


    @PostMapping("/current")
    public List<PreProjectLessonMentorReactionDTO> getCurrent(@RequestBody PreProjectLessonDTO preProjectLessonDTO, @AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) {
        return preProjectLessonService.getAllByPathName(preProjectLessonDTO, user.getUsername());
    }

    @PostMapping
    public PreProjectLessonDTO savePreProjectLessonLink(@RequestBody PreProjectLessonDTO preProjectLessonDTO, @AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) {
        return preProjectLessonService.save(preProjectLessonDTO, user.getUsername());
    }

    @PatchMapping
    public ResponseEntity<String> updatePreProjectLessonLink(@RequestBody PreProjectLessonMentorReactionDTO preProjectLessonMentorReactionDTO) {
        preProjectLessonService.update(preProjectLessonMentorReactionDTO);
        return new ResponseEntity<>("Комментарии сохранены!", HttpStatus.OK);
    }
}
