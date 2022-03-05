package com.override.models;

import dtos.TaskIdentifierDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Table(name = "student-code")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class StudentCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column
    private String code;

    @Column
    private boolean isCodeCorrect;

    @Column
    private TaskIdentifierDTO identifierDTO;

    @Column
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private PlatformUser user;
}
