package com.override.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private PlatformUser student;

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private PlatformUser mentor;

    @Column(name = "title")
    private String title;

    @Column(name = "date")
    private LocalDateTime bookedDateTime;

    @Column(name = "confirmed")
    private Boolean confirmed;

    public List<LocalDateTime> getDateTimeSlots(LocalDate date) {
        List<LocalDateTime> dateTimeSlots = new ArrayList<>();
        LocalTime time = LocalTime.of(0, 0);
        for (int i = 1; i < 48; i++) {
            dateTimeSlots.add(LocalDateTime.of(date, time));
            time = time.plusMinutes(30);
        }
        return dateTimeSlots;
    }

    public void setBookedDateTime(LocalDate date, int index) {
        List<LocalDateTime> dateTimeSlots = getDateTimeSlots(date);
        bookedDateTime = dateTimeSlots.get(index);
    }
}
