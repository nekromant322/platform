package com.override.controller.rest;

import com.override.models.Review;
import com.override.service.CustomStudentDetailService;
import com.override.service.ReviewService;
import dtos.ReviewDTO;
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
    public ResponseEntity<String> saveOrUpdateReview(@RequestBody ReviewDTO reviewDTO,
                                                     @AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) {
        reviewService.saveOrUpdateReview(reviewDTO, user.getUsername());
        return new ResponseEntity<>("Ревью сохранено!", HttpStatus.OK);
    }

    @GetMapping
    public List<Review> findReview(@RequestBody ReviewDTO reviewDTO) {
        return reviewService.findReview(reviewDTO);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping
    public ResponseEntity<String> deleteReview(@RequestBody ReviewDTO reviewDTO) {
        reviewService.deleteReview(reviewDTO);
        return new ResponseEntity<>("Ревью удалено!", HttpStatus.OK);
    }
}
