package dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewFilterDTO {
    private String studentLogin;
    private String mentorLogin;
    private LocalDate bookedDate;
}
