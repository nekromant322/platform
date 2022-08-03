package com.override.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CurrentTimeService {

    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
}