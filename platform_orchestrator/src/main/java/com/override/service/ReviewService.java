package com.override.service;

import com.override.feigns.NotificatorFeign;
import com.override.mappers.ReviewMapper;
import com.override.models.Review;
import com.override.repositories.PlatformUserRepository;
import com.override.repositories.ReviewRepository;
import dtos.ReviewDTO;
import dtos.ReviewFilterDTO;
import enums.Communication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private PlatformUserRepository platformUserRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private NotificatorFeign notificatorFeign;

    /**
     * Saves a new or changes an existing review
     * If the review is new, then the user's login is assigned to the student
     * Only the mentor can change the review, so the username is assigned to the mentor
     *
     * @param reviewDTO review information obtained from the request
     * @param userLogin username of the user making the request
     */
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

    /**
     * Searches for the necessary reviews using the filter
     * Reviews can be found by student or mentor login and by date
     * If these parameters have null, then we are looking for new reviews that have not yet assigned a mentor and time
     *
     * @param reviewFilterDTO comes from request
     * @return returns a list of reviewDTOs obtained by mapping the list of found reviews
     */
    public List<ReviewDTO> findReview(ReviewFilterDTO reviewFilterDTO) {
        if (reviewFilterDTO.getMentorLogin() != null) {
            return (reviewRepository.findReviewByMentorLogin(reviewFilterDTO.getMentorLogin())).stream()
                    .map(reviewMapper::entityToDto).collect(Collectors.toList());
        }
        if (reviewFilterDTO.getStudentLogin() != null) {
            return (reviewRepository.findReviewByStudentLogin(reviewFilterDTO.getStudentLogin())).stream()
                    .map(reviewMapper::entityToDto).collect(Collectors.toList());
        }
        if (reviewFilterDTO.getBookedDate() != null) {
            return (reviewRepository.findReviewByBookedDate(reviewFilterDTO.getBookedDate())).stream()
                    .map(reviewMapper::entityToDto).collect(Collectors.toList());
        } else {
            return (reviewRepository.findReviewByMentorIsNull()).stream()
                    .map(reviewMapper::entityToDto).collect(Collectors.toList());
        }
    }

    public void sendScheduledNotification() {
        reviewRepository.findReviewByBookedDate(LocalDate.now())
                .stream()
                .filter(review -> review.getBookedTime() != null)
                .filter(review -> review.getBookedTime().isAfter(LocalTime.now()))
                .filter(review -> review.getBookedTime().isBefore(LocalTime.now().plusMinutes(10)))
                .forEach(review -> {
                    String messageText = "Скоро ревью у @" + review.getStudent().getLogin() +
                            " с @" + review.getMentor().getLogin() + "\n" +
                            review.getBookedDate() + " " + review.getBookedTime() +
                            "\nТема: " + review.getTopic();
                    notificatorFeign.sendMessage(review.getStudent().getLogin(), messageText, Communication.TELEGRAM);
                    notificatorFeign.sendMessage(review.getMentor().getLogin(), messageText, Communication.TELEGRAM);
                });
    }
}

