package com.override.models;

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
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private PlatformUser student;

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private PlatformUser mentor;

    @Column(name = "booked_date")
    private LocalDate bookedDate;

    @Column(name = "booked_time")
    private LocalTime bookedTime;

    @Column(name = "first_time_slot")
    private LocalTime firstTimeSlot;

    @Column(name = "second_time_slot")
    private LocalTime secondTimeSlot;

    @Column(name = "third_time_slot")
    private LocalTime thirdTimeSlot;
}
