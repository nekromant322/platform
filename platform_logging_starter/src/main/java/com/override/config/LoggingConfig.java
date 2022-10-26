package com.override.config;

import com.override.LoggingFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(LoggingConfig.class)
public class LoggingConfig {

    @Bean
    public LoggingFilter loggingFilter() {
        return new LoggingFilter();
    }
}
