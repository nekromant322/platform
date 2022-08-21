package com.override.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class InterviewTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_login")
    private String userLogin;

    private String company;

    private String description;

    private String contacts;

    private LocalDate date;

    private LocalTime time;

    private String comment;

    private String stack;

    private Integer fork;

    @Column(name = "meeting_link")
    private String meetingLink;

    @Column(name = "distance_work")
    private String distanceWork;

}
