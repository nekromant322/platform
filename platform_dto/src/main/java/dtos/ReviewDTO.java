package dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class ReviewDTO {
    private Long id;
    private String student;
    private String mentor;
    private String title;
    private LocalDateTime bookedDateTime;
    private Boolean confirmed;
    private LocalDate date;
    private int index;
}
