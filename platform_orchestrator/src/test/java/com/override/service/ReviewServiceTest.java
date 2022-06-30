package com.override.service;

import com.override.feign.NotificatorFeign;
import com.override.mapper.ReviewMapper;
import com.override.model.PlatformUser;
import com.override.model.Review;
import com.override.repository.PlatformUserRepository;
import com.override.repository.ReviewRepository;
import dto.ReviewDTO;
import dto.ReviewFilterDTO;
import enums.Communication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.override.utils.TestFieldsUtil.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @Mock
    private PlatformUserRepository platformUserRepository;

    @Mock
    private NotificatorFeign notificatorFeign;

    @Test
    void saveOrUpdateReview() {
        ReviewDTO testReviewDTO = generateTestReviewDTO();
        PlatformUser testUser = generateTestUser();

        when(platformUserRepository.findFirstByLogin(testReviewDTO.getStudentLogin()))
                .thenReturn(testUser);
        when(platformUserRepository.findFirstByLogin(testReviewDTO.getMentorLogin()))
                .thenReturn(testUser);

        reviewService.saveOrUpdateReview(testReviewDTO, testUser.getLogin());
        verify(reviewRepository, times(1)).save(any());
        verify(reviewMapper, times(1)).dtoToEntity(any(), any(), any());
    }

    @Test
    void deleteReview() {
        reviewService.deleteReview(1L);
        verify(reviewRepository,times(1)).deleteById(1L);

    }

    @Test
    void deleteReviewTelegramNotification() {


        reviewService.deleteReview(1L);
        verify(reviewRepository,times(1)).deleteById(1L);

    }

    @Test
    void findReviewByMentorLogin() {
        List<ReviewDTO> testReviewDTOList = new ArrayList<>();
        testReviewDTOList.add(generateTestReviewDTO());
        ReviewDTO testReviewDTO = generateTestReviewDTO();

        List<Review> testReview = new ArrayList<>();
        testReview.add(generateTestReview());

        ReviewFilterDTO testReviewFilterDTO = new ReviewFilterDTO();
        testReviewFilterDTO.setStudentLogin(null);
        testReviewFilterDTO.setBookedDate(null);
        testReviewFilterDTO.setMentorLogin(testReviewDTO.getMentorLogin());

        when(reviewRepository.findReviewByMentorLogin(testReviewFilterDTO.getMentorLogin())).thenReturn(testReview);
        when(reviewMapper.entityToDto(testReview.iterator().next())).thenReturn(testReviewDTOList.iterator().next());

        List<ReviewDTO> review = reviewService.findReview(testReviewFilterDTO);
        Assertions.assertEquals(review.iterator().next(), testReviewDTOList.iterator().next());
    }

    @Test
    void findReviewByStudentLogin() {
        List<ReviewDTO> testReviewDTOList = new ArrayList<>();
        testReviewDTOList.add(generateTestReviewDTO());
        ReviewDTO testReviewDTO = generateTestReviewDTO();

        List<Review> testReview = new ArrayList<>();
        testReview.add(generateTestReview());

        ReviewFilterDTO testReviewFilterDTO = new ReviewFilterDTO();
        testReviewFilterDTO.setStudentLogin(testReviewDTO.getStudentLogin());
        testReviewFilterDTO.setBookedDate(null);
        testReviewFilterDTO.setMentorLogin(null);

        when(reviewRepository.findReviewByStudentLogin(testReviewFilterDTO.getStudentLogin())).thenReturn(testReview);
        when(reviewMapper.entityToDto(testReview.iterator().next())).thenReturn(testReviewDTOList.iterator().next());

        List<ReviewDTO> review = reviewService.findReview(testReviewFilterDTO);
        Assertions.assertEquals(review.iterator().next(), testReviewDTOList.iterator().next());
    }

    @Test
    void findReviewByBookedDate() {
        List<ReviewDTO> testReviewDTOList = new ArrayList<>();
        testReviewDTOList.add(generateTestReviewDTO());
        ReviewDTO testReviewDTO = generateTestReviewDTO();

        List<Review> testReview = new ArrayList<>();
        testReview.add(generateTestReview());

        ReviewFilterDTO testReviewFilterDTO = new ReviewFilterDTO();
        testReviewFilterDTO.setStudentLogin(null);
        testReviewFilterDTO.setBookedDate(testReviewDTO.getBookedDate());
        testReviewFilterDTO.setMentorLogin(null);

        when(reviewRepository.findReviewByBookedDate(testReviewFilterDTO.getBookedDate())).thenReturn(testReview);
        when(reviewMapper.entityToDto(testReview.iterator().next())).thenReturn(testReviewDTOList.iterator().next());

        List<ReviewDTO> review = reviewService.findReview(testReviewFilterDTO);
        Assertions.assertEquals(review.iterator().next(), testReviewDTOList.iterator().next());
    }

    @Test
    void findReviewByMentorIsNull() {
        ReviewDTO testReviewDTO = generateTestReviewDTO();
        testReviewDTO.setMentorLogin(null);
        List<ReviewDTO> testReviewList = new ArrayList<>();
        testReviewList.add(testReviewDTO);

        List<Review> testReview = new ArrayList<>();
        testReview.add(generateTestReview());

        ReviewFilterDTO testReviewFilterDTO = new ReviewFilterDTO();
        testReviewFilterDTO.setStudentLogin(null);
        testReviewFilterDTO.setBookedDate(null);
        testReviewFilterDTO.setMentorLogin(null);

        when(reviewRepository.findReviewByMentorIsNull()).thenReturn(testReview);
        when(reviewMapper.entityToDto(testReview.iterator().next())).thenReturn(testReviewList.iterator().next());

        List<ReviewDTO> review = reviewService.findReview(testReviewFilterDTO);
        Assertions.assertEquals(review.iterator().next(), testReviewList.iterator().next());
    }

    @Test
    void sendScheduledNotification() {
        List<Review> reviewList = new ArrayList<>();

        Review review1 = generateTestReview();
        review1.setId(10L);
        review1.setBookedTime(null);
        reviewList.add(review1);

        Review review2 = generateTestReview();
        review2.setId(20L);
        review2.setBookedTime(LocalTime.now().plusMinutes(45));
        reviewList.add(review2);

        Review review3 = generateTestReview();
        review3.setId(30L);
        review3.setBookedTime(LocalTime.now().minusMinutes(45));
        reviewList.add(review3);

        Review review4 = generateTestReview();
        review4.setId(40L);
        review4.setBookedTime(LocalTime.now().plusMinutes(5));
        reviewList.add(review4);

        when(reviewRepository.findReviewByBookedDate(LocalDateTime.now().plusMinutes(10).toLocalDate()))
                .thenReturn(reviewList);

        String messageText = "Скоро ревью у @" + review4.getStudent().getLogin() +
                " с @" + review4.getMentor().getLogin() + "\n" +
                review4.getBookedDate() + " " + review4.getBookedTime() +
                "\nТема: " + review4.getTopic();

        reviewService.sendScheduledNotification();
        verify(notificatorFeign, times(2)).sendMessage(review4.getStudent().getLogin(), messageText, Communication.TELEGRAM);
    }
}
