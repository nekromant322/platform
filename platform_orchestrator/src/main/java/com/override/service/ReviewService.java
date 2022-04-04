package com.override.service;

import com.override.mappers.ReviewMapper;
import com.override.mappers.TimeSlotMapper;
import com.override.models.Review;
import com.override.repositories.PlatformUserRepository;
import com.override.repositories.ReviewRepository;
import com.override.repositories.TimeSlotRepository;
import dtos.ReviewDTO;
import dtos.TimeSlotDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private PlatformUserRepository platformUserRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private TimeSlotMapper timeSlotMapper;

    public void saveOrUpdateReview(ReviewDTO reviewDTO) {
        reviewRepository.save(reviewMapper.dtoToEntity(reviewDTO,
                platformUserRepository.findFirstByLogin(reviewDTO.getStudentLogin()),
                platformUserRepository.findFirstByLogin(reviewDTO.getMentorLogin()),
                timeSlotRepository.findTimeSlotsById(reviewDTO.getBookedTimeSlots().iterator().next().getId())));
    }

    public void saveOrUpdateTimeSlot(TimeSlotDTO timeSlotDTO) {
        timeSlotRepository.save(timeSlotMapper.dtoToEntity(timeSlotDTO,
                reviewRepository.findReviewsById(timeSlotDTO.getReviews().iterator().next().getId())));
    }

    public void deleteReview(ReviewDTO reviewDTO) {
        reviewRepository.deleteById(reviewDTO.getId());
    }

    public void deleteTimeSlot(TimeSlotDTO timeSlotDTO) {
        timeSlotRepository.deleteById(timeSlotDTO.getId());
    }

    public List<Review> findReviewByMentor(ReviewDTO reviewDTO) {
        return reviewRepository.findReviewByMentor(platformUserRepository.
                findFirstByLogin(reviewDTO.getMentorLogin()));
    }

    public List<Review> findReviewByStudent(ReviewDTO reviewDTO) {
        return reviewRepository.findReviewByStudent(platformUserRepository.
                findFirstByLogin(reviewDTO.getStudentLogin()));
    }

    public List<Review> findReviewByBookedDate(TimeSlotDTO timeSlotDTO) {
        return reviewRepository.findReviewByBookedTimeSlots(timeSlotRepository.
                findTimeSlotByBookedDate(timeSlotDTO.getBookedDate()));
    }

    public List<Review> findReviewByBookedDateAndTime(TimeSlotDTO timeSlotDTO) {
        return reviewRepository.findReviewByBookedTimeSlots(timeSlotRepository.
                findTimeSlotByBookedDateAndBookedTime(timeSlotDTO.getBookedDate(), timeSlotDTO.getBookedTime()));
    }

    public List<Review> findReviewByMentorIsNull() {
        return reviewRepository.findReviewsByMentorIsNull();
    }
}
