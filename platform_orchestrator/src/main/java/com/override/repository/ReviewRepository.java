package com.override.repository;

import com.override.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findReviewByMentorLogin(String mentor);

    List<Review> findReviewByStudentLoginAndBookedDate(String student, LocalDate date);

    Review findFirstByStudentIdOrderByIdDesc(Long userId);

    List<Review> findReviewByStudentLogin(String student);

    List<Review> findReviewByBookedDate(LocalDate date);

    List<Review> findReviewByMentorIsNull();
}
