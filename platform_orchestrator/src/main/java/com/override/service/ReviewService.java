package com.override.service;

import com.override.mappers.ReviewMapper;
import com.override.models.PlatformUser;
import com.override.models.Review;
import com.override.repositories.ReviewRepository;
import dtos.ReviewDTO;
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

    @Autowired
    private ReviewMapper reviewMapper;

    public ResponseEntity<String> requestReview(ReviewDTO reviewDTO) {
        reviewDTO.setConfirmed(false);
        reviewRepository.save(reviewMapper.dtoToEntity(reviewDTO,
                userService.findPlatformUserByLogin(reviewDTO.getStudentLogin()),
                userService.findPlatformUserByLogin(reviewDTO.getMentorLogin())));
        return new ResponseEntity<>("Отправлен запрос на ревью!", HttpStatus.OK);
    }

    public ResponseEntity<String> approveReview(ReviewDTO reviewDTO) {
        reviewDTO.setBookedDateTime(selectDateTimeSlot(reviewDTO.getDate(), reviewDTO.getSlots(), reviewDTO.getSlot()));
        reviewDTO.setConfirmed(true);
        reviewRepository.save(reviewMapper.dtoToEntity(reviewDTO,
                userService.findPlatformUserByLogin(reviewDTO.getStudentLogin()),
                userService.findPlatformUserByLogin(reviewDTO.getMentorLogin())));
        return new ResponseEntity<>("Ревью подтверждено!", HttpStatus.OK);
    }

    public Review findReviewById(Long id) {
        return reviewRepository.findReviewById(id);
    }


    public List<Review> findReviewByMentor(String login) {
        PlatformUser mentor = userService.findPlatformUserByLogin(login);
        return reviewRepository.findReviewByMentor(mentor);
    }

    public List<Review> findReviewByStudent(String login) {
        PlatformUser student = userService.findPlatformUserByLogin(login);
        return reviewRepository.findReviewByStudent(student);
    }

    public ResponseEntity<String> updateReview(ReviewDTO reviewDTO) {
        reviewRepository.save(reviewMapper.dtoToEntity(reviewDTO,
                userService.findPlatformUserByLogin(reviewDTO.getStudentLogin()),
                userService.findPlatformUserByLogin(reviewDTO.getMentorLogin())));
        return new ResponseEntity<>("Ревью обновлено!", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteReview(Long id) {
        reviewRepository.deleteById(id);
        return new ResponseEntity<>("Ревью удалено!", HttpStatus.OK);
    }

    private List<LocalDateTime> generateDefaultDateTimeSlots(LocalDate date) {
        List<LocalDateTime> dateTimeSlots = new ArrayList<>();
        LocalTime time = LocalTime.of(0, 0);
        for (int i = 0; i < 47; i++) {
            dateTimeSlots.add(LocalDateTime.of(date, time));
            time = time.plusMinutes(30);
        }
        return dateTimeSlots;
    }

    private LocalDateTime selectDateTimeSlot(LocalDate date, int[] slots, int slot) {
        List<LocalDateTime> dateTimeSlots = generateDefaultDateTimeSlots(date);
        List<LocalDateTime> selectedTimeSlots = new ArrayList<>();
        for (int index : slots) {
            selectedTimeSlots.add(dateTimeSlots.get(index));
        }
        return selectedTimeSlots.get(slot);
    }
}
