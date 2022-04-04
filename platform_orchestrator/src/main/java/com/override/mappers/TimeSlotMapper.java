package com.override.mappers;

import com.override.models.Review;
import com.override.models.TimeSlot;
import dtos.TimeSlotDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TimeSlotMapper {

    public TimeSlot dtoToEntity(TimeSlotDTO timeSlotDTO, List<Review> reviews){
        return TimeSlot.builder()
                .id(timeSlotDTO.getId())
                .bookedDate(timeSlotDTO.getBookedDate())
                .bookedTime(timeSlotDTO.getBookedTime())
                .firstTimeSlot(timeSlotDTO.getFirstTimeSlot())
                .secondTimeSlot(timeSlotDTO.getSecondTimeSlot())
                .thirdTimeSlot(timeSlotDTO.getThirdTimeSlot())
                .reviews(reviews)
                .build();
    }
}
