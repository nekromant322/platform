package com.override.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    @ManyToMany(mappedBy = "reviews")
    private List<TimeSlot> bookedTimeSlots;
}
