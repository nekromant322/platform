package dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
@Builder
public class ReviewDTO {
    private Long id;
    private String topic;
    private String studentLogin;
    private String mentorLogin;
    private LocalDate bookedDate;
    private LocalTime bookedTime;
    private Set<LocalTime> timeSlots;
    private String callLink;
}
