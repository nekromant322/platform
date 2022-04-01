package com.override.service;

import com.override.mappers.ReviewMapper;
import com.override.models.PlatformUser;
import com.override.models.Review;
import com.override.repositories.ReviewRepository;
import dtos.ReviewDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
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
    private PlatformUserService platformUserService;

    @Test
    void requestReview() {
        final ReviewDTO testReviewDTO = generateTestReviewDTO();

        reviewService.requestReview(testReviewDTO);
        verify(reviewRepository, times(1)).save(any());
        verify(reviewMapper, times(1)).dtoToEntity(any(), any(), any());
    }

    @Test
    void approveReview() {
        final ReviewDTO testReviewDTO = generateTestReviewDTO();

        reviewService.approveReview(testReviewDTO);
        verify(reviewRepository, times(1)).save(any());
        verify(reviewMapper, times(1)).dtoToEntity(any(), any(), any());
    }

    @Test
    void findReviewById() {
        Review testReview = generateTestReview();
        ReviewDTO testReviewDTO = generateTestReviewDTO();

        when(reviewRepository.findReviewById(1L)).thenReturn(testReview);

        final Review review = reviewService.findReviewById(testReviewDTO.getId());
        Assertions.assertEquals(review, testReview);
    }

    @Test
    void findReviewByMentor() {
        Review testReview = generateTestReview();
        ReviewDTO testReviewDTO = generateTestReviewDTO();
        PlatformUser testMentor = generateTestUser();

        when(platformUserService.findPlatformUserByLogin(testMentor.getLogin()))
                .thenReturn(testMentor);
        when(reviewRepository.findReviewByMentor(testMentor))
                .thenReturn(Collections.singletonList(testReview));

        final List<Review> review = reviewService.findReviewByMentor(testReviewDTO.getMentorLogin());
        Assertions.assertTrue(review.contains(testReview));
    }

    @Test
    void findReviewByStudent() {
        Review testReview = generateTestReview();
        ReviewDTO testReviewDTO = generateTestReviewDTO();
        PlatformUser testStudent = generateTestUser();

        when(platformUserService.findPlatformUserByLogin(testStudent.getLogin()))
                .thenReturn(testStudent);
        when(reviewRepository.findReviewByStudent(testStudent))
                .thenReturn(Collections.singletonList(testReview));

        final List<Review> review = reviewService.findReviewByStudent(testReviewDTO.getStudentLogin());
        Assertions.assertTrue(review.contains(testReview));
    }

    @Test
    void updateReview() {
        final ReviewDTO testReviewDTO = generateTestReviewDTO();

        reviewService.updateReview(testReviewDTO);
        verify(reviewRepository, times(1)).save(any());
        verify(reviewMapper, times(1)).dtoToEntity(any(), any(), any());
    }

    @Test
    void deleteReview() {
        reviewService.deleteReview(1L);
        verify(reviewRepository,times(1)).deleteById(1L);
    }
}
