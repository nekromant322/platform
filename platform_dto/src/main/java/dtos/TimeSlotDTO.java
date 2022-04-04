package dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
public class TimeSlotDTO {
    private Long id;
    private LocalDate bookedDate;
    private LocalTime bookedTime;
    private LocalTime firstTimeSlot;
    private LocalTime secondTimeSlot;
    private LocalTime thirdTimeSlot;
    private List<ReviewDTO> reviews;
}
