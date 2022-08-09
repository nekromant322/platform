package com.override.controller.rest;

import com.override.service.CustomStudentDetailService;
import com.override.service.LessonProgressService;
import com.override.service.PlatformUserService;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "Создается объект LessonProgress (по имени текущего юзера и уроку) и сохраняется в lessonProgressRepository, " +
            "если его там еще нет. Другими словами указанный lesson вносится в коллекцию с пройдеными уроками, " +
            "тем самым \"помечает\" его как пройденный")
    public void checkLesson(@AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user,
                            @PathVariable String lesson) {
        lessonProgressService.markLessonAsPassed(platformUserService.findPlatformUserByLogin(user.getUsername()), lesson);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/allStat/{login}")
    @ApiOperation(value = "Возвращает список пройденных юзером уроков, по логину юзера для админа.")
    public List<String> getPassedLessons(@PathVariable String login) {
        return lessonProgressService.getPassedLessons(platformUserService.findPlatformUserByLogin(login));
    }

    @GetMapping("/allStat")
    @ApiOperation(value = "Возвращает список пройденных юзером уроков, для текущего юзера.")
    public List<String> getPassedLessonsForCurrentUser(
            @AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) {
        return lessonProgressService.getPassedLessons(platformUserService.findPlatformUserByLogin(user.getUsername()));
    }
}
