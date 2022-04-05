package com.override.service;

import com.override.mappers.ReviewMapper;
import com.override.models.Review;
import com.override.repositories.PlatformUserRepository;
import com.override.repositories.ReviewRepository;
import dtos.ReviewDTO;
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
    private ReviewMapper reviewMapper;

    public void saveOrUpdateReview(ReviewDTO reviewDTO) {
        reviewRepository.save(reviewMapper.dtoToEntity(reviewDTO,
                platformUserRepository.findFirstByLogin(reviewDTO.getStudentLogin()),
                platformUserRepository.findFirstByLogin(reviewDTO.getMentorLogin())));
    }

    public void deleteReview(ReviewDTO reviewDTO) {
        reviewRepository.deleteById(reviewDTO.getId());
    }

    public List<Review> findReviewByMentorLogin(ReviewDTO reviewDTO) {
        return reviewRepository.findReviewByMentorLogin(reviewDTO.getMentorLogin());
    }

    public List<Review> findReviewByStudentLogin(ReviewDTO reviewDTO) {
        return reviewRepository.findReviewByStudentLogin(reviewDTO.getStudentLogin());
    }

    public List<Review> findReviewByMentorLoginAndStudentLogin(ReviewDTO reviewDTO) {
        return reviewRepository.findReviewByMentorLoginAndStudentLogin(reviewDTO.getMentorLogin(), reviewDTO.getStudentLogin());
    }

    public List<Review> findReviewByBookedDate(ReviewDTO reviewDTO) {
        return reviewRepository.findReviewByBookedDate(reviewDTO.getBookedDate());
    }

    public List<Review> findReviewByBookedDateAndTime(ReviewDTO reviewDTO) {
        return reviewRepository.findReviewByBookedDateAndBookedTime(reviewDTO.getBookedDate(), reviewDTO.getBookedTime());
    }

    public List<Review> findReviewByMentorIsNull() {
        return reviewRepository.findReviewByMentorIsNull();
    }
}
