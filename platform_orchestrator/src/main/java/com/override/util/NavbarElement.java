package com.override.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class NavbarElement {
    private String text;
    private String url;
}

