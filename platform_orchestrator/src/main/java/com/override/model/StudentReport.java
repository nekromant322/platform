package com.override.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentReport {

    //TODO: когда будет секурити добавить автора
    //private Student student;
    private LocalDate date;
    private String text;
    private Double hours;
}
