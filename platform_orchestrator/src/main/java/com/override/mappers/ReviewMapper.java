package com.override.mappers;

import com.override.models.PlatformUser;
import com.override.models.Review;
import com.override.models.TimeSlot;
import dtos.ReviewDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReviewMapper {

    public Review dtoToEntity(ReviewDTO reviewDTO,
                              PlatformUser student,
                              PlatformUser mentor,
                              List<TimeSlot> bookedTimeSlots){
        return Review.builder()
                .id(reviewDTO.getId())
                .title(reviewDTO.getTitle())
                .student(student)
                .mentor(mentor)
                .bookedTimeSlots(bookedTimeSlots)
                .build();
    }
}
