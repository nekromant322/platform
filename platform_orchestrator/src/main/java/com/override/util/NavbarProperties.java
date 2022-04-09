package com.override.util;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@Getter
@ConfigurationProperties(prefix = "navbar")
public class NavbarProperties {

    private List<String> admin;

    private List<String> user;
}
