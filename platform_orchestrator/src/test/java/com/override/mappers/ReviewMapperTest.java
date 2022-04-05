package com.override.mappers;

import com.override.models.PlatformUser;
import com.override.models.Review;
import dtos.ReviewDTO;
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
        Assertions.assertEquals(review.getTitle(), testReview.getTitle());
        Assertions.assertEquals(review.getStudent(), testReview.getStudent());
        Assertions.assertEquals(review.getMentor(), testReview.getMentor());
        Assertions.assertEquals(review.getBookedDate(), testReview.getBookedDate());
        Assertions.assertEquals(review.getBookedTime(), testReview.getBookedTime());
        Assertions.assertEquals(review.getFirstTimeSlot(), testReview.getFirstTimeSlot());
        Assertions.assertEquals(review.getSecondTimeSlot(), testReview.getSecondTimeSlot());
        Assertions.assertEquals(review.getThirdTimeSlot(), testReview.getThirdTimeSlot());
    }
}
