package com.override.controllers;

import com.override.feigns.CodeExecutorFeign;
import dtos.CodeTryDTO;
import dtos.TaskIdentifierDTO;
import dtos.TestResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("lessons")
public class LessonController {

    @Autowired
    private CodeExecutorFeign codeExecutorFeign;

    @GetMapping("{course}/{chapter}/{step}/{lesson}")
    public String lessonPage(@PathVariable String course, @PathVariable Integer chapter, @PathVariable Integer step,
                             @PathVariable Integer lesson) {
        return "lessons" + "/" + course + "/" + chapter + "/" + step + "/" + lesson;
    }

    @PostMapping
    @ResponseBody
    public void getCodeTryResult(@RequestBody CodeTryDTO codeTryDTO) {
        codeExecutorFeign.execute(codeTryDTO);
    }
}
