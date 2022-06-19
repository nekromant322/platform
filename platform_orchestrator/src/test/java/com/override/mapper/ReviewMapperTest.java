package com.override.mapper;

import com.override.model.PlatformUser;
import com.override.model.Review;
import dto.ReviewDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.override.utils.TestFieldsUtil.*;

@ExtendWith(MockitoExtension.class)
public class ReviewMapperTest {

    @InjectMocks
    private ReviewMapper reviewMapper;

    @Test
    public void testDtoToEntity() {
        Review testReview = generateTestReview();
        PlatformUser testStudent = generateTestUser();
        PlatformUser testMentor = generateTestUser();
        ReviewDTO testReviewDTO = generateTestReviewDTO();

        Review review = reviewMapper.dtoToEntity(testReviewDTO, testStudent, testMentor);

        Assertions.assertEquals(review.getId(), testReview.getId());
        Assertions.assertEquals(review.getTopic(), testReview.getTopic());
        Assertions.assertEquals(review.getStudent(), testReview.getStudent());
        Assertions.assertEquals(review.getMentor(), testReview.getMentor());
        Assertions.assertEquals(review.getBookedDate(), testReview.getBookedDate());
        Assertions.assertEquals(review.getBookedTime(), testReview.getBookedTime());
        Assertions.assertEquals(review.getTimeSlots(), testReview.getTimeSlots());
    }

    @Test
    public void testEntityToDto() {
        Review testReview = generateTestReview();
        ReviewDTO testReviewDTO = generateTestReviewDTO();

        ReviewDTO reviewDTO = reviewMapper.entityToDto(testReview);

        Assertions.assertEquals(reviewDTO.getId(), testReviewDTO.getId());
        Assertions.assertEquals(reviewDTO.getTopic(), testReviewDTO.getTopic());
        Assertions.assertEquals(reviewDTO.getStudentLogin(), testReviewDTO.getStudentLogin());
        Assertions.assertEquals(reviewDTO.getMentorLogin(), testReviewDTO.getMentorLogin());
        Assertions.assertEquals(reviewDTO.getBookedDate(), testReviewDTO.getBookedDate());
        Assertions.assertEquals(reviewDTO.getBookedTime(), testReviewDTO.getBookedTime());
        Assertions.assertEquals(reviewDTO.getTimeSlots(), testReviewDTO.getTimeSlots());
    }
}
