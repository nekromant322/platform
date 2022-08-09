package com.override.controller.rest;

import com.override.model.enums.Status;
import com.override.service.InterviewReportService;
import dto.InterviewReportDTO;
import dto.InterviewReportUpdateDTO;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "Сохраняет InterviewReport в interviewReportRepository, возвращает ResponseEntity<>(body:\"Отчёт о собеседовании сохранён!\", HttpStatus.OK)")
    public ResponseEntity<String> saveInterviewReport(@RequestBody InterviewReportDTO interviewReportDTO) {
        interviewReportService.save(interviewReportDTO);
        return new ResponseEntity<>("Отчёт о собеседовании сохранён!", HttpStatus.OK);
    }

    @PatchMapping("/offer")
    @ApiOperation(value = "Меняет статут в объекте InterviewReport на \"Status.OFFER\", возвращает ResponseEntity<>(body:\"Статус собеседования изменён на Offer!\", HttpStatus.OK)")
    public ResponseEntity<String> changeStatusToOffer(@RequestBody InterviewReportUpdateDTO interviewReportUpdateDTO) {
        interviewReportService.update(interviewReportUpdateDTO, Status.OFFER);
        return new ResponseEntity<>("Статус собеседования изменён на Offer!", HttpStatus.OK);
    }

    @PatchMapping("/accepted")
    @ApiOperation(value = "Меняет статут в объекте InterviewReport на \"Status.ACCEPTED\", возвращает ResponseEntity<>(body:\"Статус собеседования изменён на Accepted!\", HttpStatus.OK)")
    public ResponseEntity<String> changeStatusToAccepted(@RequestBody InterviewReportUpdateDTO interviewReportUpdateDTO) {
        interviewReportService.update(interviewReportUpdateDTO, Status.ACCEPTED);
        return new ResponseEntity<>("Статус собеседования изменён на Accepted!", HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value = "Возвращает весь список List<InterviewReportDTO> из interviewReportRepository")
    public List<InterviewReportDTO> findAllInterviewReports() {
        return interviewReportService.findAll();
    }

    @DeleteMapping
    @ApiOperation(value = "Удаляет InterviewReport из interviewReportRepository, возвращает ResponseEntity<>(body:\"Отчёт о собеседовании удалён!\", HttpStatus.OK)")
    public ResponseEntity<String> deleteInterviewReport(@RequestParam Long id) {
        interviewReportService.delete(id);
        return new ResponseEntity<>("Отчёт о собеседовании удалён!", HttpStatus.OK);
    }
}
