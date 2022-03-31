package com.override.repositories;

import com.override.models.PlatformUser;
import com.override.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findReviewById(Long id);
    List<Review> findAllReview();
    List<Review> findMentorReview(PlatformUser mentor);
    List<Review> findStudentReview(PlatformUser student);
}
