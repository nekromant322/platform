package com.override.repositories;

import com.override.models.PlatformUser;
import com.override.models.Review;
import com.override.models.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findReviewsById(Long id);
    List<Review> findReviewByMentor(PlatformUser mentor);
    List<Review> findReviewByStudent(PlatformUser student);
    List<Review> findReviewByBookedTimeSlots(List<TimeSlot> timeSlot);
    List<Review> findReviewsByMentorIsNull();
}
