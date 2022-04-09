package com.override.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class NavbarElement {
    private String text;
    private String url;
}

