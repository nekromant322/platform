package com.override.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StudentReport {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private PlatformUser student;
    private LocalDate date;
    private String text;
    private Double hours;

    public StudentReport(LocalDate date, String text, Double hours) {
        this.date = date;
        this.text = text;
        this.hours = hours;
    }
}
