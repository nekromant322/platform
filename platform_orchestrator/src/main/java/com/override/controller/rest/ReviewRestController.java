package com.override.controller.rest;

import com.override.models.Review;
import com.override.service.ReviewService;
import dtos.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewRestController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<String> saveOrUpdateReview(@RequestBody ReviewDTO reviewDTO) {
        reviewService.saveOrUpdateReview(reviewDTO);
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
