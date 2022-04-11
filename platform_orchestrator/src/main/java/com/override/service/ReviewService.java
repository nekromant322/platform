package com.override.service;

import com.override.mappers.ReviewMapper;
import com.override.models.Authority;
import com.override.models.PlatformUser;
import com.override.models.Review;
import com.override.models.enums.Role;
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

    public void saveOrUpdateReview(ReviewDTO reviewDTO, String userLogin) {
        if (reviewDTO.getId() == null && reviewDTO.getStudentLogin() == null) {
            reviewDTO.setStudentLogin(userLogin);
        } else {
            reviewDTO.setMentorLogin(userLogin);
        }
        reviewRepository.save(reviewMapper.dtoToEntity(reviewDTO,
                platformUserRepository.findFirstByLogin(reviewDTO.getStudentLogin()),
                platformUserRepository.findFirstByLogin(reviewDTO.getMentorLogin())));
    }

    public void deleteReview(ReviewDTO reviewDTO) {
        reviewRepository.deleteById(reviewDTO.getId());
    }

    public List<Review> findReview(ReviewDTO reviewDTO) {
        if (reviewDTO.getMentorLogin() != null && reviewDTO.getStudentLogin() == null) {
            return reviewRepository.findReviewByMentorLogin(reviewDTO.getMentorLogin());
        }
        if (reviewDTO.getStudentLogin() != null && reviewDTO.getMentorLogin() == null) {
            return reviewRepository.findReviewByStudentLogin(reviewDTO.getStudentLogin());
        }
        if (reviewDTO.getStudentLogin() != null && reviewDTO.getMentorLogin() != null) {
            return reviewRepository.findReviewByMentorLoginAndStudentLogin(reviewDTO.getMentorLogin(),
                    reviewDTO.getStudentLogin());
        }
        if (reviewDTO.getBookedDate() != null && reviewDTO.getBookedTime() == null) {
            return reviewRepository.findReviewByBookedDate(reviewDTO.getBookedDate());
        }
        if (reviewDTO.getBookedDate() != null && reviewDTO.getBookedTime() != null) {
            return reviewRepository.findReviewByBookedDateAndBookedTime(reviewDTO.getBookedDate(),
                    reviewDTO.getBookedTime());
        }
        else {
            return reviewRepository.findReviewByMentorIsNull();
        }
    }
}
