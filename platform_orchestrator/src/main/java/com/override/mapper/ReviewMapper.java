package com.override.mapper;

import com.override.model.PlatformUser;
import com.override.model.Review;
import com.override.model.VkCall;
import dto.ReviewDTO;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public Review dtoToEntity(ReviewDTO reviewDTO,
                              PlatformUser student,
                              PlatformUser mentor,
                              VkCall vkCall){
        return Review.builder()
                .id(reviewDTO.getId())
                .topic(reviewDTO.getTopic())
                .student(student)
                .mentor(mentor)
                .bookedDate(reviewDTO.getBookedDate())
                .bookedTime(reviewDTO.getBookedTime())
                .timeSlots(reviewDTO.getTimeSlots())
                .vkCall(vkCall)
                .build();
    }

    public ReviewDTO entityToDto(Review review) {
        ReviewDTO reviewDTO = ReviewDTO.builder()
                .id(review.getId())
                .topic(review.getTopic())
                .studentLogin(review.getStudent().getLogin())
                .mentorLogin("")
                .bookedDate(review.getBookedDate())
                .bookedTime(review.getBookedTime())
                .timeSlots(review.getTimeSlots())
                .callLink("")
                .build();
        if (review.getMentor() != null) {
            reviewDTO.setMentorLogin(review.getMentor().getLogin());
        }
        return reviewDTO;
    }
}
