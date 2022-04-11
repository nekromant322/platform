package com.override.mappers;

import com.override.models.PlatformUser;
import com.override.models.Review;
import dtos.ReviewDTO;
import org.springframework.stereotype.Component;

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
}
