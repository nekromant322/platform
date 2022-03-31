package com.override.service;

import com.override.models.PlatformUser;
import com.override.models.Review;
import com.override.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private PlatformUserService userService;

    @Autowired
    private ReviewRepository reviewRepository;

    public ResponseEntity<String> requestReview(Review review, LocalDate date, int index) {
        review.setConfirmed(false);
        review.setBookedDateTime(setDateTime(date, index));
        reviewRepository.save(review);
        return new ResponseEntity<>("Отправлен запрос на ревью!", HttpStatus.OK);
    }

    public ResponseEntity<String> approveReview(Long id) {
        Review review = reviewRepository.findReviewById(id);
        review.setConfirmed(true);
        reviewRepository.save(review);
        return new ResponseEntity<>("Ревью подтверждено!", HttpStatus.OK);
    }

    public Review findReviewById(Long id) {
        return reviewRepository.findReviewById(id);
    }

    public List<Review> findAllReview() {
        return reviewRepository.findAllReview();
    }

    public List<Review> findMentorReview(String login) {
        PlatformUser mentor = userService.findPlatformUserByLogin(login);
        return reviewRepository.findMentorReview(mentor);
    }

    public List<Review> findStudentReview(String login) {
        PlatformUser student = userService.findPlatformUserByLogin(login);
        return reviewRepository.findStudentReview(student);
    }

    public ResponseEntity<String> updateReview(Review review) {
        reviewRepository.save(review);
        return new ResponseEntity<>("Ревью обновлено!", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteReview(Long id) {
        reviewRepository.deleteById(id);
        return new ResponseEntity<>("Ревью удалено!", HttpStatus.OK);
    }

    public List<LocalDateTime> generateDefaultDateTimeSlots(LocalDate date) {
        List<LocalDateTime> dateTimeSlots = new ArrayList<>();
        LocalTime time = LocalTime.of(0, 0);
        for (int i = 1; i < 48; i++) {
            dateTimeSlots.add(LocalDateTime.of(date, time));
            time = time.plusMinutes(30);
        }
        return dateTimeSlots;
    }

    public LocalDateTime setDateTime(LocalDate date, int index) {
        List<LocalDateTime> dateTimeSlots = generateDefaultDateTimeSlots(date);
        return dateTimeSlots.get(index);
    }
}
