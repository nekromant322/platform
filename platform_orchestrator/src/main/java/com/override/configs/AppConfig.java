package com.override.configs;

import com.github.javafaker.Faker;
import com.override.util.NavbarProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Faker faker() {
        return new Faker();
    }

    @Bean
    @ConfigurationProperties(prefix = "navbar")
    public NavbarProperties setNavbarProperties() {
        return new NavbarProperties();
    }
}
