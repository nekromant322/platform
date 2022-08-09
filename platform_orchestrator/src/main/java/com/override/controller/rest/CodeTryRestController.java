package com.override.controller.rest;

import com.override.feign.CodeExecutorFeign;
import com.override.model.CodeTry;
import com.override.service.CodeTryService;
import dto.CodeTryDTO;
import dto.TaskIdentifierDTO;
import dto.TestResultDTO;
import enums.CodeExecutionStatus;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.override.service.CustomStudentDetailService.CustomStudentDetails;

@RestController
@RequestMapping("codeTry")
public class CodeTryRestController {

    @Autowired
    private CodeTryService studentCodeService;
    @Autowired
    private CodeExecutorFeign codeExecutorFeign;
    @Autowired
    private CodeTryService codeTryService;

    @PostMapping
    @ApiOperation(value = "Если в коде есть синтаксические ошибки или он не проходит по длине, " +
            "возвращает ResponseEntity<>(body:\"Введенный код некорректен (не прошел по длине)\", " +
            "HttpStatus.BAD_REQUEST), иначе сохраняет объект CodeTry в codeTryRepository. " +
            "Далее, если пройдены все тесты, то возвращает ResponseEntity<>(body:\"Все тесты пройдены\", HttpStatus.OK), " +
            "иначе возвращает ResponseEntity<>(Ошибка компиляции кода(будет именно сообщение, в чем ошибка), HttpStatus.BAD_REQUEST)")
    public ResponseEntity<String> getCodeTryResult(@RequestBody @Valid CodeTryDTO codeTryDTO, BindingResult result,
                                                   @AuthenticationPrincipal CustomStudentDetails user) {
        if (result.hasErrors()) {
            return new ResponseEntity<>("Введенный код некорректен (не прошел по длине)", HttpStatus.BAD_REQUEST);
        }
        TestResultDTO testResult = codeExecutorFeign.execute(codeTryDTO);
        studentCodeService.saveCodeTry(codeTryDTO, testResult, user.getUsername());

        if (testResult.getCodeExecutionStatus() == CodeExecutionStatus.OK) {
            return new ResponseEntity<>("Все тесты пройдены", HttpStatus.OK);
        }
        return new ResponseEntity<>(testResult.getOutput(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    @ApiOperation(value = "Возвращает List<CodeTry> для текущего юзера по главе, шагу и уроку")
    public List<CodeTry> studentCodesLesson(@AuthenticationPrincipal CustomStudentDetails user,
                                            @RequestParam int chapter, @RequestParam int step, @RequestParam int lesson) {
        TaskIdentifierDTO taskIdentifierDTO = TaskIdentifierDTO.builder()
                .chapter(chapter)
                .step(step)
                .lesson(lesson)
                .build();
        return codeTryService.findAllByLesson(user.getUsername(), taskIdentifierDTO);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Возвращает List<CodeTry> по id студента")
    public List<CodeTry> getStudentTries(@PathVariable Long id) {
        return codeTryService.findAllByUserId(id);
    }
}
