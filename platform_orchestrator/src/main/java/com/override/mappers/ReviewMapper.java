package com.override.mappers;

import com.override.models.PlatformUser;
import com.override.models.Review;
import dtos.ReviewDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReviewMapper {

    public Review dtoToEntity(ReviewDTO reviewDTO,
                              PlatformUser student,
                              PlatformUser mentor){
        return Review.builder()
                .id(reviewDTO.getId())
                .topic(reviewDTO.getTopic())
                .student(student)
                .mentor(mentor)
                .bookedDate(reviewDTO.getBookedDate())
                .bookedTime(reviewDTO.getBookedTime())
                .timeSlots(reviewDTO.getTimeSlots())
                .build();
    }

    public ReviewDTO entityToDto(Review review) {
        if (review.getMentor() == null) {
            return ReviewDTO.builder()
                    .id(review.getId())
                    .topic(review.getTopic())
                    .studentLogin(review.getStudent().getLogin())
                    .mentorLogin("")
                    .bookedDate(review.getBookedDate())
                    .bookedTime(review.getBookedTime())
                    .timeSlots(review.getTimeSlots())
                    .build();
        } else {
            return ReviewDTO.builder()
                    .id(review.getId())
                    .topic(review.getTopic())
                    .studentLogin(review.getStudent().getLogin())
                    .mentorLogin(review.getMentor().getLogin())
                    .bookedDate(review.getBookedDate())
                    .bookedTime(review.getBookedTime())
                    .timeSlots(review.getTimeSlots())
                    .build();
        }
    }

    public List<ReviewDTO> entitiesToDto(List<Review> reviews) {
        List<ReviewDTO> dtoList = new ArrayList<>();
        for (Review review : reviews) {
            dtoList.add(entityToDto(review));
        }
        return dtoList;
    }
}
