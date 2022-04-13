package com.override.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class TimeSlotsConverter implements AttributeConverter<Set<LocalTime>, String> {
    private final String GROUP_DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(Set<LocalTime> timeList) {
        if (timeList == null) {
            return "";
        }
        return timeList.stream().map(String::valueOf).collect(Collectors.joining(GROUP_DELIMITER));
    }

    @Override
    public Set<LocalTime> convertToEntityAttribute(String string) {
        return Arrays.stream(string.split(GROUP_DELIMITER)).map(LocalTime::parse).collect(Collectors.toSet());
    }
}
