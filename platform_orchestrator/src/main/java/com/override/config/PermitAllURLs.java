package com.override.config;

import org.springframework.stereotype.Component;

@Component
public class PermitAllURLs {
    static String[] urls = (new String[]{"/login", "/init", "/css/*", "/images/*", "/js/*"});

    static String[] urlsForReportFilter = (new String[]{"/login", "/init", "/css/*", "/images/*", "/js/*", "css", "js","bugs", "navbar"});

    public String[] getPermitAllUrls() {
        return urls;
    }

    public String[] getPermitAllUrlsForReportFilter() {
        return urlsForReportFilter;
    }
}
