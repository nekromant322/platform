package com.override.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class TimeSlotConverterTest {

    @InjectMocks
    private TimeSlotsConverter timeSlotsConverter;

    @Test
    void convertToDatabaseColumn() {
        Set<LocalTime> testTimeSlots = new HashSet<>();
        testTimeSlots.add(LocalTime.of(16, 30));
        testTimeSlots.add(LocalTime.of(17, 30));
        testTimeSlots.add(LocalTime.of(18, 30));

        String testString = "16:30,17:30,18:30";

        String string = timeSlotsConverter.convertToDatabaseColumn(testTimeSlots);
        Assertions.assertEquals(string, testString);
    }

    @Test
    void convertToEntityAttribute() {
        Set<LocalTime> testTimeSlots = new HashSet<>();
        testTimeSlots.add(LocalTime.of(21, 30));
        testTimeSlots.add(LocalTime.of(22, 0));
        testTimeSlots.add(LocalTime.of(22, 30));

        String testString = "21:30,22:00,22:30";

        Set<LocalTime> timeSlots = timeSlotsConverter.convertToEntityAttribute(testString);
        Assertions.assertEquals(timeSlots, testTimeSlots);
    }
}
