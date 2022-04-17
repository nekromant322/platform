package com.override.controller.rest;

import com.override.service.CustomStudentDetailService;
import com.override.service.LessonProgressService;
import com.override.service.PlatformUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessonProgress")
public class LessonProgressRestController {

    @Autowired
    private LessonProgressService lessonProgressService;

    @Autowired
    private PlatformUserService platformUserService;

    @PostMapping("/{lesson}")
    public void checkLesson(@AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user,
                            @PathVariable String lesson) {
        lessonProgressService.checkLesson(platformUserService.findPlatformUserByLogin(user.getUsername()), lesson);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/allStat/{login}")
    public List<String> getPassedLessons(@PathVariable String login) {
        return platformUserService.findPlatformUserByLogin(login).getLessonProgress();
    }

    @GetMapping("/allStat")
    public List<String> getPassedLessonsForCurrentUser(
            @AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) {
        return platformUserService.findPlatformUserByLogin(user.getUsername()).getLessonProgress();
    }
}
