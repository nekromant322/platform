package com.override.controller.rest;

import com.override.model.enums.Status;
import com.override.service.InterviewReportService;
import dto.InterviewReportDTO;
import dto.InterviewReportUpdateDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interviewReport")
public class InterviewReportRestController {

    @Autowired
    private InterviewReportService interviewReportService;

    @PatchMapping
    @ApiOperation(value = "Сохраняет интервью репорт в БД")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Отчёт о собеседовании сохранён!")
    })
    public ResponseEntity<String> saveInterviewReport(@RequestBody InterviewReportDTO interviewReportDTO) {
        interviewReportService.save(interviewReportDTO);
        return new ResponseEntity<>("Отчёт о собеседовании сохранён!", HttpStatus.OK);
    }

    @PatchMapping("/offer")
    @ApiOperation(value = "Меняет статут в интервью репорте на \"Status.OFFER\"")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Статус собеседования изменён на Offer!")
    })
    public ResponseEntity<String> changeStatusToOffer(@RequestBody InterviewReportUpdateDTO interviewReportUpdateDTO) {
        interviewReportService.update(interviewReportUpdateDTO, Status.OFFER);
        return new ResponseEntity<>("Статус собеседования изменён на Offer!", HttpStatus.OK);
    }

    @PatchMapping("/accepted")
    @ApiOperation(value = "Меняет статут в интервью репорте на \"Status.ACCEPTED\"")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Статус собеседования изменён на Accepted!")
    })
    public ResponseEntity<String> changeStatusToAccepted(@RequestBody InterviewReportUpdateDTO interviewReportUpdateDTO) {
        interviewReportService.update(interviewReportUpdateDTO, Status.ACCEPTED);
        return new ResponseEntity<>("Статус собеседования изменён на Accepted!", HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value = "Возвращает все интервью репорты из БД")
    public List<InterviewReportDTO> findAllInterviewReports() {
        return interviewReportService.findAll();
    }

    @DeleteMapping
    @ApiOperation(value = "Удаляет интервью репорт по его id из БД")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Отчёт о собеседовании удалён!")
    })
    public ResponseEntity<String> deleteInterviewReport(@RequestParam Long id) {
        interviewReportService.delete(id);
        return new ResponseEntity<>("Отчёт о собеседовании удалён!", HttpStatus.OK);
    }
}
