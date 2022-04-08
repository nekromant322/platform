package com.override.service;

import com.override.mappers.ReviewMapper;
import com.override.models.PlatformUser;
import com.override.models.Review;
import com.override.repositories.PlatformUserRepository;
import com.override.repositories.ReviewRepository;
import dtos.ReviewDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Test
    void saveOrUpdateReview() {
        ReviewDTO testReviewDTO = generateTestReviewDTO();
        PlatformUser testUser = generateTestUser();

        when(platformUserRepository.findFirstByLogin(testReviewDTO.getStudentLogin()))
                .thenReturn(testUser);
        when(platformUserRepository.findFirstByLogin(testReviewDTO.getMentorLogin()))
                .thenReturn(testUser);

        reviewService.saveOrUpdateReview(testReviewDTO);
        verify(reviewRepository, times(1)).save(any());
        verify(reviewMapper, times(1)).dtoToEntity(any(), any(), any());
    }

    @Test
    void deleteReview() {
        ReviewDTO testReviewDTO = generateTestReviewDTO();
        reviewService.deleteReview(testReviewDTO);
        verify(reviewRepository,times(1)).deleteById(1L);
    }

    @Test
    void findReviewByMentorLogin() {
        List<Review> testReview = new ArrayList<>();
        testReview.add(generateTestReview());
        ReviewDTO testReviewDTO = generateTestReviewDTO();

        ReviewDTO testFindReviewDTO = generateTestReviewDTO();
        testFindReviewDTO.setStudentLogin(null);
        testFindReviewDTO.setBookedDate(null);

        when(reviewRepository.findReviewByMentorLogin(testReviewDTO.getMentorLogin()))
                .thenReturn(testReview);

        List<Review> review = reviewService.findReview(testFindReviewDTO);
        Assertions.assertEquals(review.iterator().next(), testReview.iterator().next());
    }

    @Test
    void findReviewByStudentLogin() {
        List<Review> testReview = new ArrayList<>();
        testReview.add(generateTestReview());
        ReviewDTO testReviewDTO = generateTestReviewDTO();

        ReviewDTO testFindReviewDTO = generateTestReviewDTO();
        testFindReviewDTO.setMentorLogin(null);
        testFindReviewDTO.setBookedDate(null);

        when(reviewRepository.findReviewByStudentLogin(testReviewDTO.getStudentLogin()))
                .thenReturn(testReview);

        List<Review> review = reviewService.findReview(testFindReviewDTO);
        Assertions.assertEquals(review.iterator().next(), testReview.iterator().next());
    }

    @Test
    void findReviewByMentorLoginAndStudentLogin() {
        List<Review> testReview = new ArrayList<>();
        testReview.add(generateTestReview());
        ReviewDTO testReviewDTO = generateTestReviewDTO();

        ReviewDTO testFindReviewDTO = generateTestReviewDTO();
        testFindReviewDTO.setBookedDate(null);

        when(reviewRepository.findReviewByMentorLoginAndStudentLogin(testReviewDTO.getMentorLogin(),
                testReviewDTO.getStudentLogin())).thenReturn(testReview);

        List<Review> review = reviewService.findReview(testFindReviewDTO);
        Assertions.assertEquals(review.iterator().next(), testReview.iterator().next());
    }

    @Test
    void findReviewByBookedDate() {
        List<Review> testReview = new ArrayList<>();
        testReview.add(generateTestReview());
        ReviewDTO testReviewDTO = generateTestReviewDTO();

        ReviewDTO testFindReviewDTO = generateTestReviewDTO();
        testFindReviewDTO.setMentorLogin(null);
        testFindReviewDTO.setStudentLogin(null);
        testFindReviewDTO.setBookedTime(null);

        when(reviewRepository.findReviewByBookedDate(testReviewDTO.getBookedDate()))
                .thenReturn(testReview);

        List<Review> review = reviewService.findReview(testFindReviewDTO);
        Assertions.assertEquals(review.iterator().next(), testReview.iterator().next());
    }

    @Test
    void findReviewByBookedDateAndTime() {
        List<Review> testReview = new ArrayList<>();
        testReview.add(generateTestReview());
        ReviewDTO testReviewDTO = generateTestReviewDTO();

        ReviewDTO testFindReviewDTO = generateTestReviewDTO();
        testFindReviewDTO.setMentorLogin(null);
        testFindReviewDTO.setStudentLogin(null);

        when(reviewRepository.findReviewByBookedDateAndBookedTime(testReviewDTO.getBookedDate(),
                testReviewDTO.getBookedTime())).thenReturn(testReview);

        List<Review> review = reviewService.findReview(testFindReviewDTO);
        Assertions.assertEquals(review.iterator().next(), testReview.iterator().next());
    }

    @Test
    void findReviewByMentorIsNull() {
        Review testReview = generateTestReview();
        testReview.setMentor(null);
        List<Review> testReviewList = new ArrayList<>();
        testReviewList.add(testReview);

        ReviewDTO testFindReviewDTO = generateTestReviewDTO();
        testFindReviewDTO.setMentorLogin(null);
        testFindReviewDTO.setStudentLogin(null);
        testFindReviewDTO.setBookedDate(null);

        when(reviewRepository.findReviewByMentorIsNull()).thenReturn(testReviewList);

        List<Review> review = reviewService.findReview(testFindReviewDTO);
        Assertions.assertEquals(review.iterator().next(), testReviewList.iterator().next());
    }
}
