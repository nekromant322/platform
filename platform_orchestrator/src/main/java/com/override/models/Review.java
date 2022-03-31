package com.override.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
}
