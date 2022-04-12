package com.override.controller.rest;

import com.override.service.LessonProgressService;
import com.override.service.PlatformUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessonProgress")
public class LessonProgressRestController {

    @Autowired
    private LessonProgressService lessonProgressService;

    @Autowired
    private PlatformUserService platformUserService;

    @PatchMapping("/{login}/{lesson}")
    public void checkLesson(@PathVariable String login,
                            @PathVariable String lesson) {
        lessonProgressService.checkLesson(platformUserService.findPlatformUserByLogin(login), lesson);
    }

    @GetMapping("/{login}")
    public List<String> getPassedLessons(@PathVariable String login) {
        return lessonProgressService.getPassedLessons(platformUserService.findPlatformUserByLogin(login));
    }
}
