package com.override.service;

import com.override.mappers.ReviewMapper;
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

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    public List<ReviewDTO> findReview(ReviewDTO reviewDTO) {
        if (reviewDTO.getMentorLogin() != null) {
            return reviewMapper.entitiesToDto(reviewRepository.findReviewByMentorLogin(reviewDTO.getMentorLogin()));
        }
        if (reviewDTO.getStudentLogin() != null) {
            return reviewMapper.entitiesToDto(reviewRepository.findReviewByStudentLogin(reviewDTO.getStudentLogin()));
        }
        if (reviewDTO.getBookedDate() != null) {
            return reviewMapper.entitiesToDto(reviewRepository.findReviewByBookedDate(reviewDTO.getBookedDate()));
        } else {
            return reviewMapper.entitiesToDto(reviewRepository.findReviewByMentorIsNull());
        }
    }
}
