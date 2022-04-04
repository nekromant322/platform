package dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReviewDTO {
    private Long id;
    private String title;
    private String studentLogin;
    private String mentorLogin;
    private List<TimeSlotDTO> bookedTimeSlots;
}
