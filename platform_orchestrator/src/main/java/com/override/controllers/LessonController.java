package com.override.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LessonController {

    @GetMapping("/lessons/{course}/{chapter}/{step}/{lesson}")
    public String lessonPage(@PathVariable String course, @PathVariable Integer chapter, @PathVariable Integer step,
                             @PathVariable Integer lesson) {
        return "lessons" + "/" + course + "/" + chapter + "/" + step + "/" + lesson;
    }
}
