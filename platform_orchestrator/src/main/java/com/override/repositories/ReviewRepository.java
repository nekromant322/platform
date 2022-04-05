package com.override.repositories;

import com.override.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findReviewByMentorLogin(String mentor);
    List<Review> findReviewByStudentLogin(String student);
    List<Review> findReviewByMentorLoginAndStudentLogin(String mentor, String student);
    List<Review> findReviewByBookedDate(LocalDate date);
    List<Review> findReviewByBookedDateAndBookedTime(LocalDate date, LocalTime time);
    List<Review> findReviewByMentorIsNull();
}
