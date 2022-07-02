package com.override.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "sum")
    private Long sum;

    @Column(name = "account_number")
    private Long accountNumber;

    @Column(name = "message")
    private String message;

    public Payment(String studentName, LocalDate date, Long sum, Long accountNumber, String message) {
        this.studentName = studentName;
        this.date = date;
        this.sum = sum;
        this.accountNumber = accountNumber;
        this.message = message;
    }
}
