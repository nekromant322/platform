package com.override.repositories;

import com.override.models.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    List<TimeSlot> findTimeSlotsById(Long id);
    List<TimeSlot> findTimeSlotByBookedDate(LocalDate date);
    List<TimeSlot> findTimeSlotByBookedDateAndBookedTime(LocalDate date, LocalTime time);
}
