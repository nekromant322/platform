package dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class ReviewDTO {
    private Long id;
    private String title;
    private String studentLogin;
    private String mentorLogin;
    private LocalDate bookedDate;
    private LocalTime bookedTime;
    private LocalTime firstTimeSlot;
    private LocalTime secondTimeSlot;
    private LocalTime thirdTimeSlot;
}
