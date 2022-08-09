package dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewFilterDTO {
    private String mentorLogin;
    private String studentLogin;
    private LocalDate bookedDate;
}
