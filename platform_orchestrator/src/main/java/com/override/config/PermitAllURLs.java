package com.override.config;

import org.springframework.stereotype.Component;

@Component
public class PermitAllURLs {
    static String[] urls = (new String[]{"/login", "/init", "/css/*", "/images/*", "/js/*", "css", "js", "navbar"});

    public String[] getPermitAllUrls() {
        return urls;
    }
}
