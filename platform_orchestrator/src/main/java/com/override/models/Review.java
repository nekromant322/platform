package com.override.models;

import com.override.converter.TimeSlotsConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "topic")
    private String topic;

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

    @Convert(converter = TimeSlotsConverter.class)
    @Column(name = "time_slots")
    private Set<String> timeSlots;
}
