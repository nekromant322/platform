package dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;


@Data
@Builder
public class InterviewTableDTO {
    private Long id;

    private String userLogin;

    private String company;

    private String description;

    private String contacts;

    private LocalDate date;

    private LocalTime time;

    private String comment;

    private String stack;

    private int fork;

    private String meetingLink;

    private String distanceWork;
}
