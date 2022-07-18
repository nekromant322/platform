package com.override.controller.rest;

import com.override.model.PreProjectLesson;
import com.override.service.CustomStudentDetailService;
import com.override.service.PreProjectLessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/preProjectLink")
public class PreProjectLessonsController {

    @Autowired
    private PreProjectLessonService preProjectLessonService;

    @PostMapping()
    public PreProjectLesson savePreProjectLessonLink(@RequestBody PreProjectLesson link, @AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) {
        return preProjectLessonService.saveLink(link, user.getUsername());
    }
}
