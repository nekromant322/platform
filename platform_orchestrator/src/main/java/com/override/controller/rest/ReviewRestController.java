package com.override.controller.rest;

import com.override.models.Review;
import com.override.service.ReviewService;
import dtos.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewRestController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<String> requestReview(@RequestBody ReviewDTO reviewDTO) {
        return reviewService.requestReview(reviewDTO);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<String> approveReview(@RequestParam Long id) {
        return reviewService.approveReview(id);
    }

    @GetMapping("/one")
    public Review findReviewById(@RequestParam Long id) {
        return reviewService.findReviewById(id);
    }

    @GetMapping("/all")
    public List<Review> findAllReview() {
        return reviewService.findAllReview();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/mentors")
    public List<Review> findMentorReview(@RequestParam String login) {
        return reviewService.findMentorReview(login);
    }

    @GetMapping("/students")
    public List<Review> findStudentReview(@RequestParam String login) {
        return reviewService.findStudentReview(login);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<String> updateReview(@RequestBody ReviewDTO reviewDTO) {
        return reviewService.updateReview(reviewDTO);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping
    public ResponseEntity<String> deleteReview(@RequestParam Long id) {
        return reviewService.deleteReview(id);
    }
}
