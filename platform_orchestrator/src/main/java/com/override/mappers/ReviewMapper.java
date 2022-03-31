package com.override.mappers;

import com.override.models.PlatformUser;
import com.override.models.Review;
import com.override.service.PlatformUserService;
import dtos.ReviewDTO;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Builder
@Component
public class ReviewMapper {

    @Autowired
    private PlatformUserService platformUserService;

    public Review dtoToEntity(ReviewDTO reviewDTO, PlatformUser student, PlatformUser mentor){
        return Review.builder()
                .id(reviewDTO.getId())
                .student(student)
                .mentor(mentor)
                .title(reviewDTO.getTitle())
                .bookedDateTime(reviewDTO.getBookedDateTime())
                .confirmed(reviewDTO.getConfirmed())
                .build();
    }
}
