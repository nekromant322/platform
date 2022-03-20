package com.override.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentReport {

    //TODO: когда будет секурити добавить автора
    //private Student student;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private LocalDate date;
    private String text;
    private Double hours;

    public StudentReport(LocalDate date, String text, Double hours) {
        this.date = date;
        this.text = text;
        this.hours = hours;
    }
}
