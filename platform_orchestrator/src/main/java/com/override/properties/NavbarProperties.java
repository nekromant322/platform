package com.override.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "navbar")
public class NavbarProperties {

    private List<String> admin;

    private List<String> user;

    private List<String> graduate;
}
