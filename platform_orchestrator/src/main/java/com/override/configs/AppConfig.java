package com.override.configs;

import com.github.javafaker.Faker;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.IOException;

@Configuration
@Slf4j
public class AppConfig {

    @Bean
    public Faker faker() {
        return new Faker();
    }

    @Bean
    public ITextRenderer iTextRenderer() {
        ITextRenderer iTextRenderer = new ITextRenderer();
        try {
            iTextRenderer.getFontResolver().addFont("/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
        return iTextRenderer;
    }
}
