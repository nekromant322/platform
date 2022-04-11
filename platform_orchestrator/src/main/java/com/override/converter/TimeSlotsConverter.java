package com.override.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class TimeSlotsConverter implements AttributeConverter<Set<String>, String> {
    private final String GROUP_DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(Set<String> timeList) {
        if (timeList == null) {
            return "";
        }
        return timeList.stream().map(String::valueOf).collect(Collectors.joining(GROUP_DELIMITER));
    }

    @Override
    public Set<String> convertToEntityAttribute(String string) {
        return Arrays.stream(string.split(GROUP_DELIMITER)).map(String::valueOf).collect(Collectors.toSet());
    }
}
