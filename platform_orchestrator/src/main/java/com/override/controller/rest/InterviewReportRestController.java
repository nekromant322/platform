package com.override.controller.rest;

import com.override.model.enums.Status;
import com.override.service.InterviewReportService;
import dto.InterviewReportDTO;
import dto.InterviewReportUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Сохраняет интервью репорт в БД")
    @ApiResponse(responseCode = "200", description = "Отчёт о собеседовании сохранён!")
    public ResponseEntity<String> saveInterviewReport(@RequestBody InterviewReportDTO interviewReportDTO) {
        interviewReportService.save(interviewReportDTO);
        return new ResponseEntity<>("Отчёт о собеседовании сохранён!", HttpStatus.OK);
    }

    @PatchMapping("/offer")
    @Operation(summary = "Меняет статут в интервью репорте на \"Status.OFFER\"")
    @ApiResponse(responseCode = "200", description = "Статус собеседования изменён на Offer!")
    public ResponseEntity<String> changeStatusToOffer(@RequestBody InterviewReportUpdateDTO interviewReportUpdateDTO) {
        interviewReportService.update(interviewReportUpdateDTO, Status.OFFER);
        return new ResponseEntity<>("Статус собеседования изменён на Offer!", HttpStatus.OK);
    }

    @PatchMapping("/accepted")
    @Operation(summary = "Меняет статут в интервью репорте на \"Status.ACCEPTED\"")
    @ApiResponse(responseCode = "200", description = "Статус собеседования изменён на Accepted!")
    public ResponseEntity<String> changeStatusToAccepted(@RequestBody InterviewReportUpdateDTO interviewReportUpdateDTO) {
        interviewReportService.update(interviewReportUpdateDTO, Status.ACCEPTED);
        return new ResponseEntity<>("Статус собеседования изменён на Accepted!", HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Возвращает все интервью репорты из БД")
    public List<InterviewReportDTO> findAllInterviewReports() {
        return interviewReportService.findAll();
    }

    @DeleteMapping
    @Operation(summary = "Удаляет интервью репорт по его id из БД")
    @ApiResponse(responseCode = "200", description = "Отчёт о собеседовании удалён!")
    public ResponseEntity<String> deleteInterviewReport(@RequestParam Long id) {
        interviewReportService.delete(id);
        return new ResponseEntity<>("Отчёт о собеседовании удалён!", HttpStatus.OK);
    }
}
