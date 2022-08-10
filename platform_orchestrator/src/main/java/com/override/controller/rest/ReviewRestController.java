package com.override.controller.rest;

import com.override.service.CustomStudentDetailService;
import com.override.service.ReviewService;
import dto.ReviewDTO;
import dto.ReviewFilterDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewRestController {

    @Autowired
    private ReviewService reviewService;

    @PatchMapping
    @ApiOperation(value = "Сохраняет новое или изменяет существующее ревью в БД, зависит от значения полей ДТОшки")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ревью сохранено!")
    })
    public ResponseEntity<String> saveOrUpdateReview(@RequestBody ReviewDTO reviewDTO,
                                                     @AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) {
        reviewService.saveOrUpdate(reviewDTO, user.getUsername());
        return new ResponseEntity<>("Ревью сохранено!", HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Возвращает список ревью, в зависимости от значения полей ДТОшки")
    public List<ReviewDTO> findReview(@RequestBody ReviewFilterDTO reviewFilterDTO) {
        return reviewService.find(reviewFilterDTO);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping
    @ApiOperation(value = "Удаляет ревью по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ревью удалено!")
    })
    public ResponseEntity<String> deleteReview(@RequestParam Long id) {
        reviewService.delete(id);
        return new ResponseEntity<>("Ревью удалено!", HttpStatus.OK);
    }
}
