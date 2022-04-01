package dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class ReviewDTO {
    private Long id;
    private String studentLogin;
    private String mentorLogin;
    private String title;
    private LocalDateTime bookedDateTime;
    private Boolean confirmed;
    private LocalDate date;
    private int[] slots;
    private int slot;
}
