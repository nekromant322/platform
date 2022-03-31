package com.override.controller.rest;

import com.override.models.Review;
import com.override.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewRestController {

    @Autowired
    private ReviewService reviewService;

    // В конце раздела студент сможет запросить ревью, задав тему и выбрав дату и время из свободных слотов
    // Ревью при этом сохраняется в бд, но со статусом confirmed(false), это значит что время забронированно, но ещё не подтверждено
    @PostMapping
    public ResponseEntity<String> requestReview(@RequestBody Review review, @RequestParam LocalDate date,
                                                @RequestParam int index) {
        review.setBookedDateTime(date, index);
        return reviewService.requestReview(review);
    }

    // Ментор получает запрос на ревью и подтверждает бронь (или не подтверждает)
    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<String> appointReview(@RequestParam Long id) {
        return reviewService.appointReview(id);
    }

    @GetMapping("/one")
    public Review findReviewById(@RequestParam Long id) {
        return reviewService.findReviewById(id);
    }

    @GetMapping("/all")
    public List<Review> findAllReview() {
        return reviewService.findAllReview();
    }

    // Ментор может смотреть все свои ревью, будет внедрён фильтр на прошедшие и запланированные, а также по датам
    @Secured("ROLE_ADMIN")
    @GetMapping("/mentors")
    public List<Review> findMentorReview(@RequestParam String login) {
        return reviewService.findMentorReview(login);
    }

    // Аналогично, только для студента
    @GetMapping("/students")
    public List<Review> findStudentReview(@RequestParam String login) {
        return reviewService.findStudentReview(login);
    }

    // Нужно, чтобы изменить дату например или назначить другого ментора
    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<String> updateReview(@RequestBody Review review) {
        return reviewService.updateReview(review);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping
    public ResponseEntity<String> deleteReview(@RequestParam Long id) {
        return reviewService.deleteReview(id);
    }
}
